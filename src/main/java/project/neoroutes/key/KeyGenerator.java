package project.neoroutes.key;

import sun.security.x509.*;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

public class KeyGenerator {
    private final FileOutputStream fos;
    private final String password;
    private final String userId;
    private final File file;
    private boolean fileExists = false;


    public KeyGenerator(String address, String userId, String password) throws IOException {
        this.password = password;
        this.userId = userId;
        this.file = new File(address);
        if(!file.exists()) {
            file.createNewFile();
        }else{
            fileExists = true;
        }
        fos = new FileOutputStream(file);
    }

    public KeyStore generate(){
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
            if ((CNGenerator.getCN(userId).replace("cn=", "").equals(CN))) {
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
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            Certificate[] chain = {generateCertificate(CNGenerator.getCN(userId), keyPair, 365 * 10, "SHA256withRSA")};
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setKeyEntry("main", keyPair.getPrivate(), password.toCharArray(), chain);
            keyStore.store(fos, password.toCharArray());
            return keyStore;
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getValByAttributeTypeFromIssuerDN(String dn, String attributeType) {
        String[] dnSplits = dn.split(",");
        for (String dnSplit : dnSplits) {
            if (dnSplit.contains(attributeType)) {
                String[] cnSplits = dnSplit.trim().split("=");
                if(cnSplits[1]!= null) {
                    return cnSplits[1].trim();
                }
            }
        }
        return "";
    }

    private X509Certificate generateCertificate(String dn, KeyPair keyPair, int validity, String sigAlgName) throws GeneralSecurityException, IOException {
        PrivateKey privateKey = keyPair.getPrivate();

        X509CertInfo info = new X509CertInfo();

        Date from = new Date();
        Date to = new Date(from.getTime() + validity * 1000L * 24L * 60L * 60L);

        CertificateValidity interval = new CertificateValidity(from, to);
        BigInteger serialNumber = new BigInteger(64, new SecureRandom());
        X500Name owner = new X500Name(dn);
        AlgorithmId sigAlgId = new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);

        info.set(X509CertInfo.VALIDITY, interval);
        info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(serialNumber));
        info.set(X509CertInfo.SUBJECT, owner);
        info.set(X509CertInfo.ISSUER, owner);
        info.set(X509CertInfo.KEY, new CertificateX509Key(keyPair.getPublic()));
        info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
        info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(sigAlgId));

        // Sign the cert to identify the algorithm that's used.
        X509CertImpl certificate = new X509CertImpl(info);
        certificate.sign(privateKey, sigAlgName);

        // Update the algorithm, and resign.
        sigAlgId = (AlgorithmId) certificate.get(X509CertImpl.SIG_ALG);
        info.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, sigAlgId);
        certificate = new X509CertImpl(info);
        certificate.sign(privateKey, sigAlgName);

        return certificate;
    }
}
