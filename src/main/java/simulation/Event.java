package simulation;

import architecture.Router;
import communication.Direction;

public class Event implements Comparable<Event> {

    private EventType eventType;
    private int time;
    private Router router;

    // Flit case
    private Direction direction;

    public Event(EventType eventType, int time, Router router) {
        this.eventType = eventType;
        this.time = time;
        this.router = router;
    }

    public Event(EventType eventType, int time, Router router, Direction direction) {
        this.eventType = eventType;
        this.time = time;
        this.router = router;
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

    public Router getRouter() {
        return router;
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
        return "EventType : " + eventType + ", Router : (" + router.getX() +
                "," + router.getY() + "), Time : " + time;
    }
}
