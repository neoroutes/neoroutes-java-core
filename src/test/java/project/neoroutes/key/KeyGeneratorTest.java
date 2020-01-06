package project.neoroutes.key;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class KeyGeneratorTest {
    private final KeyGenerator keyGenerator;
    private final String keyAddress = "/tmp/key.jks";
    private final String keyPass = "123456";
    private final String userId = UUID.randomUUID().toString();

    KeyGeneratorTest() throws IOException {
        keyGenerator = new KeyGenerator(keyAddress, userId, keyPass);
    }

    @Test
    void generate() throws KeyStoreException {
        KeyStore keyStore = keyGenerator.generate();
        assertNotNull(keyStore);

        Certificate certificate = keyStore.getCertificate("main");
        X509Certificate x509Certificate = (X509Certificate) certificate;
        String dn = x509Certificate.getIssuerDN().getName();
        String CN = getValByAttributeTypeFromIssuerDN(dn,"CN=");
        assertEquals(CNGenerator.getCN(userId).replace("cn=", ""), CN);
    }

    private String getValByAttributeTypeFromIssuerDN(String dn, String attributeType) {
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
}