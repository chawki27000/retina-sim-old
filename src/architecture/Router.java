package architecture;


import communication.Direction;
import communication.Flit;
import communication.Message;
import communication.Packet;
import psimjava.Process;
import simulation_gen.NocSim;

import java.util.ArrayList;

public class Router extends Process {

    int x, y;
    private InPort inLeft, inRight, inUp, inDown;
    private OutPort oLeft, oRight, oUp, oDown;

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
            sendPacket(packet);
        }

    }

    /**
     * Send a packet to its next router
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

        // On X axe
        // By the West
        if (y > dy) {
            System.out.println("By West");

            for (Flit flit : flitList) {
                sendFlit(flit, Direction.WEST);
            }

        }

        // By the East
        else if (y < dy) {
            System.out.println("By East");

            for (Flit flit : flitList) {
                sendFlit(flit, Direction.EAST);
            }

        }
        // On Y axe
        else {
            // By the North
            if (x > dx) {
                System.out.println("By North");

                for (Flit flit : flitList) {
                    sendFlit(flit, Direction.NORTH);
                }

            }
            // By the South
            else if (x < dx) {
                System.out.println("By South");

                for (Flit flit : flitList) {
                    sendFlit(flit, Direction.SOUTH);
                }

            } else {
                // Destination Reached
                return true;
            }
        }

        return false;
    }

    public void sendFlit(Flit flit, Direction direction) {

        // Free VC ID
        int freeVC = 0;

        if (direction == Direction.WEST) {
            System.out.println("Flit " + flit.getType() + " sended in WEST");
            freeVC = oLeft.getDest().getInRight().getFirstFreeVC();

            oLeft.getDest().getInRight().accepteFlit(flit, freeVC);

        } else if (direction == Direction.EAST) {
            System.out.println("Flit " + flit.getType() + " sended in EAST");
            freeVC = oRight.getDest().getInLeft().getFirstFreeVC();

            oRight.getDest().getInLeft().accepteFlit(flit, freeVC);


        } else if (direction == Direction.NORTH) {
            System.out.println("Flit " + flit.getType() + " sended in NORTH");
            freeVC = oUp.getDest().getInRight().getFirstFreeVC();


            oUp.getDest().getInDown().accepteFlit(flit, freeVC);


        } else if (direction == Direction.SOUTH) {
            System.out.println("Flit " + flit.getType() + " sended in SOUTH");
            freeVC = oDown.getDest().getInRight().getFirstFreeVC();

            oDown.getDest().getInUp().accepteFlit(flit, freeVC);
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void Main_body() {

        System.out.println("Router " + get_name() + " activated at: " + get_clock());

        while (get_clock() < NocSim.simPeriod) {

            // Non empty buffer testing (for flit forwarding)
            if (inLeft.getFirstNonEmptyVC() != null) {
                System.out.println("Router " + get_name() + " inLeft Non Empty");

            } else if (inUp.getFirstNonEmptyVC() != null) {
                System.out.println("Router " + get_name() + " inUp Non Empty");
            } else if (inRight.getFirstNonEmptyVC() != null) {
                System.out.println("Router " + get_name() + " inRight Non Empty");
            } else if (inDown.getFirstNonEmptyVC() != null) {
                System.out.println("Router " + get_name() + " inDown Non Empty");
            } else {
                System.out.println("Router " + get_name() + " deactivate at: " + get_clock());
                deactivate(this);
            }
            delay(125d);
        }
    }
}
