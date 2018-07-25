package simulation_gen;

import architecture.Router;
import architecture.VirtualChannel;
import communication.*;
import output.FileWriter;
import simulation.Event;
import simulation.EventList;
import simulation.EventType;

import java.util.ArrayList;

public class Simulator {

    public static EventList eventList;
    public static int simulationPeriod;
    public static int clock;
    public static ArrayList<Trace> traceList;
    public static ArrayList<Message> messagesList;
    public static ArrayList<MessageInstance> messageInstancesList;

    // Packet and Flit default size
//    public static int PACKET_DEFAULT_SIZE = 64;
    public static int FLIT_DEFAULT_SIZE = 16;

    private int message, id, instance;
    private coordinates dest;

    public Simulator(int simulationPeriod) {
        this.simulationPeriod = simulationPeriod;
        clock = 0;
        traceList = new ArrayList<>();
        messagesList = new ArrayList<>();
        messageInstancesList = new ArrayList<>();
    }

    public void simulate() {

        // local variable
        Flit flit = null;
        VirtualChannel vcAllotted = null;
        Direction direction;
        int result;
        Event event;

        // Simulation Loop
        while (!eventList.isEmpty() && clock < simulationPeriod) {

            Event curr_ev = eventList.pull();
            eventList.removeEvent(curr_ev);

            Router router = curr_ev.getRouter_src();

            clock = curr_ev.getTime();

            switch (curr_ev.getEventType()) {

                case MESSAGE_SEND:
                    router.sendMessage(curr_ev.getMessage_instance(), clock);
                    break;

                case SEND_HEAD_FLIT:
                    // get the direction
                    direction = curr_ev.getDirection();

                    // get Allotted VC
                    vcAllotted = curr_ev.getVcAllotted();

                    // Getting Flit
                    flit = dequeueFlit(router, vcAllotted, direction, clock);

                    result = router.sendHeadFlit(flit, clock + 1);
                    if (result == 0) {
                        if (removeFlit(flit, vcAllotted, direction))
                            System.out.println(flit + " : Credit Updated");
                        else
                            System.out.println(flit + " : Credit Not Updated");
                    } else if (result == 1) {
                        System.out.println(flit + " : Credit Not Updated");
                        event = new Event(EventType.SEND_HEAD_FLIT, (clock + flit.getId() + 1), router, direction, vcAllotted);
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
                    if (result == 0) {
                        if (removeFlit(flit, vcAllotted, direction))
                            System.out.println(flit + " : Credit Updated");
                        else
                            System.out.println(flit + " : Credit Not Updated");
                    } else if (result == 1) {
                        System.out.println(flit + " : Credit Not Updated");
                        event = new Event(EventType.SEND_BODY_TAIL_FLIT, (clock + flit.getId() + 1), router, direction, vcAllotted);
                        Simulator.eventList.push(event);
                    }

                default:
                    break;
            }
        }
    }

    private boolean removeFlit(Flit flit, VirtualChannel vcAllotted, Direction direction) {
        boolean ret = false;

        if (direction == null) {
            ret = vcAllotted.removeFlit(flit);
        } else {
            if (direction == Direction.EAST) {
                ret = vcAllotted.removeFlit(flit);
            } else if (direction == Direction.WEST) {
                ret = vcAllotted.removeFlit(flit);
            } else if (direction == Direction.NORTH) {
                ret = vcAllotted.removeFlit(flit);
            } else if (direction == Direction.SOUTH) {
                ret = vcAllotted.removeFlit(flit);
            }

        }
        return ret;
    }

    private Flit dequeueFlit(Router router, VirtualChannel vcAllotted, Direction direction, int time) {
        Flit flit = null;

        if (direction == null) {
            flit = vcAllotted.dequeueFlit();
        } else {
            if (direction == Direction.EAST)
                flit = vcAllotted.dequeueFlit();
            else if (direction == Direction.WEST)
                flit = vcAllotted.dequeueFlit();
            else if (direction == Direction.NORTH)
                flit = vcAllotted.dequeueFlit();
            else if (direction == Direction.SOUTH)
                flit = vcAllotted.dequeueFlit();

        }

        if (flit.getType() == FlitType.TAIL) {
            releaseVC(vcAllotted, direction);

            if (direction != null)
                FileWriter.log(direction + " VC (" + vcAllotted + ") of " + router + " RELEASED by " + flit + " at : " + time);
        }

        return flit;
    }

    private void releaseVC(VirtualChannel vcAllotted, Direction direction) {

        if (direction == null) {
            return;
        } else {
            if (direction == Direction.EAST)
                vcAllotted.releaseAllottedVC();
            else if (direction == Direction.WEST)
                vcAllotted.releaseAllottedVC();
            else if (direction == Direction.NORTH)
                vcAllotted.releaseAllottedVC();
            else if (direction == Direction.SOUTH)
                vcAllotted.releaseAllottedVC();
        }
    }
}
