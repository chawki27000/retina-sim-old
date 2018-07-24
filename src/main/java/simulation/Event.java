package simulation;

import architecture.Router;
import communication.Direction;
import communication.MessageInstance;

public class Event implements Comparable<Event> {

    private EventType eventType;
    private int time;
    private Router router_src;
    private Router router_dest;
    private MessageInstance message_instance;

    
    // Flit case
    private Direction direction;
    private int vcAllotted;

    public Event(EventType eventType, int time, Router router_src) {
        this.eventType = eventType;
        this.time = time;
        this.router_src = router_src;
    }

    public Event(EventType eventType, int time, Router router_src, Router dst,
                  MessageInstance message_instance) {
        this.eventType = eventType;
        this.time = time;
        this.router_src = router_src;
        this.router_dest = dst;
        this.message_instance = message_instance;
    }

    public MessageInstance getMessage_instance() {
		return message_instance;
	}

	public void setMessage_instance(MessageInstance message_instance) {
		this.message_instance = message_instance;
	}

	public Event(EventType eventType, int time, Router router_src, Direction direction, int vcAllotted) {
        this.eventType = eventType;
        this.time = time;
        this.router_src = router_src;
        this.direction = direction;
        this.vcAllotted = vcAllotted;
    }

    public Direction getDirection() {
        return direction;
    }

    public EventType getEventType() {
        return eventType;
    }

    public int getTime() {
        return time;
    }

    public Router getRouter_src() {
        return router_src;
    }

    public Router getRouter_dest() {
        return router_dest;
    }

    public int getVcAllotted() {
        return vcAllotted;
    }

    public void shift() {
        time++;
    }

    @Override
    public int compareTo(Event o) {
        if (o.time < time)
            return 0;
        else
            return 1;
    }

    @Override
    public String toString() {
        return "EventType : " + eventType + ", Router : (" + router_src.getX() +
                "," + router_src.getY() + "), Time : " + time;
    }
}
