package architecture;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import communication.Flit;
import communication.Message;
import communication.Packet;

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
     *
     * @param id       input port ID
     * @param NBVCLIST Number of VC per port
     * @param NSlots   size of VC buffer
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

    public int getFirstFreeVC() {
        for (VirtualChannel channel : vclist) {
            if (channel.isFree())
                return channel.getId();
        }

        return -1;
    }

    public VirtualChannel getFirstFullVC() {
        for (VirtualChannel channel: vclist) {
            if(channel.getSize() ==
                    Message.packetDefaultSize / Packet.FlitDefaultSize)
                return channel;
        }
        return null;
    }

    public void accepteFlit(Flit flit, int freeVC) {

        vclist.get(freeVC).enqueueFlit(flit);

    }

}
