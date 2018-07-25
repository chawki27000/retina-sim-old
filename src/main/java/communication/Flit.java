package communication;

import architecture.Router;
import architecture.VirtualChannel;

import java.util.HashMap;

public class Flit {

    FlitType type;
    private int id;
    private int timeBegin, timeEnd;
    private VirtualChannel actualVC;
    // Only Header Flit
    private coordinates dst;
    private Packet packet;

    public HashMap<Router, VirtualChannel> vcAllottedMap = new HashMap<Router, VirtualChannel>();

    public Flit(int id, FlitType type, Packet packet, int timeBegin) {
        this.type = type;
        this.timeBegin = timeBegin;
        this.packet = packet;
        this.id = id;
    }

    public FlitType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setTimeEnd(int timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getTimeEnd() {
        return timeEnd;
    }

    public int getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(int timeBegin) {
        this.timeBegin = timeBegin;
    }

    public VirtualChannel getActualVC() {
        return actualVC;
    }

    public void setActualVC(VirtualChannel actualVC) {
        this.actualVC = actualVC;
    }

    /*
        Only Header Flit
        */
    public void setDestinationInfo(coordinates dst) {
        if (type == FlitType.HEAD) {
            this.dst = dst;
        }
    }

    /*
    Only Header Flit
    */
    public void addVCAllotted(Router router, VirtualChannel VC) {
        vcAllottedMap.putIfAbsent(router, VC);
    }

    /*
    Only Header Flit
    */
    public VirtualChannel getVCAllottedFromRouter(Router router) {
        return vcAllottedMap.getOrDefault(router, null);
    }

    public String afficherHashMap() {
        return vcAllottedMap.toString();
    }


    public Packet getPacket() {
        return packet;
    }


    /*
        Only Body and Tail Flit
         */
    public void setDst(coordinates dst) {
        this.dst = dst;
    }

    public coordinates getDst() {
        return this.dst;
    }

    @Override
    public String toString() {
        return type + " Flit [M:" + packet.getMessage().getId() +
                " P:" + packet.getId() +
                " F:" + id + "]";
    }
}
