package edu.moravian.tower.targettingSystem;

import edu.moravian.creep.BasicCreep;
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
        super(radius, pos, TargettingType.HARD);
      }

    @Override
    public Creep determineTarget(Iterator<BasicCreep> potentialTargets)
      {
        // determine which creep within range has the most health

        //Get one creep to have a point to start comparing them
        BasicCreep temp;
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
            BasicCreep potential = potentialTargets.next();
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
