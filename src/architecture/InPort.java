package architecture;

import communication.Flit;
import communication.Message;
import communication.Packet;

import java.util.ArrayList;

/**
 * This class aims to define an Input Port which is located
 * in one of fourth router edges
 */
public class InPort {

    private ArrayList<VirtualChannel> vclist;
    private Router src;
    private int idx;
    private int NumberofVCList;

    /**
     * InPort constructor class
     *
     * @param idx      input port ID
     * @param NBVCLIST Number of VC per port
     * @param NSlots   size of VC buffer
     */
    public InPort(int idx, int NBVCLIST, int NSlots) {

        this.idx = idx;
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

    public int getIdx() {
        return idx;
    }

    public void setIdx(int id) {
        this.idx = id;
    }

    public int getNumberofVCList() {
        return NumberofVCList;
    }

    public void setNumberofVCList(int numberofVCList) {
        NumberofVCList = numberofVCList;
    }

    public int getFirstFreeVC() {
        for (VirtualChannel channel : vclist) {
            if (channel.isFree())
                return channel.getIdx();
        }

        return -1;
    }

    public VirtualChannel getFirstNonEmptyVC() {
        for (VirtualChannel vc : vclist) {
            if (!vc.isFree())
                return vc;
        }
        return null;
    }

    public void accepteFlit(Flit flit, int freeVC) {

        vclist.get(0).enqueueFlit(flit);

    }

}
