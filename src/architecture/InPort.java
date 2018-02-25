package architecture;

import java.util.ArrayList;

public class InPort {

    ArrayList<VirtualChannel> vclist;
    Router src;
    int id;
    int NumberofVCList;

    public InPort(int id, int NBVCLIST, int NSlots) {
        this.id = id;
        this.NumberofVCList = NBVCLIST;
        vclist = new ArrayList<VirtualChannel>();
        for (int i = 0; i < NumberofVCList; i++) {
            vclist.add(new VirtualChannel(i, NSlots));
        }
    }

    void setNeighbor(Router s) {
        src = s;
    }

    public ArrayList<VirtualChannel> getVclist() {
        return vclist;
    }

    public void setVclist(ArrayList<VirtualChannel> vclist) {
        this.vclist = vclist;
    }

    public Router getSrc() {
        return src;
    }

    public void setSrc(Router src) {
        this.src = src;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberofVCList() {
        return NumberofVCList;
    }

    public void setNumberofVCList(int numberofVCList) {
        NumberofVCList = numberofVCList;
    }

}
