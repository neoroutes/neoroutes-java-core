package project.neoroutes.key;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.neoroutes.helper.CertificateHelper.getValByAttributeTypeFromIssuerDN;

class KeyStoreGeneratorTest {
    private final KeyStoreGenerator keyStoreGenerator;
    private final String keyAddress = "/tmp/key.jks";
    private final String keyPass = "123456";
    private final String userId = UUID.randomUUID().toString();
    private final CNGenerator cnGenerator = new NeoRoutesCNGenerator(userId);


    KeyStoreGeneratorTest() throws IOException {
        new File(keyAddress).delete();
        keyStoreGenerator = new KeyStoreGenerator(cnGenerator, keyAddress, keyPass, userId);
    }

    @Test
    void generate() throws KeyStoreException {
        KeyStore keyStore = keyStoreGenerator.generate();
        assertNotNull(keyStore);

        Certificate certificate = keyStore.getCertificate(userId);
        X509Certificate x509Certificate = (X509Certificate) certificate;
        String dn = x509Certificate.getIssuerDN().getName();
        String CN = getValByAttributeTypeFromIssuerDN(dn,"CN=");
        assertEquals(cnGenerator.generate().replace("cn=",""), CN);
    }


}