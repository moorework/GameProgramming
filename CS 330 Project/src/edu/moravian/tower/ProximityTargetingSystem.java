package edu.moravian.tower;

import edu.moravian.creep.Creep;
import edu.moravian.math.Point2D;

/**
 *
 * @author myles
 */
public class ProximityTargetingSystem extends TargetingSystem {

    public ProximityTargetingSystem(int radius, Point2D pos)
    {
        super(radius, pos);
    }
    
    @Override
    public Creep determineTarget(Iterable<Creep> potentialTargets)
    {
        // determine which creep is nearest
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
