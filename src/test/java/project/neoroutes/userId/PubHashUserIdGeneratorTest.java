package project.neoroutes.userId;

import org.junit.jupiter.api.Test;
import project.neoroutes.key.KeyGenerator;
import project.neoroutes.key.PubHashUserIdGenerator;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PubHashUserIdGeneratorTest {

    @Test
    void generate() {
        KeyGenerator keyGenerator = new KeyGenerator();
        KeyPair keyPair = keyGenerator.generate();


        PubHashUserIdGenerator pubHashUserIdGenerator = new PubHashUserIdGenerator("user.pub.id",keyPair.getPublic());
        String generate1 = pubHashUserIdGenerator.generate();
        String generate2 = pubHashUserIdGenerator.generate();
        System.out.println(generate1);

        assertEquals(generate1, generate2);
    }
}