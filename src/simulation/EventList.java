package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EventList {

    private ArrayList<Event> eventQueue = new ArrayList<Event>();

    public void push(Event event) {
        eventQueue.add(event);
        Collections.sort(eventQueue, new Comparator<Event>() {
            @Override
            public int compare(Event ev_1, Event ev_2) {
                if (ev_1.getTime() > ev_2.getTime())
                    return 1;
                else
                    return -1;
            }
        });
    }

    public Event pull() {
        if (eventQueue.size() == 0)
            return null;
        return eventQueue.get(0);
    }


    public void removeEvent(Event ev) {
        eventQueue.remove(ev);
    }

    public boolean isEmpty() {
        return eventQueue.isEmpty();
    }

    @Override
    public String toString() {
        String str = "EventList : [ ";

        for (Event t : eventQueue) {
            str += ", " + t + "\n";
        }
        str += " ]";

        return str;
    }
}
