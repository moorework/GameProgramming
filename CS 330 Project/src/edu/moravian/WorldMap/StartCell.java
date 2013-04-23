package edu.moravian.WorldMap;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class StartCell extends PathCell {

    private final boolean CAN_BE_OCCUPIED;
    private final boolean IS_PATHABLE;

    public StartCell() {
        CAN_BE_OCCUPIED = false;
        IS_PATHABLE = true;
    }
}
