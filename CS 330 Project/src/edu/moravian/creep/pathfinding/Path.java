package edu.moravian.creep.pathfinding;

import edu.moravian.math.Point2D;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author myles
 */
public class Path
{

    private ArrayList<Point2D> waypoints;

    @SuppressWarnings("unchecked")
    public Path(Collection<Point2D> waypoints)
    {
        this.waypoints = (ArrayList) waypoints;
    }

    /**
     * Determines whether the path contains further waypoints.
     *
     * @return true if there is at least one more waypoint, false otherwise.
     */
    public boolean hasNextWayPoint()
    {
        return !waypoints.isEmpty();
    }

    /**
     * Retrieve the next waypoint on the Path. Note that each waypoint can only
     * be retrieved once.
     *
     * @return the next waypoint on the path
     */
    public Point2D getNextWayPoint()
    {
        return waypoints.remove(0);
    }
}
