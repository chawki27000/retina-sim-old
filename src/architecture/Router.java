package architecture;


import communication.Flit;
import communication.Message;
import communication.Packet;

import java.util.ArrayList;

public class Router {

    int x, y;
    InPort inLeft, inRight, inUp, inDown;
    OutPort oLeft, oRight, oUp, oDown;

    public Router(int x, int y, InPort inLeft, InPort inRight, InPort inUp,
                  InPort inDown, OutPort oLeft, OutPort oRight, OutPort oUp,
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

    public void accepteFlit(Flit flit) {

    }

    // TODO : send to next router
    public void forwardMessage(Message message) {

        int dx, dy;

        // Slicing message in several packets
        ArrayList<Packet> packetList = message.getPacketList();


        for (Packet packet : packetList) {
            // Slicing each packet in several flits
            ArrayList<Flit> flitList = packet.getFlitList();

            // Extract header flit information
            dx = flitList.get(0).getDx();
            dy = flitList.get(0).getDy();

            for (Flit flit : flitList) {
                // To Output port
                nextDestination(flit, new int[]{dx, dy});
            }
        }


    }

    // TODO : compute next desination
    private boolean nextDestination(Flit flit, int[] dest) {

        // dest router coordinate
        int dx = dest[0], dy = dest[1];

        // On X axe
        // By the West
        if (x > dx) {
            oLeft.getDest().accepteFlit(flit);
        }

        // By the East
        else if (x < dx) {
            oRight.getDest().accepteFlit(flit);
        }
        // On Y axe
        else {
            // By the North
            if (y > dy) {
                oUp.getDest().accepteFlit(flit);
            }
            // By the South
            else if (y < dy) {
                oDown.getDest().accepteFlit(flit);
            }
            // Destination Reached
            else {
                return true;
            }
        }

        return false;
    }

}
