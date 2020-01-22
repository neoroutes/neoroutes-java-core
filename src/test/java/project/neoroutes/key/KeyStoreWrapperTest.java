package project.neoroutes.key;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import project.neoroutes.helper.CertificateHelper;
import project.neoroutes.helper.KeyStoreWrapper;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KeyStoreWrapperTest {
    private final KeyStoreGenerator keyStoreGenerator;
    private final KeyStore keyStore;
    private final String keyAddress = "/tmp/key2.jks";
    private final String keyPass = "123456";
    private final String userId = UUID.randomUUID().toString();
    private final CNGenerator cnGenerator = new NeoRoutesCNGenerator(userId);


    public KeyStoreWrapperTest() throws IOException {
        deleteFile();
        keyStoreGenerator = new KeyStoreGenerator(cnGenerator, keyAddress, keyPass, userId);
        keyStore = keyStoreGenerator.generate();
    }

    @Test
    public void addCertificate1() throws GeneralSecurityException, IOException {
        deleteFile();
        String uuid = UUID.randomUUID().toString();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        X509Certificate certificate = CertificateHelper.generateCertificate(new NeoRoutesCNGenerator(uuid).generate(), keyPair, 365, "SHA256withRSA");

        KeyStoreWrapper keyStoreWrapper = new KeyStoreWrapper(keyStore, keyAddress, keyPass);
        keyStoreWrapper.addCertificate(certificate, uuid);

        KeyStoreGenerator keyStoreGenerator2 = new KeyStoreGenerator(cnGenerator, keyAddress, keyPass, userId);
        KeyStore keyStore2 = keyStoreGenerator2.generate();
        KeyStoreWrapper keyStoreWrapper2 = new KeyStoreWrapper(keyStore2, keyAddress, keyPass);
        assertEquals(keyStoreWrapper2.getCertificatesList().size(), 2);
    }


    @Test
    public void addCertificate2() throws GeneralSecurityException, IOException {
        deleteFile();
        String uuid = UUID.randomUUID().toString();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        X509Certificate certificate = CertificateHelper.generateCertificate(new NeoRoutesCNGenerator(uuid).generate(), keyPair, 365, "SHA256withRSA");

        KeyStoreWrapper keyStoreWrapper = new KeyStoreWrapper(keyStore, keyAddress, keyPass);
        keyStoreWrapper.addCertificate(certificate.getEncoded(), uuid);

        KeyStoreGenerator keyStoreGenerator2 = new KeyStoreGenerator(cnGenerator, keyAddress, keyPass, userId);
        KeyStore keyStore2 = keyStoreGenerator2.generate();
        KeyStoreWrapper keyStoreWrapper2 = new KeyStoreWrapper(keyStore2, keyAddress, keyPass);
        assertEquals(keyStoreWrapper2.getCertificatesList().size(), 2);
    }

    @Test
    public void addCertificate3() throws GeneralSecurityException, IOException {
        deleteFile();
        String uuid = UUID.randomUUID().toString();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        X509Certificate certificate = CertificateHelper.generateCertificate(new NeoRoutesCNGenerator(uuid).generate(), keyPair, 365, "SHA256withRSA");

        KeyStoreWrapper keyStoreWrapper = new KeyStoreWrapper(keyStore, keyAddress, keyPass);
        keyStoreWrapper.addCertificate(new String(new Base64().encode(certificate.getEncoded()), "UTF-8"), uuid);

        KeyStoreGenerator keyStoreGenerator2 = new KeyStoreGenerator(cnGenerator, keyAddress, keyPass, userId);
        KeyStore keyStore2 = keyStoreGenerator2.generate();
        KeyStoreWrapper keyStoreWrapper2 = new KeyStoreWrapper(keyStore2, keyAddress, keyPass);
        assertEquals(keyStoreWrapper2.getCertificatesList().size(), 2);
    }

    @Test
    public void addCertificate4() throws GeneralSecurityException, IOException {
        deleteFile();
        String uuid = UUID.randomUUID().toString();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        X509Certificate certificate = CertificateHelper.generateCertificate(new NeoRoutesCNGenerator(uuid).generate(), keyPair, 365, "SHA256withRSA");

        KeyStoreWrapper keyStoreWrapper = new KeyStoreWrapper(keyStore, keyAddress, keyPass);
        keyStoreWrapper.addCertificate(certificate, uuid);

        KeyStoreGenerator keyStoreGenerator2 = new KeyStoreGenerator(cnGenerator, keyAddress, keyPass, userId);
        KeyStore keyStore2 = keyStoreGenerator2.generate();
        KeyStoreWrapper keyStoreWrapper2 = new KeyStoreWrapper(keyStore2, keyAddress, keyPass);
        System.out.println(keyStoreWrapper2.getCertificatesMap());
        assertTrue(keyStoreWrapper2.getCertificatesMap().containsKey(uuid));
    }

    @Test
    public void deleteCertificate() throws GeneralSecurityException, IOException {
        deleteFile();
        String uuid = UUID.randomUUID().toString();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        X509Certificate certificate = CertificateHelper.generateCertificate(new NeoRoutesCNGenerator(uuid).generate(), keyPair, 365, "SHA256withRSA");

        KeyStoreWrapper keyStoreWrapper = new KeyStoreWrapper(keyStore, keyAddress, keyPass);
        keyStoreWrapper.addCertificate(certificate, uuid);

        keyStoreWrapper.deleteCertificate(uuid);
        assertEquals(1, keyStoreWrapper.getCertificatesList().size());
    }

    @Test
    public void getCertificate() throws GeneralSecurityException, IOException {
        deleteFile();
        String uuid = UUID.randomUUID().toString();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        X509Certificate certificate = CertificateHelper.generateCertificate(new NeoRoutesCNGenerator(uuid).generate(), keyPair, 365, "SHA256withRSA");
        KeyStoreWrapper keyStoreWrapper = new KeyStoreWrapper(keyStore, keyAddress, keyPass);
        keyStoreWrapper.addCertificate(certificate, uuid);

        Certificate certificate1 = keyStoreWrapper.getCertificate(uuid);
        assertEquals(certificate, certificate1);
    }

    private void deleteFile(){
        new File(keyAddress).delete();
    }
}
