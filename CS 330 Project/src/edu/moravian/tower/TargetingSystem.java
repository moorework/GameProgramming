package edu.moravian.tower;

import edu.moravian.creep.Creep;
import edu.moravian.math.Point2D;

/**
 *
 * @author myles
 */
public abstract class TargetingSystem {
    private int visionRadius;
    private Point2D position;
    
    public TargetingSystem(int radius, Point2D pos)
    {
        visionRadius = radius;
        position = pos;
    }
    
    public abstract Creep determineTarget(Iterable<Creep> potentialTargets);
    
    /*
     * Determines whethet a creep is within the range of the targeting system.
     */
    protected boolean withinRange(Creep creep)
    {
        Point2D creepPos = creep.getPosition(); // get the position of the creep
        
        // determine the absolute distance between the creep and our position
        double distance = creepPos.minus(creepPos).magnitude();
        // subtract our viewing radius from the distance...
        distance = distance - visionRadius;
        
        // if the result is positive, then the creep is outside of our area of
        // sight
        if (distance > 0) {
            return false;
        }
        else { // otherwise the result is negative, in which case the creep is
               // within range
            return true;
        }
    }
}
