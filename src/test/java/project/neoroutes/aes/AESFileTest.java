package project.neoroutes.aes;

import org.junit.jupiter.api.Test;
import project.neoroutes.helper.AESSecretKeyGenerator;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AESFileTest {
    private final AESSecretKeyGenerator aesSecretKeyGenerator;
    private final IVRepository ivRepository;
    private String plainText = "This is a sample plain text!";

    public AESFileTest() {
        this.aesSecretKeyGenerator = new AESSecretKeyGenerator("mypassword");
        ivRepository = new IVRepository() {};
    }

    @Test
    public void encryptionDecryptionTest() throws Exception {
        generatePlainTextFile("plain.txt", plainText);
        AESFileEncryption aesFileEncryption = new AESFileEncryption(aesSecretKeyGenerator, "plain.txt", "enc.txt", ivRepository);
        aesFileEncryption.encrypt();
        AESFileDecryption aesFileDecryption = new AESFileDecryption(aesSecretKeyGenerator, "enc.txt", "decrypted.txt", ivRepository);
        aesFileDecryption.decrypt();
        String results = readFile("decrypted.txt");
        assertEquals(plainText, results);
    }

    private void generatePlainTextFile(String path, String s) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(path));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(s);
        bw.close();
    }

    public String readFile(String path) throws IOException {
        FileInputStream fstream = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        StringBuilder result = new StringBuilder();
        while ((strLine = br.readLine()) != null)   {
            // Print the content on the console
            result.append(strLine);
        }

        fstream.close();
        return result.toString();
    }

}
