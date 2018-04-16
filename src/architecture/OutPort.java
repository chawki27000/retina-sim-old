package architecture;

public class OutPort {

    private int idx;
    private Router dest;

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
