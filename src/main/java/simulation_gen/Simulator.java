package simulation_gen;

import architecture.Router;
import communication.Direction;
import communication.Flit;
import simulation.Event;
import simulation.EventList;

import java.util.ArrayList;

public class Simulator {

    public static EventList eventList;
    private int simulationPeriod;
    public static int clock;
    public static ArrayList<Trace> traceList;

    private int message;
    private int[] dest;

    public Simulator(int simulationPeriod) {
        this.simulationPeriod = simulationPeriod;
        clock = 0;
        traceList = new ArrayList<>();
    }

    public void simulate() {

        // local variable
        Flit flit = null;
        int vcAllotted = -1;

        // Simulation Loop
        while (!eventList.isEmpty() && clock < simulationPeriod) {

            Event curr_ev = eventList.pull();
            eventList.removeEvent(curr_ev);

            Router router = curr_ev.getRouter_src();

            clock = curr_ev.getTime();

            switch (curr_ev.getEventType()) {

                case MESSAGE_SEND:
                    message = curr_ev.getMessageSize();
                    dest = curr_ev.getRouter_dest();
                    router.sendMessage(message, dest);
                    break;

                case SEND_FLIT:
                    if (router.isSendingBufferEmpty()) {
                        break;
                    }

                    flit = router.sendingBufferPull();

                    router.sendHeadFlit(flit);

                    break;

                case FORWARD_FLIT:
                    // get the direction
                    Direction direction = curr_ev.getDirection();

                    // get Allotted VC
                    vcAllotted = curr_ev.getVcAllotted();

                    if (direction == Direction.EAST)
                        flit = router.getInRight().getVclist().get(vcAllotted).dequeueFlit();
                    else if (direction == Direction.WEST)
                        flit = router.getInLeft().getVclist().get(vcAllotted).dequeueFlit();
                    else if (direction == Direction.NORTH)
                        flit = router.getInUp().getVclist().get(vcAllotted).dequeueFlit();
                    else if (direction == Direction.SOUTH)
                        flit = router.getInDown().getVclist().get(vcAllotted).dequeueFlit();

                    router.sendFlit(flit, vcAllotted);

                    break;

                default:
                    break;
            }
        }
    }
}
