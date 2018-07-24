package main;

import architecture.InPort;
import communication.Flit;
import communication.FlitType;
import communication.Packet;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class InportTest {

    static InPort inPort;
    static Flit flit;

    @BeforeAll
    static void inport_initilisation() {
        inPort = new InPort(1, 4, 10);
        flit = new Flit(1, FlitType.HEAD, new Packet(1, 1), 0);
    }

    @Test
    void inport_vc_test() {
        assertEquals(inPort.getFirstFreeVC(), 0);

        inPort.accepteFlit(flit, 0);

        assertEquals(inPort.getFirstFreeVC(), 1);
    }

}
