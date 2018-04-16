package communication;

import java.util.ArrayList;

public class Packet {

    public static int FlitDefaultSize = 16;

    int id;
    ArrayList<Flit> flitList = new ArrayList<>();

    /**
     * Packet class constructor
     * It builds a packet and all inner flits
     *
     * @param id Packet index
     */
    public Packet(int id) {
        this.id = id;

        // Packet Building
        int numberOfFlit = Message.packetDefaultSize / Packet.FlitDefaultSize;

        for (int i = 0; i < numberOfFlit; i++) {
            if (i == 0) // Head Flit
                flitList.add(new Flit(FlitType.HEAD));
            else if (i == numberOfFlit - 1) // Tail  Flit
                flitList.add(new Flit(FlitType.TAIL));
            else // Data Flit
                flitList.add(new Flit(FlitType.DATA));
        }
    }

    public int getId() {
        return id;
    }

    public Flit getHeaderFlit() {
        return flitList.get(0);
    }

    public ArrayList<Flit> getFlitList() {
        return flitList;
    }

    public Flit getFlit(int index) {
        return flitList.get(index);
    }

    public void setDestinationInfo(int[] dest) {
        // Get Header Flit
        Flit flit = flitList.get(0);
        flit.setDestinationInfo(dest[0], dest[1], id);
    }

    public void addFlit(Flit flit) {
        flitList.add(flit);
    }
}
