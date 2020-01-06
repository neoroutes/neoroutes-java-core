package project.neoroutes.diffieHellman;

import org.junit.jupiter.api.Test;

import java.security.InvalidKeyException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EncryptedSessionTest {
    private final EncryptedSession encryptedSession1;
    private final EncryptedSession encryptedSession2;
    private final String sample = "The message to encrypt";
    private String encryptedMessage;

    public EncryptedSessionTest() throws InvalidKeyException {
        DiffieHellman dh1 = new DiffieHellman();
        DiffieHellman dh2 = new DiffieHellman();
        this.encryptedSession1 = new EncryptedSession(dh1, dh2.getPublicKey());
        this.encryptedSession2 = new EncryptedSession(dh2, dh1.getPublicKey());
    }

    @Test
    void encrypt() {
        this.encryptedMessage = encryptedSession1.encrypt(sample);
        assertNotEquals(encryptedMessage, sample);
        System.out.println("Encrypted Text: "+ this.encryptedMessage);
    }

    @Test
    void decrypt() {
        encrypt();
        String decrypt = encryptedSession1.decrypt(this.encryptedMessage);
        assertEquals(decrypt, sample);
        System.out.println("Decrypted Text: "+ decrypt);
    }

    @Test
    void messageEqualityTest(){
        assertEquals(encryptedSession1.encrypt(sample), encryptedSession2.encrypt(sample));
        System.out.println("Keys are equal");
    }
}