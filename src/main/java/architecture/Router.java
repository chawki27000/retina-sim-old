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
     */
    public void sendMessage(MessageInstance minst, int time) {
        sendPacket(minst.getPacket(), time);
    }

    /**
     * prepare a packet to its next router
     *
     * @param packet
     * @return
     */
    private boolean sendPacket(Packet packet, int time) {


        Event event = null;
        int clock = time;

        // Get header flit to extract Dest router
        Flit headerFlit = packet.getHeaderFlit();

        // Extract header flit information
        coordinates dst_ = headerFlit.getDst();

        // Slicing each packet in several flits
        ArrayList<Flit> flitList = packet.getFlitList();

        // get the first free VC in InLocal and hold it
        VirtualChannel freeVC = inLocal.getFirstFreeVC();
        freeVC.lockAllottedVC();

        // Flit preparation
        for (Flit flit : flitList) {

            // Flit pushing in local Inport into VC 0
            freeVC.enqueueFlit(flit);

            // set coordinates in others flit
            flit.setDst(dst_);

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

    /**
     * 0 : true
     * 1 : VC not allotted
     * 2 : Full Buffer
     *
     * @param flit
     * @param time
     * @return
     */
    public int sendFlit(Flit flit, int time) {

        // Get Routing Direction
        Direction direction = getRoutingDirection(flit.getDst());

        if (direction == null) {
            System.out.println(flit + " : Destination Reached");
            flit.setTimeEnd(time);
            return 0;
        }

        boolean accepted;
        Event event;

        if (direction == Direction.WEST) {
            System.out.println(time + " ==> " + flit + " : sent in WEST");

            // getting VC Allotted from Head Flit
            Packet packet = flit.getPacket();
            VirtualChannel vcAllotted = packet.getHeaderFlit().getVCAllottedFromRouter(oLeft.getDest());
            if (vcAllotted == null) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return 1;
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
            VirtualChannel vcAllotted = packet.getHeaderFlit().getVCAllottedFromRouter(oRight.getDest());
            if (vcAllotted == null) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return 1;
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
            VirtualChannel vcAllotted = packet.getHeaderFlit().getVCAllottedFromRouter(oUp.getDest());
            if (vcAllotted == null) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return 1;
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
            VirtualChannel vcAllotted = packet.getHeaderFlit().getVCAllottedFromRouter(oDown.getDest());
            if (vcAllotted == null) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return 1;
            }

            // Flit Sending
            accepted = oDown.getDest().getInUp().accepteFlit(flit, vcAllotted);

            // Tracing
            Trace t = new Trace(flit, this, oDown.getDest(), vcAllotted, time);
            Simulator.traceList.add(t);

        }
        return 0;
    }

    /**
     * 0 : true
     * 1 : VC not allotted
     * 2 : Full Buffer
     *
     * @param flit
     * @param time
     * @return
     */
    public int sendHeadFlit(Flit flit, int time) {


        // Get IRouting Direction
        Direction direction = getRoutingDirection(flit.getDst());

        if (direction == null) {
            System.out.println(flit + " : Destination Reached");
            flit.setTimeEnd(time);
            return 0;
        }

        boolean accepted;
        // Free VC ID
        VirtualChannel freeVC = null;

        if (direction == Direction.WEST) {
            System.out.println(time + " ==> " + flit + " : sent in WEST");

            // Getting the first free VC
            freeVC = oLeft.getDest().getInRight().getFirstFreeVC();
            if (freeVC == null) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return 1;
            }

            // Lock the VC
            freeVC.lockAllottedVC();

            System.out.println(direction + " VC (" + freeVC + ") of " + oLeft.getDest() + " LOCKED by " + flit + " at : " + time);

            // Flit Sending
            accepted = oLeft.getDest().getInRight().accepteFlit(flit, freeVC);

            // History writing
            flit.addVCAllotted(oLeft.getDest(), freeVC);

            // Tracing
            Trace t = new Trace(flit, this, oLeft.getDest(), freeVC, time);
            Simulator.traceList.add(t);

            // Event Simulation Push
            NextEvents(time, oLeft.getDest(), Direction.EAST, freeVC, flit);


        } else if (direction == Direction.EAST) {
            System.out.println(time + " ==> " + flit + " : sent in EAST");

            // Getting the first free VC
            freeVC = oRight.getDest().getInLeft().getFirstFreeVC();
            if (freeVC == null) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return 1;
            }

            // Lock the VC
            freeVC.lockAllottedVC();

            System.out.println(direction + " VC (" + freeVC + ") of " + oRight.getDest() + " LOCKED by " + flit + " at : " + time);

            // Flit Sending
            accepted = oRight.getDest().getInLeft().accepteFlit(flit, freeVC);

            // History writing
            flit.addVCAllotted(oRight.getDest(), freeVC);

            // Tracing
            Trace t = new Trace(flit, this, oRight.getDest(), freeVC, time);
            Simulator.traceList.add(t);

            // Event Simulation Push
            NextEvents(time, oRight.getDest(), Direction.WEST, freeVC, flit);

        } else if (direction == Direction.NORTH) {
            System.out.println(time + " ==> " + flit + " : sent in NORTH");

            // Getting the first free VC
            freeVC = oUp.getDest().getInDown().getFirstFreeVC();
            if (freeVC == null) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return 1;
            }

            // Lock the VC
            freeVC.lockAllottedVC();

            System.out.println(direction + " VC (" + freeVC + ") of " + oUp.getDest() + " LOCKED by " + flit + " at : " + time);

            // Flit Sending
            accepted = oUp.getDest().getInDown().accepteFlit(flit, freeVC);

            // History writing
            flit.addVCAllotted(oUp.getDest(), freeVC);

            // Tracing
            Trace t = new Trace(flit, this, oUp.getDest(), freeVC, time);
            Simulator.traceList.add(t);

            // Event Simulation Push
            NextEvents(time, oUp.getDest(), Direction.SOUTH, freeVC, flit);


        } else if (direction == Direction.SOUTH) {
            System.out.println(time + " ==> " + flit + " : sent in SOUTH");

            // Getting the first free VC
            freeVC = oDown.getDest().getInUp().getFirstFreeVC();
            if (freeVC == null) {
                System.out.println(time + " ==> " + flit + " : Blocking");
                return 1;
            }

            // Lock the VC
            freeVC.lockAllottedVC();

            System.out.println(direction + " VC (" + freeVC + ") of " + oDown.getDest() + " LOCKED by " + flit + " at : " + time);

            // Flit Sending
            accepted = oDown.getDest().getInUp().accepteFlit(flit, freeVC);

            // History writing
            flit.addVCAllotted(oDown.getDest(), freeVC);

            // Tracing
            Trace t = new Trace(flit, this, oDown.getDest(), freeVC, time);
            Simulator.traceList.add(t);

            // Event Simulation Push
            NextEvents(time, oDown.getDest(), Direction.NORTH, freeVC, flit);
        }

        return 0;
    }

    public Direction getRoutingDirection(coordinates crd) {
        // On X axe
        // By the West
        if (y > crd.getY()) {
            return Direction.WEST;
        }
        // By the East
        else if (y < crd.getY()) {
            return Direction.EAST;
        }
        // On Y axe
        else {
            // By the North
            if (x > crd.getX()) {
                return Direction.NORTH;
            }
            // By the South
            else if (x < crd.getX()) {
                return Direction.SOUTH;
            } else {
                // Destination Reached
                return null;
            }
        }
    }

    public void NextEvents(int time, Router router, Direction direction, VirtualChannel vcAllotted, Flit flit) {

        int nbflit = (int) Math.ceil(flit.getPacket().getSize() / Simulator.FLIT_DEFAULT_SIZE);
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