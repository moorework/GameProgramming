/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.creep.pathfinding;

import edu.moravian.WorldMap.NavGraph;
import edu.moravian.WorldMap.PathCell;
import edu.moravian.math.Point2D;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Worst tests ever written; do not judge me.
 * 
 * 
 * @author myles
 */
public class PathFinderTest {
    private NavGraph twoPathGraphModel;
    private PathCell twoPathGoalCell;
    private PathCell twoPathStartCell;
    
    private NavGraph complexGraphModel;
    private PathCell complexGoalCell;
    private PathCell complexStartCell;
    
    public PathFinderTest() {
        generateTwoPathGraph();
        generateComplexGraph();
    }
    
    private void generateTwoPathGraph()
    {
        twoPathGraphModel = new NavGraph();
        
        
        twoPathGoalCell = new PathCell(false, true);
        twoPathGoalCell.setCenterPoint(new Point2D(15, 50));
        
        twoPathStartCell = new PathCell(true, false);
        twoPathStartCell.setCenterPoint(new Point2D(0, 5));
        
        // add short path
        double weight = 2;
        PathCell currCell = twoPathStartCell;
        PathCell newCell;
        for (int x = 5; x < 15; x += 5) {
            for (int y = 20; y < 50; y += 15) {
                newCell = new PathCell(false, false);
                newCell.setCenterPoint(new Point2D(x, y));
                
                twoPathGraphModel.addEdge(currCell, newCell, weight);
                
                currCell = newCell;
            }
        }
        // add final node on short path to the goal
        twoPathGraphModel.addEdge(currCell, twoPathGoalCell, weight);
        
        // add long path
        weight = 10;
        currCell = twoPathStartCell;
        for (int x = 1; x < 15; x += 5) {
            for (int y = 5; y > -25; y -= 15) {
                newCell = new PathCell(false, false);
                newCell.setCenterPoint(new Point2D(x, y));
                
                twoPathGraphModel.addEdge(currCell, newCell, weight);
                
                currCell = newCell;
            }
        }
        // add final node on long path to the goal
        twoPathGraphModel.addEdge(currCell, twoPathGoalCell, weight);
    }
    
    private void generateComplexGraph() {
        
        complexGraphModel = new NavGraph();
        
        
        complexGoalCell = new PathCell(false, true);
        complexGoalCell.setCenterPoint(new Point2D(55, 1));
        
        complexStartCell = new PathCell(true, false);
        complexStartCell.setCenterPoint(new Point2D(5, 2));
        
        // dead end 1
        PathCell cellA = new PathCell(false, false);
        cellA.setCenterPoint(new Point2D(3, 10));
        
        complexGraphModel.addEdge(complexStartCell, cellA, 0.2);
        
        // dead end 1
        PathCell cellB = new PathCell(false, false);
        cellB.setCenterPoint(new Point2D(2, 18));
        
        complexGraphModel.addEdge(cellA, cellB, 0.2);
        
        // shortest path 1
        PathCell cellC = new PathCell(false, false);
        cellC.setCenterPoint(new Point2D(15, 2));
        
        complexGraphModel.addEdge(complexStartCell, cellC, 1.0);
        
        // cell where the two shortest paths converge
        PathCell cellD = new PathCell(false, false);
        cellD.setCenterPoint(new Point2D(25, 2));
        
        complexGraphModel.addEdge(cellC, cellD, 1.1);
        
        // part of both shortest paths
        PathCell cellE = new PathCell(false, false);
        cellE.setCenterPoint(new Point2D(40, 2));
       
        complexGraphModel.addEdge(cellD, cellE, 1.0);
        
        // end of all paths that arrive at the goal
       
        complexGraphModel.addEdge(cellE, complexGoalCell, 1.0);
        
        // shortest path 2
        PathCell cellF = new PathCell(false, false);
        cellF.setCenterPoint(new Point2D(5, 0));
        
        complexGraphModel.addEdge(complexStartCell, cellF, 0.5);
        
        // dead end 2
        PathCell cellG = new PathCell(false, false);
        cellG.setCenterPoint(new Point2D(5, -2));

        complexGraphModel.addEdge(cellF, cellG, 0.1);
        
        // shortest path 2
        PathCell cellH = new PathCell(false, false);
        cellH.setCenterPoint(new Point2D(15, -1));
        complexGraphModel.addEdge(cellG, cellH, 0.5);
        
        // shortest path 2
        PathCell cellI = new PathCell(false, false);
        cellI.setCenterPoint(new Point2D(25, -1));
        
        complexGraphModel.addEdge(cellH, cellI, 0.5);
        
        // cell where the two shortest paths converge
        complexGraphModel.addEdge(cellI, cellD, 0.5);
    }
    
