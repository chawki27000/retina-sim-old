package communication;

public class Flit {

    FlitType type;

    private int timeBegin, timeEnd;
    // Only Header Flit
    int dx, dy, packetID;

    public Flit(FlitType type, int timeBegin) {
        this.type = type;
        this.timeBegin = timeBegin;
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

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getPacketID() {
        return packetID;
    }
}
