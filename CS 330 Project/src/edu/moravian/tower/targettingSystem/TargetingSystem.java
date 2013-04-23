package edu.moravian.tower.targettingSystem;

import edu.moravian.creep.Creep;
import edu.moravian.math.Point2D;
import java.util.Iterator;

/**
 *
 * @author myles
 */
public abstract class TargetingSystem
  {
    private int visionRadius;
    private Point2D position;
    
    private final TargettingType targettingType;

    public TargetingSystem(int radius, Point2D pos, TargettingType targettingType)
      {
        visionRadius = radius;
        position = pos;
        
        this.targettingType = targettingType;
      }

    public abstract Creep determineTarget(Iterator<Creep> potentialTargets);

    /*
     * Determines whethet a creep is within the range of the targeting system.
     */
    protected boolean withinRange(Creep creep)
      {

        Point2D creepPos = creep.getPosition(); // get the position of the creep

        // determine the absolute distance between the creep and our position
        double distance = creepPos.minus(this.getPosition()).magnitude();
        // subtract our viewing radius from the distance...
        distance = distance - visionRadius;

        // if the result is positive, then the creep is outside of our area of
        // sight
        if (distance > 0)
          {
            return false;
          } 
        else
          { // otherwise the result is negative, in which case the creep is
            // within range
            return true;
          }
      }

    public boolean hasTarget(Iterator<Creep> potentialTargets)
      {    
        //TODO make this better

        while (potentialTargets.hasNext())
          {
            Creep tmp = potentialTargets.next();
            if (this.withinRange(tmp))
              {
                return true;
              }

          }

        return false;

      }

    public Point2D getPosition()
      {
        return position;
      }
    
    public TargettingType typeOfTower() {
        return targettingType;
    }
    
  }
