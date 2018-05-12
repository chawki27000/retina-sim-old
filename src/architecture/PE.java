package architecture;

import communication.Flit;

import java.util.ArrayList;

public class PE {

    private ArrayList<Flit> flitBuffer;

    public PE() {
        this.flitBuffer = new ArrayList<>();
    }

    public void pushFlit(Flit flit) {
        flitBuffer.add(flit);
    }

    public Flit pullFlit() {
        if (flitBuffer.isEmpty())
            return null;

        Flit flit = flitBuffer.get(0);
        flitBuffer.remove(flit);
        return flit;
    }

    public boolean isEmpty() {
        return flitBuffer.isEmpty();
    }
}
