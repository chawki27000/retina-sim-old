package simulation_gen;

import architecture.Router;
import communication.Direction;
import communication.Flit;
import simulation.Event;
import simulation.EventList;

public class Simulator {

    public static EventList eventList;
    private int simulationPeriod;
    public static int clock;

    public Simulator(int simulationPeriod) {
        this.simulationPeriod = simulationPeriod;
        clock = 0;
    }

    public void simulate() {

        while (!eventList.isEmpty() && clock < simulationPeriod) {

            Event curr_ev = eventList.pull();
            eventList.removeEvent(curr_ev);

            Router router = curr_ev.getRouter();


            switch (curr_ev.getEventType()) {

                case MESSAGE_SEND:
                    router.sendMessage(64, new int[]{2,2});
                    break;

                case SEND_FLIT:
                    if (router.pe.isEmpty()) {
                        break;
                    }

                    Flit flit = router.pe.pullFlit();

                    Direction direction = curr_ev.getDirection();
                    router.sendFlit(flit, direction);
                    clock++; // ## clock ##
                    break;

                case RECEIVE_FLIT:
                    break;

                default:
                    break;
            }
        }
    }
}
