package communication;

import architecture.Router;

import java.util.HashMap;

public class Flit {

    FlitType type;

    private int timeBegin, timeEnd;
    // Only Header Flit
    private int dx, dy, packetID;
    private Packet packet;

    public HashMap<Router, Integer> vcAllottedMap = new HashMap<Router, Integer>();

    public Flit(FlitType type, Packet packet, int timeBegin) {
        this.type = type;
        this.timeBegin = timeBegin;
        this.packet = packet;
    }

    public FlitType getType() {
        return type;
    }

    public void setTimeEnd(int timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getTimeEnd() {
        return timeEnd;
    }

    public int getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(int timeBegin) {
        this.timeBegin = timeBegin;
    }


    /*
    Only Header Flit
    */
    public void setDestinationInfo(int dx, int dy, int packetID) {
        if (type == FlitType.HEAD) {
            this.dx = dx;
            this.dy = dy;
            this.packetID = packetID;
        }
    }

    /*
    Only Header Flit
    */
    public void addVCAllotted(Router router, int vcAllotted) {
        vcAllottedMap.putIfAbsent(router, vcAllotted);
    }

    /*
    Only Header Flit
    */
    public int getVCAllottedFromRouter(Router router) {
        return vcAllottedMap.get(router);
    }

    public String afficherHashMap() {
        return vcAllottedMap.toString();
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getPacketID() {
        return packetID;
    }

    public Packet getPacket() {
        return packet;
    }

    /*
        Only Body and Tail Flit
         */
    public void setDxDy(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }


    @Override
    public String toString() {
        return type + " Flit (Packet " + packetID + ")";
    }
}
