package simulation_gen;

import architecture.Router;

public class Trace {

    private Router src;
    private Router dest;
    private int vcAllotted;
    private int time;

    public Trace(Router src, Router dest, int vcAllotted, int time) {
        this.src = src;
        this.dest = dest;
        this.time = time;
        this.vcAllotted = vcAllotted;
    }

    @Override
    public String toString() {
        return "Sent Flit from Router (" + src.getX() + "," + src.getY() + ") to (" +
                dest.getX() + "," + dest.getY() + ") on VC (" + vcAllotted + ") at : " + time;
    }
}
