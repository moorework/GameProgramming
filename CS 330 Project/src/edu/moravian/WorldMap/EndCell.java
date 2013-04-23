package edu.moravian.WorldMap;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class EndCell extends PathCell {

    private final boolean CAN_BE_OCCUPIED;
    private final boolean IS_PATHABLE;

    public EndCell() {
        CAN_BE_OCCUPIED = false;
        IS_PATHABLE = true;
    }
}
