/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.pathfinding;

import edu.moravian.creep.pathfinding.Path;
import edu.moravian.math.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author myles
 */
public class PathTest {
    private int X_UPPER_BOUND = 100;
    private int Y_UPPER_BOUND = 100;
    
    private ArrayList<Point2D> generateWayPoints(int numPoints) {
        ArrayList<Point2D> wayPoints = new ArrayList<Point2D>();
        
        double xPoint;
        double yPoint;
        for (int i = 0; i < numPoints; i++) {
            xPoint = Math.random() * X_UPPER_BOUND;
            yPoint = Math.random() * Y_UPPER_BOUND;
            
            wayPoints.add(new Point2D(xPoint, yPoint));
        }
        
        return wayPoints;
    }

    @Test
    public void pathTest() {
        ArrayList<Point2D> wayPoints = generateWayPoints(10);
        
        ArrayList<Point2D> pathArg = new ArrayList<Point2D>();
        for (int i = 0; i < wayPoints.size(); i++) {
            pathArg.add(wayPoints.get(i));
        }
        
        Path path = new Path(pathArg);
        
        int numWayPoints = 0;
        while (path.hasNextWayPoint()) {
            assertTrue(wayPoints.contains(path.getNextWayPoint()));
            numWayPoints++;
        }
        
        assertEquals(10, numWayPoints);
    }
}
