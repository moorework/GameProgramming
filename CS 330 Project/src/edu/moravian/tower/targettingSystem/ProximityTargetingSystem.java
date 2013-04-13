package edu.moravian.tower.targettingSystem;

import edu.moravian.creep.Creep;
import edu.moravian.math.Point2D;

/**
 *
 * @author myles
 */
public class ProximityTargetingSystem extends TargetingSystem
{

    public ProximityTargetingSystem(int radius, Point2D pos)
    {
        super(radius, pos);
    }

    @Override
    public Creep determineTarget(Iterable<Creep> potentialTargets)
    {
        Creep temp;
        if (potentialTargets.iterator().hasNext())
        {
            temp = potentialTargets.iterator().next();
        }
        else
        {
            return null;
        }

        //TODO is this right?
        double champDist = temp.getPosition().minus(temp.getPosition()).magnitude();
        for (Creep potential : potentialTargets)
        {
            double tempDist = potential.getPosition().minus(potential.getPosition()).magnitude();
            if (tempDist < champDist && this.withinRange(potential))
            {
                temp = potential;
            }
        }
        return temp;

    }
}
