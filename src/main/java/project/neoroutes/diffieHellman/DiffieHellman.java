package project.neoroutes.diffieHellman;

import lombok.Getter;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Objects;

@Getter
public class DiffieHellman implements Serializable {
    public static final long serialVersionUID = 100L;
    private PublicKey publicKey;
    private KeyPair keyPair;

    public DiffieHellman(KeyPair keyPair){
        this.keyPair = keyPair;
        publicKey = keyPair.getPublic();
    }

    public DiffieHellman() {
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance("EC");
            kpg.initialize(256);
            keyPair = kpg.generateKeyPair();
            publicKey = keyPair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiffieHellman that = (DiffieHellman) o;
        return Arrays.equals(publicKey.getEncoded(), that.publicKey.getEncoded()) &&
                Arrays.equals(keyPair.getPrivate().getEncoded(), that.keyPair.getPrivate().getEncoded());
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicKey, keyPair);
    }
}
