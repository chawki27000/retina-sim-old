package luncher;

import architecture.NoC;
import simulation.Event;
import simulation.EventList;
import simulation.EventType;
import simulation_gen.Simulator;
import simulation_gen.Trace;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // NoC initialisation
        NoC noc = new NoC("Network-On-Chip", 3, 1, 10);

        // EventList initialisation
        Simulator.eventList = new EventList();

        // Event Creatiion
        Event ev_1 = new Event(EventType.MESSAGE_SEND, 0, noc.getRouter(0, 0));

        // Event pushing
        Simulator.eventList.push(ev_1);

        System.out.println(Simulator.eventList);

        Simulator s = new Simulator(40);
        s.simulate();

        // Traces Printing
        System.out.println("- - - TRACE - - -");
        for (Trace t : Simulator.traceList) {
            System.out.println(t);
        }
    }
}