package edu.moravian.WorldMap;

import edu.moravian.math.Point2D;

/**
 *
 * @author myles
 */
public class PathCell implements WorldCell {
    private final boolean CAN_BE_OCCUPIED;
    private final boolean IS_PATHABLE;
    
    private boolean spawnCell;
    private boolean endCell;
    private Point2D centerPoint;
    
    public PathCell(boolean isSpawnCell, boolean isEndCell)
    {
        CAN_BE_OCCUPIED = false;
        IS_PATHABLE = true;
        
        spawnCell = isSpawnCell;
        endCell = isEndCell;
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
    
    public void setCenterPoint(Point2D centerPoint)
    {
        this.centerPoint = centerPoint;
    }
    
    public Point2D getCenterPoint()
    {
        return centerPoint;
    }
    
    public boolean isSpawn() {
        return spawnCell;
    }
    
    public boolean isEnd() {
        return endCell;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof PathCell == false) {
            return false;
        }
        
        PathCell pc = (PathCell) o;
        
        if (pc.getCenterPoint() != centerPoint) {
            return false;
        }
        
        return true;
    }
    

    

}
