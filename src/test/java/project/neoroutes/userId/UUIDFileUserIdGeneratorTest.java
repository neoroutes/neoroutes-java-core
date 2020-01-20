package project.neoroutes.userId;

import org.junit.jupiter.api.Test;
import project.neoroutes.key.UUIDFileUserIdGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UUIDFileUserIdGeneratorTest {
    private final String path = "user.uuid";

    @Test
    public void generate(){
        UUIDFileUserIdGenerator uuidFileUserIdGenerator1 = new UUIDFileUserIdGenerator(path);
        UUIDFileUserIdGenerator uuidFileUserIdGenerator2 = new UUIDFileUserIdGenerator(path);
        System.out.println(uuidFileUserIdGenerator1.generate());
        System.out.println(uuidFileUserIdGenerator2.generate());
        assertEquals(uuidFileUserIdGenerator1.generate(), uuidFileUserIdGenerator1.generate());
        assertEquals(uuidFileUserIdGenerator1.generate(), uuidFileUserIdGenerator2.generate());
    }

}
