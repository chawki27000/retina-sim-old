package communication;

import simulation_gen.Simulator;

import java.util.ArrayList;

public class Packet {

	int id;
	int size;
	Message message;
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	ArrayList<Flit> flitList = new ArrayList<>();

	/**
	 * Packet class constructor It builds a packet and all inner flits
	 *
	 * @param id
	 *            Packet index
	 */
	
	public Packet(int id, int size) {
		this.id = id;
		this.size = size;
		// Packet Building
		int numberOfFlit = (int) (Math.ceil(this.size / Simulator.FLIT_DEFAULT_SIZE));

		for (int i = 0; i < numberOfFlit; i++) {
			if (i == 0) // Head Flit
				flitList.add(new Flit(i, FlitType.HEAD, this, Simulator.clock));
			else if (i == numberOfFlit - 1) // Tail Flit
				flitList.add(new Flit(i, FlitType.TAIL, this, Simulator.clock));
			else // Data Flit
				flitList.add(new Flit(i, FlitType.BODY, this, Simulator.clock));
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

	public Flit getLastFlit() {
		return flitList.get(flitList.size() - 1);
	}

	/*
	 * Header Flit Only
	 */
	public void setDestinationInfo(coordinates dst) {
		// Get Header Flit
		Flit flit = flitList.get(0);
		flit.setDestinationInfo(dst);
	}

	public void addFlit(Flit flit) {
		flitList.add(flit);
	}

	@Override
	public String toString() {
		return "Packet ID : " + id;
	}
}
