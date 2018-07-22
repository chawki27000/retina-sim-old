package communication;

import analysis.EndToEndLatency;
import simulation_gen.ConfigParse;
import simulation_gen.Simulator;

import java.util.ArrayList;

public class Message {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public coordinates getSrc_coor() {
		return src_coor;
	}

	public void setSrc_coor(coordinates src_coor) {
		this.src_coor = src_coor;
	}

	public coordinates getDst_coor() {
		return dst_coor;
	}

	public void setDst_coor(coordinates dst_coor) {
		this.dst_coor = dst_coor;
	}

	private int id;
	int period;
	Packet packet;
	private coordinates src_coor, dst_coor;

	public Message(int id, int period, Packet packet, coordinates src, coordinates dst) {
		this.id = id;
		this.period = period;
		this.src_coor = src;
		this.dst_coor = dst;
		this.packet = packet;
	}

	// Analysis Function

	public int getE2ELatencyByAnalysis() {
		// local variable
		int nR;
		int nI, nL;

		// Routing Distance Computing
		nR = EndToEndLatency.routingDistance(src_coor, dst_coor);

		// Iteration Number
		nI = EndToEndLatency.numberIteration(1, ConfigParse.numberOfVC);

		// Network Latency
		// nI : Number of iteration
		// oV : Total VC occupied (pessimistic)
		// nR : Number of iteration
		nL = EndToEndLatency.networkLatency(nI, 1, nR);
		return (int) ((EndToEndLatency.NETWORK_ACCESS_LAT * 2) + nL);
	}
}
