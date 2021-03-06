package project.neoroutes.diffieHellman;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EncryptedSession {
    private final PublicKey receiverPublicKey;
    private final DiffieHellman diffieHellman;
    private final String ALGO = "AES";
    private KeyAgreement keyAgreement;
    private byte[] derivedKey;


    public EncryptedSession(DiffieHellman diffieHellman, PublicKey receiverPublicKey) throws InvalidKeyException {
        this.receiverPublicKey = receiverPublicKey;
        this.diffieHellman = diffieHellman;
        try {
            this.keyAgreement = KeyAgreement.getInstance("ECDH");
            this.keyAgreement.init(diffieHellman.getKeyPair().getPrivate());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        init();
    }

    private void init(){
        try {
            keyAgreement.doPhase(receiverPublicKey, true);
            byte[] sharedSecret = keyAgreement.generateSecret();
            MessageDigest hash = MessageDigest.getInstance("SHA-256");
            hash.update(sharedSecret);
            // Simple deterministic ordering
            List<ByteBuffer> keys = Arrays.asList(ByteBuffer.wrap(diffieHellman.getPublicKey().getEncoded()), ByteBuffer.wrap(receiverPublicKey.getEncoded()));
            Collections.sort(keys);
            hash.update(keys.get(0));
            hash.update(keys.get(1));

            derivedKey = hash.digest();
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String msg) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(msg.getBytes(StandardCharsets.UTF_8));
            return new String(new Base64().encode(encVal), StandardCharsets.UTF_8);
        } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public String decrypt(String encryptedData) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = new Base64().decode(encryptedData);
            byte[] decValue = c.doFinal(decodedValue);
            return new String(decValue);
        } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedData;
    }

    private Key generateKey() {
        return new SecretKeySpec(derivedKey, ALGO);
    }

}