    @Test
    public void testPathFinderTwoPaths() {
        // tests the pathFinder using a graph that has two paths from the start to
        // the goal 
        
        // get the centerPoint of the startCell
        Point2D startCellPoint = twoPathStartCell.getCenterPoint();
        // our starting position will be close to the StartCell but not at the
        // StartCell itself. This is to ensure that we properly identify the
        // StartCell as our first waypoint
        Point2D startPoint = new Point2D(startCellPoint.getX() - 0.5, startCellPoint.getY() - 0.5);
        
        // generate a path from our starting position to our goal position given
        // our pre-constructed graph; this path is what we are interested in
        Path foundPath = PathFinder.generatePath(twoPathGraphModel, startPoint, twoPathGoalCell.getCenterPoint());
        
        // represents the actual shortest path through the graph
        ArrayList<Point2D> actualShortestPath = new ArrayList<Point2D>();
        
        // add the startPoint, as that will naturally be the first waypoint
        actualShortestPath.add(twoPathStartCell.getCenterPoint());
        // compute each of the cells in the graph that lie along the shortest path
        for (int x = 5; x < 15; x += 5) {
            for (int y = 20; y < 50; y += 15) {
                // add them to our expected shortest path
                actualShortestPath.add(new Point2D(x, y));
            }
        }
        
        actualShortestPath.add(twoPathGoalCell.getCenterPoint());

        for (int i = 0; i < actualShortestPath.size(); i++) {
            System.out.println(actualShortestPath.get(i));
        }
        
        comparePaths(foundPath, actualShortestPath);
    }
    
    @Test
    public void testEquivalentAlternatePaths() {
        // tests the pathFinder using a graph that contains two equivalent paths
        // and ensure that they do not become enmeshed
        
        // get the centerPoint of the startCell
        Point2D startCellPoint = complexStartCell.getCenterPoint();
        // our starting position will be close to the StartCell but not at the
        // StartCell itself. This is to ensure that we properly identify the
        // StartCell as our first waypoint
        Point2D startPoint = new Point2D(startCellPoint.getX() - 0.1, startCellPoint.getY() + 0.2);
        
        // generate a path from our starting position to our goal position given
        // our pre-constructed graph; this path is what we are interested in
        Path foundPath = PathFinder.generatePath(complexGraphModel, startPoint, complexGoalCell.getCenterPoint());
        
        // the path should have waypoints
        assertTrue(foundPath.hasNextWayPoint());
        
        // represents the actual shortest paths through the graph
        ArrayList<Point2D> shortestPathA = new ArrayList<Point2D>();
        ArrayList<Point2D> shortestPathB = new ArrayList<Point2D>();
        
        shortestPathA.add(complexStartCell.getCenterPoint());
        shortestPathB.add(complexStartCell.getCenterPoint());
        
        shortestPathA.add(new Point2D(15, 2));
        shortestPathA.add(new Point2D(25, 2));
        shortestPathA.add(new Point2D(40, 2));
        shortestPathA.add(complexGoalCell.getCenterPoint());
        
        shortestPathB.add(new Point2D(5, 0));
        shortestPathB.add(new Point2D(5, -2));
        shortestPathB.add(new Point2D(15, -1));
        shortestPathB.add(new Point2D(25, -1));
        shortestPathB.add(new Point2D(25, 2));
        shortestPathB.add(new Point2D(40, 2));
        shortestPathB.add(complexGoalCell.getCenterPoint());
        
        
        // ensure that the generated path is one of the two shortest paths
        if (foundPath.numWayPoints() == 5) {
            comparePaths(foundPath, shortestPathA);
        }
        else if (foundPath.numWayPoints() == 8) {
            comparePaths(foundPath, shortestPathB);
        }else{
        
        fail("Generated path did not match either of equivalent shortest paths");
        }
    }
    
    private void comparePaths(Path generatedPath, ArrayList<Point2D> expectedShortest) {
        // ensure that every point in the true shortest path is also in the
        // produced shortest path and that they're in the same order
        //
        // while the generated path still has more waypoints...
        
        while (generatedPath.hasNextWayPoint() == true) {
            // the current waypoint should be identical to the waypoint in the
            // actual shortest path
            assertEquals(expectedShortest.remove(0), generatedPath.getNextWayPoint());
        }
    }
    
}
