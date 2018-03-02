package architecture;

import communication.Flit;

import java.util.ArrayList;

/**
 * This class aims to define an Input Port which is located
 * in one of fourth router edges
 */
public class InPort {

    ArrayList<VirtualChannel> vclist;
    Router src;
    int id;
    int NumberofVCList;

    /**
     * InPort constructor class
     * @param id input port ID
     * @param NBVCLIST Number of VC per port
     * @param NSlots size of VC buffer
     */
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
