package architecture;

import communication.Direction;

public interface IRouting {
    public Direction getRoutingDirection(int dx, int dy);
}
