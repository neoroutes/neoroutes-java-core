package project.neoroutes.helper;

import lombok.AllArgsConstructor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

/*
 * For more details and other way(s) to verify message signature visit: https://www.baeldung.com/java-digital-signature
 */

@AllArgsConstructor
public class MessageSigner {
    private final PrivateKey privateKey;

    public byte[] sign(byte[] message) throws InvalidKeyException {
        Cipher cipher = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageHash = md.digest(message);

            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(messageHash);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException ignored) {
            return null;
        }
    }
}