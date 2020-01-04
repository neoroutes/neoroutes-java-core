package project.neoroutes.diffieHellman;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class EncryptedSession {
    private final PublicKey receiverPublicKey;
    private final String ALGO = "AES";;
    private KeyAgreement keyAgreement;

    byte[] sharedSecret;

    public EncryptedSession(DiffieHellman diffieHellman, PublicKey receiverPublicKey) throws InvalidKeyException {
        this.receiverPublicKey = receiverPublicKey;
        try {
            this.keyAgreement = KeyAgreement.getInstance("ECDH");
            this.keyAgreement.init(diffieHellman.getKeyPair().getPrivate());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        init();
    }

    public void init(){
        try {
            keyAgreement.doPhase(receiverPublicKey, true);
            sharedSecret = keyAgreement.generateSecret();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String msg) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(msg.getBytes());
            return new BASE64Encoder().encode(encVal);
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
            byte[] decodedValue = new BASE64Decoder().decodeBuffer(encryptedData);
            byte[] decValue = c.doFinal(decodedValue);
            return new String(decValue);
        } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return encryptedData;
    }

    protected Key generateKey() {
        return new SecretKeySpec(sharedSecret, ALGO);
    }

}
