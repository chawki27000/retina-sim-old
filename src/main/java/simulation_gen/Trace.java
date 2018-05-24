package simulation_gen;

import architecture.Router;

public class Trace {

    private Router src;
    private Router dest;
    private int time;

    public Trace(Router src, Router dest, int time) {
        this.src = src;
        this.dest = dest;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Sent Flit from Router (" + src.getX() + "," + src.getY() + ") to (" +
                dest.getX() + "," + dest.getY() + ") at : " + time;
    }
}
