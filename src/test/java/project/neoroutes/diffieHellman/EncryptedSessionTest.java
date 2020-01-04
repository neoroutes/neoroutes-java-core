package project.neoroutes.diffieHellman;

import org.junit.jupiter.api.Test;
import java.security.InvalidKeyException;
import static org.junit.jupiter.api.Assertions.*;

class EncryptedSessionTest {
    private final EncryptedSession encryptedSession;
    private final String sample = "The message to encrypt";
    private String encryptedMessage;

    public EncryptedSessionTest() throws InvalidKeyException {
        DiffieHellman dh1 = new DiffieHellman();
        DiffieHellman dh2 = new DiffieHellman();
        this.encryptedSession = new EncryptedSession(dh1, dh2.getPublicKey());
    }

    @Test
    void encrypt() {
        this.encryptedMessage = encryptedSession.encrypt(sample);
        assertNotEquals(encryptedMessage, sample);
    }

    @Test
    void decrypt() {
        encrypt();
        String decrypt = encryptedSession.decrypt(encryptedMessage);
        assertEquals(decrypt, sample);
    }
}