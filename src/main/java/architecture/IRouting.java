package architecture;

import communication.Direction;
import communication.coordinates;

public interface IRouting {
    public Direction getRoutingDirection(coordinates crd);
}
