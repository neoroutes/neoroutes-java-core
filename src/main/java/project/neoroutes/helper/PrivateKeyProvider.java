package project.neoroutes.helper;

import lombok.AllArgsConstructor;

import java.security.*;

@AllArgsConstructor
public class PrivateKeyProvider {
    private final KeyStore keyStore;
    private final String password;

    public PrivateKey getPrivateKey() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        return (PrivateKey) keyStore.getKey("main", password.toCharArray());
    }
}
