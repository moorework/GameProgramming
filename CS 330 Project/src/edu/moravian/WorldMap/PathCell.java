package edu.moravian.WorldMap;

import edu.moravian.math.Point2D;

/**
 *
 * @author myles
 */
public class PathCell implements WorldCell {

    @Override
    public boolean canBeOccupied()
    {
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
        return true;
    }
}
