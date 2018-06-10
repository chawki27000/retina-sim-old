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
        Direction direction;

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
                    router.sendMessage(message, dest, clock);
                    break;

                case SEND_HEAD_FLIT:
                    // get the direction
                    direction = curr_ev.getDirection();

                    // get Allotted VC
                    vcAllotted = curr_ev.getVcAllotted();

                    // Getting Flit
                    if (direction == null) {
                        flit = router.getInLocal().getVclist().get(vcAllotted).dequeueFlit();
                    } else {
                        if (direction == Direction.EAST)
                            flit = router.getInRight().getVclist().get(vcAllotted).dequeueFlit();
                        else if (direction == Direction.WEST)
                            flit = router.getInLeft().getVclist().get(vcAllotted).dequeueFlit();
                        else if (direction == Direction.NORTH)
                            flit = router.getInUp().getVclist().get(vcAllotted).dequeueFlit();
                        else if (direction == Direction.SOUTH)
                            flit = router.getInDown().getVclist().get(vcAllotted).dequeueFlit();

                    }


                    router.sendHeadFlit(flit, clock + 1);

                    System.out.println(flit.afficherHashMap());
                    break;

                case SEND_BODY_FLIT:
                    // get the direction
                    direction = curr_ev.getDirection();

                    // get Allotted VC
                    vcAllotted = curr_ev.getVcAllotted();

                    // Getting Flit
                    if (direction == null) {
                        flit = router.getInLocal().getVclist().get(vcAllotted).dequeueFlit();
                    } else {
                        if (direction == Direction.EAST)
                            flit = router.getInRight().getVclist().get(vcAllotted).dequeueFlit();
                        else if (direction == Direction.WEST)
                            flit = router.getInLeft().getVclist().get(vcAllotted).dequeueFlit();
                        else if (direction == Direction.NORTH)
                            flit = router.getInUp().getVclist().get(vcAllotted).dequeueFlit();
                        else if (direction == Direction.SOUTH)
                            flit = router.getInDown().getVclist().get(vcAllotted).dequeueFlit();

                    }

                    router.sendFlit(flit, clock + 1);

                case SEND_TAIL_FLIT:
                    break;

                default:
                    break;
            }
        }
    }
}
