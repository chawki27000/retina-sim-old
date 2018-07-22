package analysis;

import communication.coordinates;

public class EndToEndLatency {

    public static double PACKET_ROUTER_LAT = 0.2635;
    public static double NETWORK_ACCESS_LAT = 0.001;

    public static int routingDistance(coordinates src, coordinates dst) {
        return Math.abs(src.getX() - dst.getX()) + Math.abs(src.getY()- dst.getY());
    }

    public static int numberIteration(int nP, int aVflow) {
        return (int) Math.ceil((double) nP / aVflow);
    }

    public static int networkLatency(double nI, int oV, int nR) {
        return (int) ((nI * oV) + (nR - 1));
    }
}
