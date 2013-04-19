package edu.moravian.WorldMap;

import edu.moravian.math.Point2D;

/**
 *
 * @author myles
 */
public class PathCell implements WorldCell {
    private final boolean CAN_BE_OCCUPIED;
    private final boolean IS_PATHABLE;
    private Point2D centerPoint;

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
        return IS_PATHABLE;
    }
    
    protected void setCenterPoint(Point2D centerPoint)
    {
        this.centerPoint = centerPoint;
    }
    
    public Point2D getCenterPoint()
    {
        return centerPoint;
    }
}
