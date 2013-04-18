package edu.moravian.tower.targettingSystem;

import edu.moravian.creep.Creep;
import edu.moravian.math.Point2D;
import java.util.Iterator;

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
    public Creep determineTarget(Iterator<Creep> potentialTargets)
      {
        Creep temp;
        if (potentialTargets.hasNext())
          {
            temp = potentialTargets.next();
          }
        else
          {
            return null;
          }

        //TODO is this right?
        double champDist = temp.getPosition().minus(this.getPosition()).magnitude();
        while (potentialTargets.hasNext())
          {
            Creep potential = potentialTargets.next();
            double tempDist = potential.getPosition().minus(this.getPosition()).magnitude();
            if ((tempDist < champDist) && this.withinRange(potential))
              {
                temp = potential;
              }
          }
        return temp;

      }
  }
