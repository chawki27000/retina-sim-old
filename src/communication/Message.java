package communication;

import java.util.ArrayList;

public class Message {

    static int packetDefaultSize = 64;

    ArrayList<Packet> packetList = new ArrayList<>();
    int size;

    /**
     * Message class contructor
     * It build a message and all inner packets
     *
     * @param size Message size
     */
    public Message(int size) {
        this.size = size;

        // Message Building
        int numberOfPacket = size / Message.packetDefaultSize;

        for (int i = 0; i < numberOfPacket; i++) {
            packetList.add(new Packet(i));
        }
    }

    public ArrayList<Packet> getPacketList() {
        return packetList;
    }

    public int getSize() {
        return size;
    }
}
