package simulation_gen;

import architecture.Router;
import communication.Direction;
import communication.Flit;
import simulation.Event;
import simulation.EventList;

import java.util.ArrayList;

public class Simulator {

    public static EventList eventList;
    private int simulationPeriod;
    public static int clock;
    public static ArrayList<Trace> traceList;

    private int message;
    private int[] dest;

    public Simulator(int simulationPeriod) {
        this.simulationPeriod = simulationPeriod;
        clock = 0;
        traceList = new ArrayList<>();
    }

    public void simulate() {
        Flit flit = null;
        while (!eventList.isEmpty() && clock < simulationPeriod) {

            Event curr_ev = eventList.pull();
            eventList.removeEvent(curr_ev);

            Router router = curr_ev.getRouter_src();

            switch (curr_ev.getEventType()) {
	    case MESSAGE_SEND:
		message = curr_ev.getMessageSize();
		dest = curr_ev.getRouter_dest();
		router.sendMessage(message, dest);
		creer_evenement_process_vc();
		//          clock++; // ## clock ##
		break;
		    
	    case SEND_HEADER_FLIT:
		if (router.pe.isEmpty()) {
		    break;
		}
		
		flit = router.pe.pullFlit();		
		router.sendFlit(flit);
		// clock++; // ## clock ##
		break;
	    case SEND_FLIT:
		// clock+1;
		eliginleToSend();
	        EST_dernierFLIT();
		break;
	    case SEND_TAIL:
		
		break;
	    case FORWARD_FLIT:
		// get the direction
		Direction direction = curr_ev.getDirection();
		
		if (direction == Direction.EAST)
		    flit = router.getInRight().getVclist().get(0).dequeueFlit();
		else if (direction == Direction.WEST)
		    flit = router.getInLeft().getVclist().get(0).dequeueFlit();
		else if (direction == Direction.NORTH)
		    flit = router.getInUp().getVclist().get(0).dequeueFlit();
		else if (direction == Direction.SOUTH)
		    flit = router.getInDown().getVclist().get(0).dequeueFlit();
		
		router.sendFlit(flit);
		//                    clock++; // ## clock ##
		break;

                case RECEIVE_FLIT:
                    break;

                default:
                    break;
            }
        }
    }
}
