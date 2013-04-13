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
        Creep temp;
        if (potentialTargets.iterator().hasNext())
        {
            temp = potentialTargets.iterator().next();
        }
        else
        {
            return null;
        }

        double champDist = temp.healthRemaining();
        for (Creep potential : potentialTargets)
        {
            double tempDist = temp.healthRemaining();
            if (tempDist < champDist && this.withinRange(potential))
            {
                temp = potential;
            }
        }
        return temp;

    }

}
