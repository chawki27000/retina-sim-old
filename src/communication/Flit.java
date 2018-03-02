package communication;

public class Flit {

    FlitType type;

    // Only Header Flit
    int dx, dy;

    public Flit(FlitType type) {
        this.type = type;
    }

    public FlitType getType() {
        return type;
    }

    /*
    Only Header Flit
     */
    public void setDestinationInfo(int dx, int dy) {
        if (type == FlitType.HEAD) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
