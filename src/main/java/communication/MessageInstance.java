package communication;

public class MessageInstance extends Message {

	public MessageInstance(Message m, int inst, int arr) {
		super(m.getId(), m.getPeriod(), m.getPacket(), m.getSrc_coor(), m.getDst_coor());
		this.arrivalTime = arr;
		this.instNumber = inst;
	}

	int arrivalTime;
	int instNumber;

	private double e2eLatency;

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getInstNumber() {
		return instNumber;
	}

	public void setInstNumber(int instNumber) {
		this.instNumber = instNumber;
	}

	public int latencyAnalysis() {
		return packet.getLastFlit().getTimeEnd() - arrivalTime;
	}

}
