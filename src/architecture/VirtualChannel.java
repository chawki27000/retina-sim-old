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

    Boolean enqueueFlit(Flit f) {
        if (list.size() == size)
            return false;
        list.add(f);
        return true;
    }

    Boolean dequeueFlit(Flit f) {
        if (list.size() == 0)
            return false;
        list.remove(f);
        return true;
    }
}