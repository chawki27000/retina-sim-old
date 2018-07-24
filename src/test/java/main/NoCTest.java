package main;

import architecture.NoC;
import architecture.Router;
import communication.coordinates;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class NoCTest {

    static NoC noc;
    static Router r1, r2, r3, r4;

    @BeforeAll
    static void noc_initialisation() {
        noc = new NoC("NoC", 8, 4, 10);
        r1 = NoC.routerMatrix[0][0];
        r2 = NoC.routerMatrix[0][7];
        r3 = NoC.routerMatrix[7][0];
        r4 = NoC.routerMatrix[7][7];
    }

    @Test
    void router_initialisation_test() {
        assertNotNull(noc.getRouter(new coordinates(7, 7)));
        assertNotNull(noc.getRouter(new coordinates(0, 0)));
        assertEquals(NoC.routerMatrix.length, 8);
    }

    @Test
    void router_link_edge_test() {
        assertNull(r1.getoUp().getDest());
        assertNull(r1.getoLeft().getDest());
        assertNotNull(r1.getoDown().getDest());
        assertNotNull(r1.getoRight().getDest());

        assertNull(r2.getoUp().getDest());
        assertNotNull(r2.getoLeft().getDest());
        assertNotNull(r2.getoDown().getDest());
        assertNull(r2.getoRight().getDest());

        assertNotNull(r3.getoUp().getDest());
        assertNull(r3.getoLeft().getDest());
        assertNull(r3.getoDown().getDest());
        assertNotNull(r3.getoRight().getDest());

        assertNotNull(r4.getoUp().getDest());
        assertNotNull(r4.getoLeft().getDest());
        assertNull(r4.getoDown().getDest());
        assertNull(r4.getoRight().getDest());
    }

    @Test
    void router_port_test() {
        assertNotNull(r1.getoDown());
        assertNotNull(r1.getoUp());
        assertNotNull(r1.getoLeft());
        assertNotNull(r1.getoRight());

        assertNotNull(r1.getInDown());
        assertNotNull(r1.getInUp());
        assertNotNull(r1.getInLeft());
        assertNotNull(r1.getInRight());

        assertEquals(r1.getInUp().getVclist().size(), 4);
        assertEquals(r1.getInUp().getVclist().get(0).getSize(), 10);

        assertEquals(r1.getInLocal().getVclist().size(), 100);
        assertEquals(r1.getInLocal().getVclist().get(0).getSize(), 40);
    }
}
