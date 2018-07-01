package simulation_gen;

import architecture.Router;
import communication.Direction;
import communication.Flit;
import communication.FlitType;
import output.FileWriter;
import simulation.Event;
import simulation.EventList;
import simulation.EventType;

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
        boolean result;
        Event event;

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
                    flit = dequeueFlit(router, vcAllotted, direction, clock);

                    result = router.sendHeadFlit(flit, clock + 1);
                    if (result) {
                        if (removeFlit(flit, router, vcAllotted, direction))
                            System.out.println(flit + " : REMOVED");
                        else
                            System.out.println(flit + " : NOT REMOVED");
                    } else {
                        System.out.println(flit + " : NOT REMOVED");
                        event = new Event(EventType.SEND_HEAD_FLIT, clock + 300, router, direction, vcAllotted);
                        Simulator.eventList.push(event);
                    }

                    break;

                case SEND_BODY_TAIL_FLIT:
                    // get the direction
                    direction = curr_ev.getDirection();

                    // get Allotted VC
                    vcAllotted = curr_ev.getVcAllotted();

                    // Getting Flit
                    flit = dequeueFlit(router, vcAllotted, direction, clock);

                    result = router.sendFlit(flit, clock + 1);
                    if (result) {
                        if (removeFlit(flit, router, vcAllotted, direction))
                            System.out.println(flit + " : REMOVED");
                        else
                            System.out.println(flit + " : NOT REMOVED");
                    } else {
                        System.out.println(flit + " : NOT REMOVED");
                        event = new Event(EventType.SEND_BODY_TAIL_FLIT, clock + 1, router, direction, vcAllotted);
                        Simulator.eventList.push(event);
                    }

                default:
                    break;
            }
        }
    }

    private boolean removeFlit(Flit flit, Router router, int vcAllotted, Direction direction) {
        boolean ret = false;

        if (direction == null) {
            ret = router.getInLocal().getVclist().get(vcAllotted).removeFlit(flit);
        } else {
            if (direction == Direction.EAST){
                ret = router.getInRight().getVclist().get(vcAllotted).removeFlit(flit);
                System.out.println(router.toString() + " ++ " +router.getInRight().getVclist().get(vcAllotted).toString());
            }
            else if (direction == Direction.WEST){
                ret = router.getInLeft().getVclist().get(vcAllotted).removeFlit(flit);
                System.out.println(router.toString() + " ++ " +router.getInLeft().getVclist().get(vcAllotted).toString());
            }
            else if (direction == Direction.NORTH){
                ret = router.getInUp().getVclist().get(vcAllotted).removeFlit(flit);
                System.out.println(router.toString() + " ++ " +router.getInUp().getVclist().get(vcAllotted).toString());
            }
            else if (direction == Direction.SOUTH){
                ret = router.getInDown().getVclist().get(vcAllotted).removeFlit(flit);
                System.out.println(router.toString() + " ++ " +router.getInDown().getVclist().get(vcAllotted).toString());
            }

        }
        return ret;
    }

    private Flit dequeueFlit(Router router, int vcAllotted, Direction direction, int time) {
        Flit flit = null;

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

        if (flit.getType() == FlitType.TAIL) {
            releaseVC(router, vcAllotted, direction);

            if (direction != null)
                FileWriter.log(direction + " VC (" + vcAllotted + ") of " + router + " RELEASED by " + flit + " at : " + time);
        }

        return flit;
    }

    private void releaseVC(Router router, int vcAllotted, Direction direction) {

        if (direction == null) {
            return;
        } else {
            if (direction == Direction.EAST)
                router.getInRight().getVclist().get(vcAllotted).releaseAllottedVC();
            else if (direction == Direction.WEST)
                router.getInLeft().getVclist().get(vcAllotted).releaseAllottedVC();
            else if (direction == Direction.NORTH)
                router.getInUp().getVclist().get(vcAllotted).releaseAllottedVC();
            else if (direction == Direction.SOUTH)
                router.getInDown().getVclist().get(vcAllotted).releaseAllottedVC();
        }
    }
}
