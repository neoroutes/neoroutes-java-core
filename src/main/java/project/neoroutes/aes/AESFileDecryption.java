package project.neoroutes.aes;

import lombok.AllArgsConstructor;
import project.neoroutes.helper.AESSecretKeyGenerator;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@AllArgsConstructor
public class AESFileDecryption {
    private final AESSecretKeyGenerator aesSecretKeyGenerator;
    private final String path;
    private final String exportPath;
    private final IVRepository ivRepository;

    public void decrypt() throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, aesSecretKeyGenerator.generate(), ivRepository.getIvParameterSpec());
        FileInputStream fis = new FileInputStream(path);
        FileOutputStream fos = new FileOutputStream(exportPath);
        byte[] in = new byte[64];
        int read;
        while ((read = fis.read(in)) != -1) {
            byte[] output = cipher.update(in, 0, read);
            if (output != null)
                fos.write(output);
        }

        byte[] output = cipher.doFinal();
        if (output != null)
            fos.write(output);
        fis.close();
        fos.flush();
        fos.close();
    }

}
