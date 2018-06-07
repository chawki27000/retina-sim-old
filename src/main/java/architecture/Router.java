package architecture;


import communication.*;
import simulation.Event;
import simulation.EventType;
import simulation_gen.Simulator;
import simulation_gen.Trace;

import java.util.ArrayList;

public class Router implements Routing {

    // Active Object attribute
    public int x_dest, y_dest, bits;

    private ArrayList<Flit> sendingBuffer;

    int x, y;
    private InPort inLeft, inRight, inUp, inDown;
    private OutPort oLeft, oRight, oUp, oDown;
    public PE pe;

    public Router(String name, int x, int y, InPort inLeft,
                  InPort inRight, InPort inUp,
                  InPort inDown, OutPort oLeft,
                  OutPort oRight, OutPort oUp,
                  OutPort oDown, PE pe) {

        this.x = x;
        this.y = y;
        this.inLeft = inLeft;
        this.inRight = inRight;
        this.inUp = inUp;
        this.inDown = inDown;
        this.oLeft = oLeft;
        this.oRight = oRight;
        this.oUp = oUp;
        this.oDown = oDown;
        this.pe = pe;

        // Sending Buffer Initialisation
        sendingBuffer = new ArrayList<>();

    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public InPort getInLeft() {
        return inLeft;
    }

    public void setInLeft(InPort inLeft) {
        this.inLeft = inLeft;
    }

    public InPort getInRight() {
        return inRight;
    }

    public void setInRight(InPort inRight) {
        this.inRight = inRight;
    }

    public InPort getInUp() {
        return inUp;
    }

    public void setInUp(InPort inUp) {
        this.inUp = inUp;
    }

    public InPort getInDown() {
        return inDown;
    }

    public void setInDown(InPort inDown) {
        this.inDown = inDown;
    }

    public OutPort getoLeft() {
        return oLeft;
    }

    public void setoLeft(OutPort oLeft) {
        this.oLeft = oLeft;
    }

    public OutPort getoRight() {
        return oRight;
    }

    public void setoRight(OutPort oRight) {
        this.oRight = oRight;
    }

    public OutPort getoUp() {
        return oUp;
    }

    public void setoUp(OutPort oUp) {
        this.oUp = oUp;
    }

    public OutPort getoDown() {
        return oDown;
    }

    public void setoDown(OutPort oDown) {
        this.oDown = oDown;
    }

    /**
     * Sending message function
     * It's the function which can initiate a sending data
     * from a router to another
     *
     * @param bits
     */
    public void sendMessage(int bits, int[] dest) {

        // New message creation
        Message message = new Message(bits);
        message.setDestinationInfo(dest);

        // Slicing message in several packets
        ArrayList<Packet> packetList = message.getPacketList();

        for (Packet packet : packetList) {

            // To Output port
            sendPacket(packet);
        }

    }

    /**
     * prepare a packet to its next router
     *
     * @param packet
     * @return
     */
    private boolean sendPacket(Packet packet) {

        int dx, dy;

        // Get header flit to extract Dest router
        Flit headerFlit = packet.getHeaderFlit();

        // Extract header flit information
        dx = headerFlit.getDx();
        dy = headerFlit.getDy();


        System.out.println("Sending packet : " + packet.getId() + " From : (" +
                x + "," + y + ")" + " TO : (" + dx + "," + dy + ")");


        // Slicing each packet in several flits
        ArrayList<Flit> flitList = packet.getFlitList();

        // Flit preparation
        for (Flit flit : flitList) {
            // Flit pushing in PE
            sendingBuffer.add(flit);
            // set coordinates in others flit
            flit.setDxDy(dx, dy);
            // Event pushing
            Event event = new Event(EventType.SEND_FLIT, Simulator.clock, this);
            Simulator.eventList.push(event);
        }


        return false;
    }

    public void sendFlit(Flit flit, int vcAllotted) {

        int dx, dy;
        dx = flit.getDx();
        dy = flit.getDy();

        // Get Routing Direction
        Direction direction = getRoutingDirection(dx, dy);

        if (direction == null) {
            System.out.println("Destination Reached");
            return;
        }


        Event event;

        if (direction == Direction.WEST) {
            System.out.println("Flit " + flit.getType() + " sended in WEST");

            oLeft.getDest().getInRight().accepteFlit(flit, vcAllotted);

            // Tracing
            Trace t = new Trace(flit, this, oLeft.getDest(), vcAllotted, Simulator.clock);
            Simulator.traceList.add(t);

            // Event Simulation Push
            event = new Event(EventType.FORWARD_FLIT, Simulator.clock + 1, oLeft.getDest(), Direction.EAST, vcAllotted);
            Simulator.eventList.push(event);


        } else if (direction == Direction.EAST) {
            System.out.println("Flit " + flit.getType() + " sended in EAST");

            oRight.getDest().getInLeft().accepteFlit(flit, vcAllotted);

            // Tracing
            Trace t = new Trace(flit, this, oRight.getDest(), vcAllotted, Simulator.clock);
            Simulator.traceList.add(t);

            // Event Simulation Push
            event = new Event(EventType.FORWARD_FLIT, Simulator.clock + 1, oRight.getDest(), Direction.WEST, vcAllotted);
            Simulator.eventList.push(event);

        } else if (direction == Direction.NORTH) {
            System.out.println("Flit " + flit.getType() + " sended in NORTH");

            oUp.getDest().getInDown().accepteFlit(flit, vcAllotted);

            // Tracing
            Trace t = new Trace(flit, this, oUp.getDest(), vcAllotted, Simulator.clock);
            Simulator.traceList.add(t);

            // Event Simulation Push
            event = new Event(EventType.FORWARD_FLIT, Simulator.clock + 1, oUp.getDest(), Direction.SOUTH, vcAllotted);
            Simulator.eventList.push(event);


        } else if (direction == Direction.SOUTH) {
            System.out.println("Flit " + flit.getType() + " sended in SOUTH");

            oDown.getDest().getInUp().accepteFlit(flit, vcAllotted);

            // Tracing
            Trace t = new Trace(flit, this, oDown.getDest(), vcAllotted, Simulator.clock);
            Simulator.traceList.add(t);

            // Event Simulation Push
            event = new Event(EventType.FORWARD_FLIT, Simulator.clock + 1, oDown.getDest(), Direction.NORTH, vcAllotted);
            Simulator.eventList.push(event);
        }
    }

    public void sendHeadFlit(Flit flit) {

        int dx, dy;
        dx = flit.getDx();
        dy = flit.getDy();

        // Get Routing Direction
        Direction direction = getRoutingDirection(dx, dy);

        if (direction == null) {
            System.out.println("Destination Reached");
            return;
        }

        // Free VC ID
        int freeVC = -1;

        Event event;

        if (direction == Direction.WEST) {
            System.out.println("Flit " + flit.getType() + " sended in WEST");
            freeVC = oLeft.getDest().getInRight().getFirstFreeVC();

            oLeft.getDest().getInRight().accepteFlit(flit, freeVC);

            // Tracing
            Trace t = new Trace(flit, this, oLeft.getDest(), freeVC, Simulator.clock);
            Simulator.traceList.add(t);

            // Event Simulation Push
            event = new Event(EventType.FORWARD_FLIT, Simulator.clock + 1, oLeft.getDest(), Direction.EAST, freeVC);
            Simulator.eventList.push(event);


        } else if (direction == Direction.EAST) {
            System.out.println("Flit " + flit.getType() + " sended in EAST");
            freeVC = oRight.getDest().getInLeft().getFirstFreeVC();

            oRight.getDest().getInLeft().accepteFlit(flit, freeVC);

            // Tracing
            Trace t = new Trace(flit, this, oRight.getDest(), freeVC, Simulator.clock);
            Simulator.traceList.add(t);

            // Event Simulation Push
            event = new Event(EventType.FORWARD_FLIT, Simulator.clock + 1, oRight.getDest(), Direction.WEST, freeVC);
            Simulator.eventList.push(event);

        } else if (direction == Direction.NORTH) {
            System.out.println("Flit " + flit.getType() + " sended in NORTH");
            freeVC = oUp.getDest().getInDown().getFirstFreeVC();

            oUp.getDest().getInDown().accepteFlit(flit, freeVC);

            // Tracing
            Trace t = new Trace(flit, this, oUp.getDest(), freeVC, Simulator.clock);
            Simulator.traceList.add(t);

            // Event Simulation Push
            event = new Event(EventType.FORWARD_FLIT, Simulator.clock + 1, oUp.getDest(), Direction.SOUTH, freeVC);
            Simulator.eventList.push(event);


        } else if (direction == Direction.SOUTH) {
            System.out.println("Flit " + flit.getType() + " sended in SOUTH");
            freeVC = oDown.getDest().getInUp().getFirstFreeVC();

            oDown.getDest().getInUp().accepteFlit(flit, freeVC);

            // Tracing
            Trace t = new Trace(flit, this, oDown.getDest(), freeVC, Simulator.clock);
            Simulator.traceList.add(t);

            // Event Simulation Push
            event = new Event(EventType.FORWARD_FLIT, Simulator.clock + 1, oDown.getDest(), Direction.NORTH, freeVC);
            Simulator.eventList.push(event);
        }

        // hold VC from the head flit
        if (flit.getType() == FlitType.HEAD) {
            oDown.getDest().getInRight().getVclist().get(freeVC).lockAllottedVC();
            System.out.println("VC : (" + freeVC + ") has been locked");
        }
//        else if (flit.getType() == FlitType.TAIL) {
//            oDown.getDest().getInRight().getVclist().get(freeVC).releaseAllottedVC();
//            System.out.println("VC : (" + freeVC + ") has been released");
//        }
    }

    public Direction getRoutingDirection(int dx, int dy) {
        // On X axe
        // By the West
        if (y > dy) {
            return Direction.WEST;
        }
        // By the East
        else if (y < dy) {
            return Direction.EAST;
        }
        // On Y axe
        else {
            // By the North
            if (x > dx) {
                return Direction.NORTH;
            }
            // By the South
            else if (x < dx) {
                return Direction.SOUTH;
            } else {
                // Destination Reached
                return null;
            }
        }
    }


    public boolean isSendingBufferEmpty() {
        return sendingBuffer.isEmpty();
    }

    public Flit sendingBufferPull() {
        if (sendingBuffer.isEmpty())
            return null;

        Flit flit = sendingBuffer.get(0);
        sendingBuffer.remove(flit);
        return flit;
    }
}