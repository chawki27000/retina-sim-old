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

    @Override
    public String toString() {
        return "Sent "+flit.getType()+" Flit from Router (" + src.getX() + "," + src.getY() + ") to (" +
                dest.getX() + "," + dest.getY() + ") on VC (" + vcAllotted + ") at : " + time;
    }
}
