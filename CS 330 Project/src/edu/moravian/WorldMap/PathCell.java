package edu.moravian.WorldMap;

/**
 *
 * @author myles
 */
public class PathCell implements WorldCell {
    private final boolean CAN_BE_OCCUPIED;
    private final boolean IS_PATHABLE;

    public PathCell()
    {
        CAN_BE_OCCUPIED = false;
        IS_PATHABLE = true;
    }
    
    @Override
    public boolean canBeOccupied()
    {
        return CAN_BE_OCCUPIED;
    }

    @Override
    public boolean isPathable()
    {
        return true;
    }
}
