package communication;

public class Flit {

    FlitType type;

    // Only Header Flit
    int dx, dy, packetID;

    public Flit(FlitType type) {
        this.type = type;
    }

    public FlitType getType() {
        return type;
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
