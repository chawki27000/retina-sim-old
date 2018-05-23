package architecture;

import communication.Flit;

import java.util.ArrayList;

public class VirtualChannel {

    int idx;
    int size;
    ArrayList<Flit> list;

    public VirtualChannel(int idx, int size) {
        this.idx = idx;
        this.size = size;
        list = new ArrayList<Flit>();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<Flit> getList() {
        return list;
    }

    public void setList(ArrayList<Flit> list) {
        this.list = list;
    }

    public int getIdx() {
        return idx;
    }

    public boolean isFree() {
        return list.size() == 0;
    }

    public Boolean enqueueFlit(Flit f) {
        if (list.size() == size)
            return false;
        list.add(f);
        return true;
    }

    public Flit dequeueFlit() {
        Flit f;
        if (list.size() == 0)
            return null;

        f = list.get(0);
        list.remove(f);
        return f;
    }
}