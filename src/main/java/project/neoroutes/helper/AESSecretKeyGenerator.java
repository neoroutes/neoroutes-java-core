package project.neoroutes.helper;

import lombok.AllArgsConstructor;
import project.neoroutes.Generator;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@AllArgsConstructor
public class AESSecretKeyGenerator implements Generator<SecretKey> {
    private final String password;

    @Override
    public SecretKey generate() {
        try {
            byte[] key = password.getBytes("UTF-8");
            System.out.println(key.length);
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            System.out.println(key.length);
            System.out.println(new String(key,"UTF-8"));
            return new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
