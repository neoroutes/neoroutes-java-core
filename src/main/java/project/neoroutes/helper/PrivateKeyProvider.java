package project.neoroutes.helper;

import lombok.AllArgsConstructor;

import java.security.*;

@AllArgsConstructor
public class PrivateKeyProvider {
    private final KeyStore keyStore;
    private final String password;
    private final String userUUID;

    public PrivateKey getPrivateKey() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        return (PrivateKey) keyStore.getKey(userUUID, password.toCharArray());
    }
}
