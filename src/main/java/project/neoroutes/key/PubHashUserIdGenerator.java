package project.neoroutes.key;

import org.apache.commons.codec.binary.Base64;
import project.neoroutes.Generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.stream.Stream;

public class PubHashUserIdGenerator implements Generator<String> {
    private final PublicKey publicKey;
    private String uuid;
    private String filePath;

    public PubHashUserIdGenerator(String filePath, PublicKey publicKey) {
        this.publicKey = publicKey;
        this.filePath = filePath;
        init();
    }

    private void init(){
        File file = new File(filePath);
        if(file.exists()){
            StringBuilder stringBuilder = new StringBuilder();
            try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
                stream.forEach(stringBuilder::append);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.uuid = stringBuilder.toString();
        }else{
            this.uuid = doGenerate();
            try (PrintWriter out = new PrintWriter(filePath)) {
                out.println(uuid);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String doGenerate() {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(publicKey.getEncoded());
            return new String(new Base64().encode(hash), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generate() {
        return uuid;
    }
}
