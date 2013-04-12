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
        Creep closestCreep = null; // the Creep closest to us
        double closestDistance = Double.MAX_VALUE; // the distance from us to that Creep
        
        Point2D creepPos; // the position of the current Creep
        double distance; // the distance from us to them
        
        for (Creep currCreep : potentialTargets) // for each Creep...
        {
            if (withinRange(currCreep)) { // the Creep is within range...
                
                creepPos = currCreep.getPosition(); // get the Creep's position
                // determine the distance between us and the Creep
                distance = creepPos.minus(position).magnitude();
                
                // if the Creep is closer to us than the previous closest Creep...
                if (distance < closestDistance) {
                    // we have found a new closestCreep
                    closestCreep = currCreep;
                    closestDistance = distance;
                }
            }
            
        }
        
        return closestCreep;
    }

}
