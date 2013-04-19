package edu.moravian.tower.targettingSystem;

import edu.moravian.creep.Creep;
import edu.moravian.math.Point2D;
import java.util.Iterator;

/**
 *
 * @author myles
 */
public class HardTargetingSystem extends TargetingSystem
  {

    public HardTargetingSystem(int radius, Point2D pos)
      {
        super(radius, pos);
      }

    @Override
    public Creep determineTarget(Iterator<Creep> potentialTargets)
      {
        // determine which creep within range has the most health

        //Get one creep to have a point to start comparing them
        Creep temp;
        if (potentialTargets.hasNext())
          {
            temp = potentialTargets.next();
          }
        else
          {
            return null;
          }

        double champHealth = temp.healthRemaining();
        while (potentialTargets.hasNext())
          {
            Creep potential = potentialTargets.next();
            double tempDist = temp.healthRemaining();
            if (tempDist < champHealth && this.withinRange(potential))
              {
                temp = potential;
              }
          }
        return temp;

      }
  }
//Try sorting by health remaining 
