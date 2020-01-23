package project.neoroutes.serialize;

import org.junit.jupiter.api.Test;
import project.neoroutes.helper.Serializer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializerTest {
    @Test
    public void serializeTest() throws IOException, ClassNotFoundException {
        Serializer<String> stringSerializer = new Serializer<>();
        byte[] bytes = stringSerializer.serialize("Hello");
        String deserialize = stringSerializer.deserialize(bytes);
        assertEquals("Hello", deserialize);
    }
}
