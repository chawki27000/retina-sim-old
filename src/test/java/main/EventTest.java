package main;

import architecture.NoC;
import architecture.Router;
import communication.*;
import org.junit.jupiter.api.*;
import simulation.Event;
import simulation.EventList;
import simulation.EventType;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    static EventList eventList;
    static NoC noc;
    static Router r1, r2, r3, r4;
    static coordinates src;
    static coordinates dest;
    static Message message;
    static MessageInstance instance;
    static Event event;

    @BeforeAll
    static void initialisation() {
        eventList = new EventList();
        noc = new NoC("NoC", 8, 4, 10);
        r1 = NoC.routerMatrix[0][0];
        r2 = NoC.routerMatrix[0][7];
        r3 = NoC.routerMatrix[7][0];
        r4 = NoC.routerMatrix[7][7];
    }

    @BeforeEach
    void event_instance() {
        coordinates src = new coordinates(0, 0);
        coordinates dest = new coordinates(7, 7);
        Message message = new Message(0, 15, new Packet(0, 4), src, dest);
        MessageInstance instance = new MessageInstance(message, 1, 0);
        event = new Event(EventType.MESSAGE_SEND, 0, r1, r2, instance);
    }

    @Test
    void create_event_test() {
        eventList.push(event);
        assertFalse(eventList.isEmpty());
        eventList.removeEvent(event);
        assertTrue(eventList.isEmpty());
    }

    @Test
    void event_shift_test() {
        assertEquals(event.getTime(), 0);
        event.shift();
        assertEquals(event.getTime(), 1);
    }

    @Test
    void event_list_shift_test() {
        Direction direction = Direction.EAST;
        int vc_allotted = 2;

//        Event event1 = new Event(EventType.SEND_BODY_TAIL_FLIT, 1, r1, direction, vc_allotted);
//        Event event2 = new Event(EventType.SEND_BODY_TAIL_FLIT, 2, r1, direction, vc_allotted);
//        Event event3 = new Event(EventType.SEND_BODY_TAIL_FLIT, 3, r1, direction, vc_allotted);
//        eventList.push(event1);
//        eventList.push(event2);
//        eventList.push(event3);
//
//        eventList.eventShift(r1, vc_allotted, direction);
//
//        assertEquals(event1.getTime(), 2);
//        assertEquals(event2.getTime(), 3);
//        assertEquals(event3.getTime(), 4);
    }
}
