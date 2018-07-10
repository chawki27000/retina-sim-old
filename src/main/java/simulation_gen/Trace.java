package simulation_gen;

import architecture.Router;
import communication.Flit;

public class Trace {

    private Flit flit;
    private Router src;
    private Router dest;
    private int vcAllotted;
    private int time;

    public Trace(Flit flit, Router src, Router dest, int vcAllotted, int time) {
        this.flit = flit;
        this.src = src;
        this.dest = dest;
        this.time = time;
        this.vcAllotted = vcAllotted;
    }

    public Flit getFlit() {
        return flit;
    }

    public Router getSrc() {
        return src;
    }

    public Router getDest() {
        return dest;
    }

    public int getVcAllotted() {
        return vcAllotted;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Sent " + flit + " from " + src + " to " +
                dest + " on VC (" + vcAllotted + ") at : " + time;
    }
}
