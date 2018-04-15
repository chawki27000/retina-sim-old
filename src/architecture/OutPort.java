package architecture;

import psimjava.Process;

public class OutPort extends Process {

    int idx;
    Router dest;

    public OutPort(int idx) {
        super(String.valueOf(idx));
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

    @Override
    protected void Main_body() {

    }
}
