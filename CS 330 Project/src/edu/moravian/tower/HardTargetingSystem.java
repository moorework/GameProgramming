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
        Creep healthiestCreep = null; // the creep with the most health
        int maxHealth = 0; // the current health of the healthiest creep
        
        for (Creep currCreep : potentialTargets) { // for each Creep
            if (withinRange(currCreep)) { // if the Creep is within striking distance...
                
                // if the Creep is the healthiest Creep we've yet seen...
                if (currCreep.healthRemaining() > maxHealth) {
                    // then they become our new healthiest
                    healthiestCreep = currCreep;
                    maxHealth = currCreep.healthRemaining();
                }
                
            }
        }
        
        return healthiestCreep;
    }

}
