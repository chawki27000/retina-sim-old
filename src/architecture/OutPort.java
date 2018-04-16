package architecture;

public class OutPort {

    int idx;
    Router dest;

    public OutPort(int idx) {
        this.idx = idx;
    }

    public Router getDest() {
        return dest;
    }

    public void setDest(Router dest) {
        this.dest = dest;
    }

    public int getIdx() {
        return idx;
    }

}
