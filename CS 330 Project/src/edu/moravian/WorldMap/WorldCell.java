
package edu.moravian.WorldMap;

import edu.moravian.math.Point2D;


/**
 *
 * @author myles
 */
public interface WorldCell {
    
    public boolean isPathable();
    
    public boolean canBeOccupied();
    
    public Point2D getCornerPoint();
}
