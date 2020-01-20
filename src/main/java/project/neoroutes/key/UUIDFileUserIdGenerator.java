package project.neoroutes.key;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

public class UUIDFileUserIdGenerator implements UserIdGenerator {
    private final String filePath;
    private String uuid;

    public UUIDFileUserIdGenerator(String filePath) {
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
            this.uuid = UUID.randomUUID().toString();
            try (PrintWriter out = new PrintWriter(filePath)) {
                out.println(uuid);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public String generate() {
        return this.uuid;
    }
}
