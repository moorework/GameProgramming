package edu.moravian.creep.pathfinding;

import edu.moravian.math.Point2D;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author myles
 */
public class Path {
    private ArrayList<Point2D> wayPoints;
    
    public Path(Collection<Point2D> wayPoints) {
        this.wayPoints = (ArrayList) wayPoints;
    }
    
    public boolean hasNextWayPoint() {
        return !wayPoints.isEmpty();
    }
    
    public Point2D getNextWayPoint() {
        return wayPoints.remove(0);
    }
}
