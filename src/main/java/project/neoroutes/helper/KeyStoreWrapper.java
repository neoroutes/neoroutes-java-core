package project.neoroutes.helper;

import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;

@AllArgsConstructor
public class KeyStoreWrapper {
    private final KeyStore keyStore;
    private final String address;
    private final String password;
    private String publicKeyHash;



    public synchronized void addCertificate(Certificate certificate, String userId) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        keyStore.setCertificateEntry(userId, certificate);
        store();
    }

    private void store() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(address));
        keyStore.store(fileOutputStream, password.toCharArray());
        fileOutputStream.close();
    }

    public void deleteCertificate(String userId) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        keyStore.deleteEntry(userId);
        store();
    }

    public void addCertificate(String base64, String userId) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
        byte[] decodedValue = new Base64().decode(base64);
        addCertificate(decodedValue, userId);
    }
    public void addCertificate(byte[] bytes, String userId) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        InputStream in = new ByteArrayInputStream(bytes);
        X509Certificate cert = (X509Certificate)certFactory.generateCertificate(in);
        addCertificate(cert, userId);
    }

    public Certificate getCertificate(String alias) throws KeyStoreException {
        return keyStore.getCertificate(alias);
    }

    public List<Certificate> getCertificatesList() throws KeyStoreException {
        Enumeration<String> aliases = keyStore.aliases();
        List<Certificate> certificates = new ArrayList<>();
        while (aliases.hasMoreElements()){
            String element = aliases.nextElement();
            certificates.add(keyStore.getCertificate(element));
        }
        return certificates;
    }

    public Map<String, Certificate> getCertificatesMap() throws KeyStoreException {
        Enumeration<String> aliases = keyStore.aliases();
        Map<String, Certificate> certificates = new HashMap<>();
        while (aliases.hasMoreElements()){
            String element = aliases.nextElement();
            certificates.put(element, keyStore.getCertificate(element));
        }
        return certificates;
    }

    public KeyStore getKeyStore(){
        return keyStore;
    }

    public synchronized String getPublicKeyHash(){
        if(publicKeyHash == null){
            try {
                Certificate certificate = getCertificate("main");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                publicKeyHash = new Base64().encodeAsString(md.digest(certificate.getPublicKey().getEncoded()));
            } catch (NoSuchAlgorithmException | KeyStoreException e) {
                throw new RuntimeException(e);
            }
        }

        return publicKeyHash;
    }
}
