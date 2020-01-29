package project.neoroutes.key;

import project.neoroutes.Generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static project.neoroutes.helper.CertificateHelper.generateCertificate;
import static project.neoroutes.helper.CertificateHelper.getValByAttributeTypeFromIssuerDN;

public class KeyStoreGenerator implements Generator<KeyStore> {
    private final CNGenerator cnGenerator;
    private final String password;
    private final File file;
    private boolean fileExists = false;


    public KeyStoreGenerator(CNGenerator cnGenerator, String address, String password) throws IOException {
        this.cnGenerator = cnGenerator;
        this.password = password;
        this.file = new File(address);
        if(!file.exists()) {
            file.createNewFile();
        }else{
            fileExists = true;
        }
    }

    public synchronized KeyStore generate(){
        if(fileExists){
            KeyStore keyStore = getExistingKeyStore();
            if(keyStore != null && isValidKeyStore(keyStore)){
                return keyStore;
            }
        }
        return doGenerate();
    }

    private boolean isValidKeyStore(KeyStore keyStore) {
        try {
            Certificate certificate = keyStore.getCertificate("main");
            X509Certificate x509Certificate = (X509Certificate) certificate;
            String dn = x509Certificate.getIssuerDN().getName();
            String CN = getValByAttributeTypeFromIssuerDN(dn,"CN=");
            if ((cnGenerator.generate().replace("cn=", "").equals(CN))) {
                return true;
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return false;
    }

    private KeyStore getExistingKeyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(new FileInputStream(file), password.toCharArray());
            return keyStore;
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private KeyStore doGenerate(){
        KeyPairGenerator keyPairGenerator = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            Certificate[] chain = {generateCertificate(cnGenerator.generate(), keyPair, 365 * 10, "SHA256withRSA")};
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setKeyEntry("main", keyPair.getPrivate(), password.toCharArray(), chain);
            keyStore.store(fileOutputStream, password.toCharArray());
            this.fileExists  = true;
            return keyStore;
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
