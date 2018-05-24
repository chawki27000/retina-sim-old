package simulation;

import architecture.Router;
import communication.Direction;

public class Event implements Comparable<Event> {

    private EventType eventType;
    private int time;
    private Router router_src;
    private int[] router_dest;
    private int message;

    // Flit case
    private Direction direction;

    public Event(EventType eventType, int time, Router router_src) {
        this.eventType = eventType;
        this.time = time;
        this.router_src = router_src;
    }

    public Event(EventType eventType, int time, Router router_src, int[] router_dest, int message) {
        this.eventType = eventType;
        this.time = time;
        this.router_src = router_src;
        this.router_dest = router_dest;
        this.message = message;
    }

    public Event(EventType eventType, int time, Router router_src, Direction direction) {
        this.eventType = eventType;
        this.time = time;
        this.router_src = router_src;
        this.direction = direction;
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

    public int[] getRouter_dest() {
        return router_dest;
    }

    public int getMessageSize() {
        return message;
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
