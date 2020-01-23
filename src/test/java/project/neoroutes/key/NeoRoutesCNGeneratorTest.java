package project.neoroutes.key;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NeoRoutesCNGeneratorTest {

    @Test
    public void getCN() {
        String s = UUID.randomUUID().toString();
        assertEquals(new NeoRoutesCNGenerator(s).generate(), "cn="+s+".neoroutes");
    }
}