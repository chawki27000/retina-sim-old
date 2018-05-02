package architecture;


import communication.*;
import simulation_gen.NocSim;

import java.util.ArrayList;

public class Router {

    // Active Object attribute
    public int x_dest, y_dest, bits;

    int x, y;
    private InPort inLeft, inRight, inUp, inDown;
    private OutPort oLeft, oRight, oUp, oDown;

    public Router(String name, int x, int y, InPort inLeft,
                  InPort inRight, InPort inUp,
                  InPort inDown, OutPort oLeft,
                  OutPort oRight, OutPort oUp,
                  OutPort oDown) {

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

        // Get Routing Direction
        Direction direction = getDirection(dx, dy);

        if (direction == null) {
            System.out.println("Destination Reached");
            return true;
        }

        for (Flit flit : flitList) {
            sendFlit(flit, direction);
        }


        return false;
    }

    private void sendFlit(Flit flit, Direction direction) {

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

    private Direction getDirection(int dx, int dy) {
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

    private void forwardFlit(Flit f) {
        // getting the first information if it's a Header
        if (f.getType() == FlitType.HEAD) {
            x_dest = f.getDx();
            y_dest = f.getDx();
        }

        // Get Direction and Forward
        Direction direction = getDirection(x_dest, y_dest);

        if (direction == null) {
            System.out.println("Destination Reached");
        } else {
            // Sending
            sendFlit(f, direction);
        }
    }

}