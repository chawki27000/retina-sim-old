package architecture;

import java.util.HashMap;

public class OutPort {

    private int idx;
    private Router dest;

    // Arbitration table
    HashMap<Integer, Integer> arbitrationMap = new HashMap<>();

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

    // Arbitration functions
    public void putArbitrationMap(int VC, int time) {
        arbitrationMap.put(VC, time);
    }

    public boolean checkArbitrationMap(int VC, int time) {
        int value;
        if (arbitrationMap.containsKey(VC)) {
            value = arbitrationMap.get(VC);

            if (value == time)
                return true;
        }
        return false;
    }

}
