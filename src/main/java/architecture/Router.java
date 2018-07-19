package architecture;


import communication.*;
import simulation.Event;
import simulation.EventType;
import simulation_gen.Simulator;
import simulation_gen.Trace;

import java.util.ArrayList;

public class Router implements IRouting {

    // Active Object attribute
    public int x_dest, y_dest, bits;

    int x, y, idx;
    private InPort inLeft, inRight, inUp, inDown, inLocal;
    private OutPort oLeft, oRight, oUp, oDown;
    public PE pe;

    public Router(int idx, String name, int x, int y, InPort inLeft,
                  InPort inRight, InPort inUp,
                  InPort inDown, InPort inLocal,
                  OutPort oLeft, OutPort oRight,
                  OutPort oUp, OutPort oDown, PE pe) {

        this.idx = idx;
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
        this.inLocal = inLocal;
        this.pe = pe;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public InPort getInLeft() {
        return inLeft;
    }

    public InPort getInRight() {
        return inRight;
    }


    public InPort getInUp() {
        return inUp;
    }

    public InPort getInLocal() {
        return inLocal;
    }


    public InPort getInDown() {
        return inDown;
    }

    public OutPort getoLeft() {
        return oLeft;
    }


    public OutPort getoRight() {
        return oRight;
    }

    public OutPort getoUp() {
        return oUp;
    }

    public OutPort getoDown() {
        return oDown;
    }

    public int getIdx() {
        return idx;
    }

    /**
     * Sending message function
     * It's the function which can initiate a sending data
     * from a router to another
     *
     * @param bits
     */
    public void sendMessage(int id, int instance, int bits, int[] dest, int time) {

        // New message creation
        Message message = new Message(id, instance, bits, new int[]{this.getX(), this.getY()}, dest);
        message.setDestinationInfo(dest);

        // Slicing message in several packets
        ArrayList<Packet> packetList = message.getPacketList();

        for (Packet packet : packetList) {

            // To Output port
            sendPacket(packet, time);
        }

    }

    /**
     * prepare a packet to its next router
     *
     * @param packet
     * @return
     */
    private boolean sendPacket(Packet packet, int time) {

        int dx, dy;
        Event event = null;
        int clock = time;

        // Get header flit to extract Dest router
        Flit headerFlit = packet.getHeaderFlit();

        // Extract header flit information
        dx = headerFlit.getDx();
        dy = headerFlit.getDy();


        System.out.println("Sending packet : " + packet.getId() + " From : (" +
                x + "," + y + ")" + " TO : (" + dx + "," + dy + ") at : " + time);


        // Slicing each packet in several flits
        ArrayList<Flit> flitList = packet.getFlitList();

        // get the first free VC in InLocal and hold it
        int freeVC = inLocal.getFirstFreeVC();
        inLocal.getVclist().get(freeVC).lockAllottedVC();

        // Flit preparation
        for (Flit flit : flitList) {

            // Flit pushing in local Inport into VC 0
            inLocal.getVclist().get(freeVC).enqueueFlit(flit);

            // set coordinates in others flit
            flit.setDxDy(dx, dy);

            // Event pushing
            if (flit.getType() == FlitType.HEAD)
                event = new Event(EventType.SEND_HEAD_FLIT, clock, this, (Direction) null, freeVC);
            else if (flit.getType() == FlitType.BODY || flit.getType() == FlitType.TAIL)
                event = new Event(EventType.SEND_BODY_TAIL_FLIT, clock, this, (Direction) null, freeVC);

            Simulator.eventList.push(event);

            clock++;
        }


        return false;
    }

    public boolean sendFlit(Flit flit, int time) {

        int dx, dy;
        dx = flit.getDx();
        dy = flit.getDy();

        // Get Routing Direction
        Direction direction = getRoutingDirection(dx, dy);

        if (direction == null) {
            System.out.println(time + " ==> " + flit + " : Destination Reached");
            return true;
        }

        boolean accepted;
        Event event;

        if (direction == Direction.WEST) {
            System.out.println(time + " ==> " + flit + " : sent in WEST");

            // getting VC Allotted from Head Flit
            Packet packet = flit.getPacket();
            int vcAllotted = packet.getHeaderFlit().getVCAllottedFromRouter(oLeft.getDest());
            if (vcAllotted < 0) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return false;
            }

            // Flit Sending
            accepted = oLeft.getDest().getInRight().accepteFlit(flit, vcAllotted);

            // Tracing
            Trace t = new Trace(flit, this, oLeft.getDest(), vcAllotted, time);
            Simulator.traceList.add(t);


        } else if (direction == Direction.EAST) {
            System.out.println(time + " ==> " + flit + " : sent in EAST");

            // getting VC Allotted from Head Flit
            Packet packet = flit.getPacket();
            int vcAllotted = packet.getHeaderFlit().getVCAllottedFromRouter(oRight.getDest());
            if (vcAllotted < 0) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return false;
            }

            // Flit Sending
            accepted = oRight.getDest().getInLeft().accepteFlit(flit, vcAllotted);

            // Tracing
            Trace t = new Trace(flit, this, oRight.getDest(), vcAllotted, time);
            Simulator.traceList.add(t);


        } else if (direction == Direction.NORTH) {
            System.out.println(time + " ==> " + flit + " : sent in NORTH");

            // getting VC Allotted from Head Flit
            Packet packet = flit.getPacket();
            int vcAllotted = packet.getHeaderFlit().getVCAllottedFromRouter(oUp.getDest());
            if (vcAllotted < 0) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return false;
            }

            // Flit Sending
            accepted = oUp.getDest().getInDown().accepteFlit(flit, vcAllotted);

