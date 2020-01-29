package project.neoroutes.signature;

import org.junit.jupiter.api.Test;
import project.neoroutes.diffieHellman.DiffieHellman;
import project.neoroutes.diffieHellman.EncryptedSession;
import project.neoroutes.helper.KeyStoreWrapper;
import project.neoroutes.helper.MessageSigner;
import project.neoroutes.helper.PrivateKeyProvider;
import project.neoroutes.helper.SignatureVerifier;
import project.neoroutes.key.CNGenerator;
import project.neoroutes.key.KeyStoreGenerator;
import project.neoroutes.key.NeoRoutesCNGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * This test, creates an encrypted session. It is considered that both sessions know about each others main public key
 * Therefore we encrypt messages using Diffie Hellman and sign them using public key that we already have outside of the encrypted session
 */

public class SignatureTest {
    private final KeyStoreGenerator keyStoreGenerator;
    private final String keyAddress = "/tmp/key.jks";
    private final String keyPass = "123456";
    private final String userId = UUID.randomUUID().toString();
    private final CNGenerator cnGenerator = new NeoRoutesCNGenerator(userId);

    private final EncryptedSession encryptedSession1;
    private final EncryptedSession encryptedSession2;


    public SignatureTest() throws IOException, InvalidKeyException {
        new File(keyAddress).delete();
        keyStoreGenerator = new KeyStoreGenerator(cnGenerator, keyAddress, keyPass, userId);
        DiffieHellman dh1 = new DiffieHellman();
        DiffieHellman dh2 = new DiffieHellman();
        this.encryptedSession1 = new EncryptedSession(dh1, dh2.getPublicKey());
        this.encryptedSession2 = new EncryptedSession(dh2, dh1.getPublicKey());
    }

    @Test
    public void testSignature() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, InvalidKeyException {
        KeyStore keyStore = keyStoreGenerator.generate();
        PrivateKeyProvider privateKeyProvider = new PrivateKeyProvider(keyStore, keyPass);
        PrivateKey privateKey = privateKeyProvider.getPrivateKey();
        Certificate certificate = new KeyStoreWrapper(keyStore, keyAddress, keyPass).getCertificate("main");
        PublicKey publicKey = certificate.getPublicKey();

        System.out.println(new String(privateKey.getEncoded(), StandardCharsets.UTF_8));
        System.out.println(new String(publicKey.getEncoded(), StandardCharsets.UTF_8));
        String message = "This is my message";

        String encryptedMessage = encryptedSession1.encrypt(message);
        byte[] encryptedMessageBytes = encryptedMessage.getBytes();
        MessageSigner messageSigner = new MessageSigner(privateKey);
        byte[] signature = messageSigner.sign(encryptedMessageBytes);
        System.out.println(new String(signature, StandardCharsets.UTF_8));


        String decryptedMessage = encryptedSession2.decrypt(encryptedMessage);
        System.out.println(decryptedMessage);
        assertTrue(SignatureVerifier.verify(publicKey, signature, encryptedMessageBytes));
        assertEquals(message, decryptedMessage);

    }

}
