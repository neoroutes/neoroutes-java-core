package project.neoroutes.diffieHellman;

import lombok.Getter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

@Getter
public class DiffieHellman {
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
}
