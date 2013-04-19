package edu.moravian.WorldMap;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class StartCell implements WorldCell {

    private final boolean CAN_BE_OCCUPIED;
    private final boolean IS_PATHABLE;

    public StartCell() {
        CAN_BE_OCCUPIED = false;
        IS_PATHABLE = true;
    }

    @Override
    public boolean canBeOccupied() {
        return CAN_BE_OCCUPIED;
    }

    @Override
    public boolean isPathable() {
        return true;
    }
}