            // Tracing
            Trace t = new Trace(flit, this, oUp.getDest(), vcAllotted, time);
            Simulator.traceList.add(t);


        } else if (direction == Direction.SOUTH) {
            System.out.println(time + " ==> " + flit + " : sent in SOUTH");

            // getting VC Allotted from Head Flit
            Packet packet = flit.getPacket();
            int vcAllotted = packet.getHeaderFlit().getVCAllottedFromRouter(oDown.getDest());
            if (vcAllotted < 0) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return false;
            }

            // Flit Sending
            accepted = oDown.getDest().getInUp().accepteFlit(flit, vcAllotted);

            // Tracing
            Trace t = new Trace(flit, this, oDown.getDest(), vcAllotted, time);
            Simulator.traceList.add(t);

        }
        return true;
    }

    public boolean sendHeadFlit(Flit flit, int time) {

        int dx, dy;
        dx = flit.getDx();
        dy = flit.getDy();

        // Get IRouting Direction
        Direction direction = getRoutingDirection(dx, dy);

        if (direction == null) {
            System.out.println(time + " ==> " + flit + " : Destination Reached");
            return true;
        }

        boolean accepted;
        // Free VC ID
        int freeVC = -1;

        if (direction == Direction.WEST) {
            System.out.println(time + " ==> " + flit + " : sent in WEST");

            // Getting the first free VC
            freeVC = oLeft.getDest().getInRight().getFirstFreeVC();
            if (freeVC < 0) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return false;
            }

            // Lock the VC
            oLeft.getDest().getInRight().getVclist().get(freeVC).lockAllottedVC();

            System.out.println(direction + " VC (" + freeVC + ") of " + oLeft.getDest() + " LOCKED by " + flit + " at : " + time);

            // Flit Sending
            accepted = oLeft.getDest().getInRight().accepteFlit(flit, freeVC);

            // History writing
            flit.addVCAllotted(oLeft.getDest(), freeVC);

            // Tracing
            Trace t = new Trace(flit, this, oLeft.getDest(), freeVC, time);
            Simulator.traceList.add(t);

            // Event Simulation Push
            NextEvents(time, oLeft.getDest(), Direction.EAST, freeVC);


        } else if (direction == Direction.EAST) {
            System.out.println(time + " ==> " + flit + " : sent in EAST");

            // Getting the first free VC
            freeVC = oRight.getDest().getInLeft().getFirstFreeVC();
            if (freeVC < 0) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return false;
            }

            // Lock the VC
            oRight.getDest().getInLeft().getVclist().get(freeVC).lockAllottedVC();

            System.out.println(direction + " VC (" + freeVC + ") of " + oRight.getDest() + " LOCKED by " + flit + " at : " + time);

            // Flit Sending
            accepted = oRight.getDest().getInLeft().accepteFlit(flit, freeVC);

            // History writing
            flit.addVCAllotted(oRight.getDest(), freeVC);

            // Tracing
            Trace t = new Trace(flit, this, oRight.getDest(), freeVC, time);
            Simulator.traceList.add(t);

            // Event Simulation Push
            NextEvents(time, oRight.getDest(), Direction.WEST, freeVC);

        } else if (direction == Direction.NORTH) {
            System.out.println(time + " ==> " + flit + " : sent in NORTH");

            // Getting the first free VC
            freeVC = oUp.getDest().getInDown().getFirstFreeVC();
            if (freeVC < 0) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return false;
            }

            // Lock the VC
            oUp.getDest().getInDown().getVclist().get(freeVC).lockAllottedVC();

            System.out.println(direction + " VC (" + freeVC + ") of " + oUp.getDest() + " LOCKED by " + flit + " at : " + time);

            // Flit Sending
            accepted = oUp.getDest().getInDown().accepteFlit(flit, freeVC);

            // History writing
            flit.addVCAllotted(oUp.getDest(), freeVC);

            // Tracing
            Trace t = new Trace(flit, this, oUp.getDest(), freeVC, time);
            Simulator.traceList.add(t);

            // Event Simulation Push
            NextEvents(time, oUp.getDest(), Direction.SOUTH, freeVC);


        } else if (direction == Direction.SOUTH) {
            System.out.println(time + " ==> " + flit + " : sent in SOUTH");

            // Getting the first free VC
            freeVC = oDown.getDest().getInUp().getFirstFreeVC();
            if (freeVC < 0) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return false;
            }

            // Lock the VC
            oDown.getDest().getInUp().getVclist().get(freeVC).lockAllottedVC();

            System.out.println(direction + " VC (" + freeVC + ") of " + oDown.getDest() + " LOCKED by " + flit + " at : " + time);

            // Flit Sending
            accepted = oDown.getDest().getInUp().accepteFlit(flit, freeVC);

            // History writing
            flit.addVCAllotted(oDown.getDest(), freeVC);

            // Tracing
            Trace t = new Trace(flit, this, oDown.getDest(), freeVC, time);
            Simulator.traceList.add(t);

            // Event Simulation Push
            NextEvents(time, oDown.getDest(), Direction.NORTH, freeVC);
        }

        return true;
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

    public void NextEvents(int time, Router router, Direction direction, int vcAllotted) {

        int nbflit = Simulator.PACKET_DEFAULT_SIZE / Simulator.FLIT_DEFAULT_SIZE;
        Event event = null;
        int clock = time;

        for (int i = 0; i < nbflit; i++) {
            if (i == 0)
                event = new Event(EventType.SEND_HEAD_FLIT, clock, router, direction, vcAllotted);
            else
                event = new Event(EventType.SEND_BODY_TAIL_FLIT, clock, router, direction, vcAllotted);

            Simulator.eventList.push(event);
            clock++;
        }
    }

    @Override
    public String toString() {
        return "Router (" + x + "," + y + ")";
    }
}