package architecture;

import communication.Direction;

public interface Routing {
    public Direction getRoutingDirection(int dx, int dy);
}
