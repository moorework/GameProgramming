package edu.moravian.tower.targettingSystem;

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
        
        //Get one creep to have a point to start comparing them
        Creep temp;
        if (potentialTargets.iterator().hasNext())
        {
            temp = potentialTargets.iterator().next();
        }
        else
        {
            return null;
        }

        double champHealth = temp.healthRemaining();
        for (Creep potential : potentialTargets)
        {
            double tempDist = temp.healthRemaining();
            if (tempDist < champHealth && this.withinRange(potential))
            {
                temp = potential;
            }
        }
        return temp;

    }

}
