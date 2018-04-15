package architecture;


import communication.Flit;
import communication.Message;
import communication.Packet;
import psimjava.Process;
import simulation_gen.NocSim;

import java.util.ArrayList;

public class Router extends Process {

    // Process attribute
    private int x_dest = -1;
    private int y_dest = -1;
    private int bits_send = 0;

    int x, y;
    public static InPort inLeft, inRight, inUp, inDown;
    public OutPort oLeft, oRight, oUp, oDown;

    public Router(String name, int x, int y, InPort inLeft,
                  InPort inRight, InPort inUp,
                  InPort inDown, OutPort oLeft,
                  OutPort oRight, OutPort oUp,
                  OutPort oDown) {
        super(name);
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
            nextDestination(packet);
        }

    }

    /**
     * Send a packet to its next router
     *
     * @param packet
     * @return
     */
    private boolean nextDestination(Packet packet) {

        int dx, dy;

        // Get header flit to extract Dest router
        Flit headerFlit = packet.getHeaderFlit();

        // Extract header flit information
        dx = headerFlit.getDx();
        dy = headerFlit.getDy();

        System.out.println("Sending packet : " + packet.getId() + " From : (" +
                x + "," + y + ")" + " TO : (" + dx + "," + dy + ")");

        // Free VC ID
        int freeVC = 0;

        // Slicing each packet in several flits
        ArrayList<Flit> flitList = packet.getFlitList();

        // On X axe
        // By the West
        if (x < dx) {
            System.out.println("By West");
            // Get the free VC among VCs Input Port
            System.out.println("next router : " + oRight.getDest().getX() + " " + oRight.getDest().getY());
            freeVC = oRight.getDest().getInRight().getFirstFreeVC();

            // SENDING
            for (Flit flit : flitList) {

                oRight.getDest().getInRight().accepteFlit(flit, freeVC);
            }
        }

        // By the East
        else if (x > dx) {
            System.out.println("By East");
            // Get the free VC among VCs Input Port
            freeVC = oLeft.getDest().getInRight().getFirstFreeVC();

            // SENDING
            for (Flit flit : flitList) {

                oLeft.getDest().getInLeft().accepteFlit(flit, freeVC);
            }
        }
        // On Y axe
        else {
            // By the North
            if (y < dy) {
                System.out.println("By North");
                // Get the free VC among VCs Input Port
                freeVC = oUp.getDest().getInRight().getFirstFreeVC();

                // SENDING
                for (Flit flit : flitList) {

                    oUp.getDest().getInDown().accepteFlit(flit, freeVC);
                }
            }
            // By the South
            else if (y > dy) {
                System.out.println("By South");
                // Get the free VC among VCs Input Port
                freeVC = oDown.getDest().getInRight().getFirstFreeVC();

                // SENDING
                for (Flit flit : flitList) {

                    oDown.getDest().getInUp().accepteFlit(flit, freeVC);
                }
            } else {
                // Destination Reached
                return true;
            }
        }

        return false;
    }

    /**
     * Forward a packet when it's will arrive in a router
     */
//    public void packetForward() {
//
//        Packet arrived = null;
//
//        // check if a VC buffer is full
//        if (inLeft.getFirstFullVC() != null) {
//            arrived = packetBuilding(inLeft.getFirstFullVC());
//        }
//
//        if (inRight.getFirstFullVC() != null) {
//            arrived = packetBuilding(inRight.getFirstFullVC());
//        }
//
//        if (inUp.getFirstFullVC() != null) {
//            arrived = packetBuilding(inUp.getFirstFullVC());
//        }
//
//        if (inDown.getFirstFullVC() != null) {
//            arrived = packetBuilding(inDown.getFirstFullVC());
//        }
//
//        nextDestination(arrived);
//
//    }

    /**
     * Rebuild a packet when the virtual channel that's contain him
     * will be full
     *
     * @param channel
     * @return
     */
    private Packet packetBuilding(VirtualChannel channel) {
        // Get packet idx
        int id = channel.getList().get(0).getPacketID();
        Packet packet = new Packet(id);
        Flit tmp;

        tmp = channel.getList().get(0);
        packet.addFlit(tmp);
        channel.dequeueFlit(tmp);

        return packet;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setSendingInfo(int x, int y, int bits) {
        x_dest = x;
        y_dest = y;
        bits_send = bits;
    }


    @Override
    protected void Main_body() {
        System.out.println("Router " + get_name() + " created at: " + get_clock());

        while (get_clock() < NocSim.simPeriod) {

            if (x_dest > -1 && y_dest > -1) {
                reactivate(this);

                System.out.println("Router " + get_name() + " send at: " + get_clock());
                sendMessage(bits_send, new int[]{x_dest, y_dest});


            }
            else {
                deactivate(this);
            }

        }
        terminate();

    }
}
