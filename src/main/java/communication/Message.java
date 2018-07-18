package communication;

import analysis.EndToEndLatency;
import simulation_gen.ConfigParse;

import java.util.ArrayList;

public class Message {


    public static int packetDefaultSize = 64;
    public static int packetNum = 0;
    public static int messageNum = 0;

    private int id, instance;
    ArrayList<Packet> packetList = new ArrayList<>();
    int size, numberOfPacket;


    // Analysis Attribute
    private double e2eLatency;
    private int[] src_coor, dest_coor;

    /**
     * Message class constructor
     * It build a message and all inner packets
     *
     * @param size Message size
     */
    public Message(int id, int instance, int size, int[] src_coor, int[] dest_coor) {
        this.id = id;
        this.instance = instance;
        this.size = size;
        this.src_coor = src_coor;
        this.dest_coor = dest_coor;

        // Message Building
        numberOfPacket = size / Message.packetDefaultSize;

        for (int i = 0; i < numberOfPacket; i++) {
            packetList.add(new Packet(packetNum, this));
            packetNum++;
        }

    }

    public void setDestinationInfo(int[] dest) {
        for (Packet packet : packetList) {
            packet.setDestinationInfo(dest);
        }
    }

    public ArrayList<Packet> getPacketList() {
        return packetList;
    }

    public int getSize() {
        return size;
    }

    public int getId() {
        return id;
    }

    public int getInstance() {
        return instance;
    }

    /**
     * Analysis Function
     */
    public double getE2ELatency() {
        // local variable
        int nR;
        double nI, nL;

        // Routing Distance Computing
        nR = EndToEndLatency.routingDistance(src_coor[0], src_coor[1],
                dest_coor[0], dest_coor[1]);

        // Iteration Number
        nI = EndToEndLatency.numberIteration(numberOfPacket, ConfigParse.numberOfVC);

        // Network Latency
        // nI : Number of iteration
        // oV : Total VC occupied (pessimistic)
        // nR : Number of iteration
        nL = EndToEndLatency.networkLatency(nI, ConfigParse.numberOfVC, nR);

        return (EndToEndLatency.NETWORK_ACCESS_LAT * 2) + nL;
    }
}
