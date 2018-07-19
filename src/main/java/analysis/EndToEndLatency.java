package analysis;

public class EndToEndLatency {

    public static double PACKET_ROUTER_LAT = 0.2635;
    public static double NETWORK_ACCESS_LAT = 0.001;

    public static int routingDistance(int src_x, int src_y, int dest_x, int dest_y) {
        return Math.abs(dest_x - src_x) + Math.abs(dest_y - src_y);
    }

    public static double numberIteration(int nP, int aVflow) {
        return (int) Math.ceil((double) nP / aVflow);
    }

    public static double networkLatency(double nI, int oV, int nR) {
        return ((nI * oV) + (nR - 1));
    }
}
