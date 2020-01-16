package project.neoroutes.aes;

import lombok.AllArgsConstructor;
import project.neoroutes.helper.AESSecretKeyGenerator;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@AllArgsConstructor
public class AESFileEncryption {
    private final AESSecretKeyGenerator aesSecretKeyGenerator;
    private final String path;
    private final String exportPath;
    private final IVRepository ivRepository;

    public void encrypt() throws Exception {
        FileInputStream inFile = new FileInputStream(path);
        FileOutputStream outFile = new FileOutputStream(exportPath);

        SecretKey secretKey = aesSecretKeyGenerator.generate();
        SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret, ivRepository.getIvParameterSpec());

        //file encryption
        byte[] input = new byte[64];
        int bytesRead;

        while ((bytesRead = inFile.read(input)) != -1) {
            byte[] output = cipher.update(input, 0, bytesRead);
            if (output != null)
                outFile.write(output);
        }

        byte[] output = cipher.doFinal();
        if (output != null)
            outFile.write(output);

        inFile.close();
        outFile.flush();
        outFile.close();
    }

}
