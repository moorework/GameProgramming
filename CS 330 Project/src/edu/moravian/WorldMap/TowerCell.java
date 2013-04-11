package edu.moravian.WorldMap;

import edu.moravian.math.Point2D;

/**
 *
 * @author myles
 */
public class TowerCell implements WorldCell {

    @Override
    public boolean canBeOccupied()
    {
        return true;
    }
    
    public boolean isOccupied()
    {
        // TODO implement me
        return false;
    }
    
    public boolean setOccupied()
    {
        // TODO implement me
        return false;
    }
    
    public boolean setUnOccupied()
    {
        // TODO implement me
        return false;
    }

    @Override
    public Point2D getCornerPoint()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isPathable()
    {
        return false;
    }
}
