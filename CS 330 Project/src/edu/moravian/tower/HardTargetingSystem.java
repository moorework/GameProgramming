package edu.moravian.tower;

import edu.moravian.creep.Creep;
import edu.moravian.math.Point2D;

/**
 *
 * @author myles
 */
public class HardTargetingSystem extends TargetingSystem {

    public HardTargetingSystem(int radius, Point2D pos)
    {
        super(radius, pos);
    }
    
    @Override
    public Creep determineTarget(Iterable<Creep> potentialTargets)
    {
        // determine which creep within range has the most health
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
