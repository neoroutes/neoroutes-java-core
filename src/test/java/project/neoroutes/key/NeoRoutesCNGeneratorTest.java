package project.neoroutes.key;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NeoRoutesCNGeneratorTest {

    @Test
    void getCN() {
        String s = UUID.randomUUID().toString();
        assertEquals(new NeoRoutesCNGenerator(s).generate(), "cn="+s+".neoroutes");
    }
}