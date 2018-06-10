package communication;

import architecture.Router;

import java.util.HashMap;

public class Flit {

    FlitType type;

    private int timeBegin, timeEnd;
    // Only Header Flit
    private int dx, dy, packetID;
    private HashMap<Router, Integer> vcAllotedMap = new HashMap<Router, Integer>();

    public Flit(FlitType type, int packetID, int timeBegin) {
        this.type = type;
        this.timeBegin = timeBegin;
        this.packetID = packetID;
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
        vcAllotedMap.putIfAbsent(router, vcAllotted);
    }

    /*
    Only Header Flit
    */
    public int getVCAllottedFromRouter(Router router) {
        return vcAllotedMap.get(router);
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
