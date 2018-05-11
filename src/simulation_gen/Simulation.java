package simulation_gen;

import simulation.Event;
import simulation.EventList;

public class Simulation {

    public static EventList eventList;
    private int simulationPeriod;
    public static int clock;

    public Simulation(int simulationPeriod) {
        this.simulationPeriod = simulationPeriod;
    }

    public void simulate() {

        while (!eventList.isEmpty() && clock < simulationPeriod) {

            Event curr_ev = eventList.pull();
            eventList.removeEvent(curr_ev);

            switch (curr_ev.getEventType()) {
                case MESSAGE_SEND:
                    break;

                case SEND_FLIT:
                    break;

                case RECEIVE_FLIT:
                    break;

                default:
                    break;
            }
        }
    }
}
