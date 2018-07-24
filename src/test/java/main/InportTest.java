package main;

import architecture.InPort;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class InportTest {

    static InPort inPort;

    @BeforeAll
    static void inport_initilisation() {
        inPort = new InPort(1, 4, 10);
    }

    @Test
    void inport_vc_test() {
        assertEquals(inPort.getFirstFreeVC(), 0);

        inPort.getVclist().get(0).lockAllottedVC();
        assertEquals(inPort.getFirstFreeVC(), 1);

        inPort.getVclist().get(1).lockAllottedVC();
        assertEquals(inPort.getFirstFreeVC(), 2);

        inPort.getVclist().get(2).lockAllottedVC();
        assertEquals(inPort.getFirstFreeVC(), 3);

        inPort.getVclist().get(3).lockAllottedVC();
        assertEquals(inPort.getFirstFreeVC(), -1);

    }

}
