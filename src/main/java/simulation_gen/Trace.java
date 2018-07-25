package simulation_gen;

import architecture.Router;
import architecture.VirtualChannel;
import communication.Flit;

public class Trace {

    private Flit flit;
    private Router src;
    private Router dest;
    private VirtualChannel vcAllotted;
    private int time;

    public Trace(Flit flit, Router src, Router dest, VirtualChannel vcAllotted, int time) {
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

    public VirtualChannel getVcAllotted() {
        return vcAllotted;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Sent " + flit + " from " + src + " to " +
                dest + " on " + vcAllotted + " at : " + time;
    }
}
