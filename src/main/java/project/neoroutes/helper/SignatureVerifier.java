package project.neoroutes.helper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Arrays;

/*
 * For more details and other way(s) to verify message signature visit: https://www.baeldung.com/java-digital-signature
 */

public class SignatureVerifier {

    public static boolean verify(PublicKey publicKey, byte[] signature, byte[] message) throws InvalidKeyException {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] decryptedMessageHash = cipher.doFinal(signature);
            System.out.println(new String(decryptedMessageHash, StandardCharsets.UTF_8));

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] newMessageHash = md.digest(message);
            System.out.println(new String(newMessageHash, StandardCharsets.UTF_8));

            return Arrays.equals(decryptedMessageHash, newMessageHash);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException ignored) {}
        return false;
    }

}
