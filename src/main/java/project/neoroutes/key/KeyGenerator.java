package project.neoroutes.key;

import lombok.SneakyThrows;
import project.neoroutes.Generator;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyGenerator implements Generator<KeyPair> {
    @SneakyThrows
    @Override
    public KeyPair generate() {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
}
