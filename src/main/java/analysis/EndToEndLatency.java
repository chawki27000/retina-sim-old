package analysis;

public class EndToEndLatency {

    private double pe_router_lat;
    private double packet_router_lat;


    public EndToEndLatency(double pe_router_lat, double packet_router_lat) {
        this.pe_router_lat = pe_router_lat;
        this.packet_router_lat = packet_router_lat;
    }

    public int routingDistance(int sender, int receiver, int co) {
        return (int) (Math.abs((sender % co) - (receiver % co)) +
                Math.abs(Math.floor((float) sender / co) -
                        Math.floor((float) receiver / co)) + 1);
    }

    public static double numberIteration(int nP, int aVflow) {
        return (int) Math.ceil((double) nP / aVflow);
    }

    public static double networkLatency(int nI, int oV, int nR, double latPack) {
        return ((nI * oV) + (nR - 1)) * latPack;
    }
}
