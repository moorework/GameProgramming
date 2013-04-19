package edu.moravian.creep.pathfinding;

import edu.moravian.WorldMap.NavGraph;
import edu.moravian.WorldMap.PathCell;
import edu.moravian.math.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author myles
 */
public class PathFinder {

    public static Path generatePath(NavGraph navGraph, Point2D startingPoint, Point2D targetPoint) {
        ArrayList<Point2D> path = new ArrayList<Point2D>();
        
        // we start by finding the point closest to our startingPoint and targetPoints;
        // the closest startingPoint will also be our first waypoint.
        
        PathCell startCell = getClosestCellToPoint(navGraph.vertexSet(), startingPoint);
        PathCell goalCell = getClosestCellToPoint(navGraph.vertexSet(), targetPoint);
        
        for (PathCell pCell : computeShortestDist(navGraph, startCell, goalCell)) {
            path.add(0, pCell.getCenterPoint());
        }
        
        return new Path(path);
    }
    
    private static Collection<PathCell> computeShortestDist(NavGraph graph, PathCell startingNode, PathCell targetNode) {
        // distance from start to a node
        HashMap<PathCell, Double> distance = new HashMap<PathCell, Double>();
        // the predecessor of a node in the shortest path
        HashMap<PathCell, PathCell> predecessor = new HashMap<PathCell, PathCell>();
        // priority queue
        PriorityQueue pQueue = new PriorityQueue(graph.vertexSet().size(), new PathCellNodeComparator(distance));
        
        
        // for each vertex in the graph
        for (PathCell pCell : graph.vertexSet()) {
            // the distance is as far as possible
            distance.put(pCell, Double.MAX_VALUE);
            // there is no predecessor
            predecessor.put(pCell, null);
            
            pQueue.add(pCell);
        }
        
        // start the process by adding our startingNode to the graph. The distance
        // from it to itself is 0
        distance.put(startingNode, 0.0);
        
        PathCell currCell; // the cell in the queue with the lowest distance from
                           // the starting cell
        double dist; // the distance between start and a neighbor of currCell
        while (pQueue.isEmpty() == false) { // whille the queue is not empty
            // pull out the cell with the lowest distance from the start
            currCell = (PathCell) pQueue.poll();
            
            // for every neighbor of currCell
            for (PathCell adjCell : graph.edgesOf(currCell)) {
                // compute the distance between start and the neighbor to currCell, adjCell
                dist = distance.get(currCell) + graph.getEdgeWeight(currCell, adjCell);
                
                // if the distance to the neighbor is shorter through currCell than
                // the current path to it...
                if (distance.get(adjCell) > dist) {
                    // the new distance from start to the neighbor is that distance
                    distance.put(adjCell, dist);
                    // the predecessor to the neighbor is currCell
                    predecessor.put(adjCell, currCell);
                }
            }
        }
        
        // the priority queue is now empty, so let's use the information to find
        // the shortest path from 
        
        ArrayList<PathCell> shortestPath = new ArrayList<PathCell>();
        
        currCell = targetNode;
        while (currCell != null) {
            shortestPath.add(0, currCell);
            
            currCell = predecessor.get(currCell);
        }
        
        return shortestPath;
    }
    
    private static PathCell getClosestCellToPoint(Collection<PathCell> cells, Point2D point) {
        
        double dist; // distance between pCell and startingPoint
        Point2D tempCenterPoint; // pCell's centerPoint
        double closestDist = -10000.0; // distance of closest pCell yet found
        PathCell closestPathCell = null; // the closest PathCell we've found to
                                         // the startingPoint
        for (PathCell pCell : cells) { // for each PathCell...
            tempCenterPoint = pCell.getCenterPoint(); // retrieve the centerPoint
            // compute the distance between the pCell and the centerPoint
            dist = tempCenterPoint.minus(point).magnitude();
            
            // if the distance is less than the closest we've yet found then
            // we've found a new closestPoint
            if (dist < closestDist) {closestPathCell = pCell;}
        }
        
        return closestPathCell;
    }
    
    
        private static class PathCellNodeComparator implements Comparator {
            private HashMap<PathCell, Double> distance;
            
            public PathCellNodeComparator(HashMap<PathCell, Double> weightKey) {
                distance = weightKey;
            }
            
        @Override
        public int compare(Object t, Object t1)
        {
            PathCell cell = (PathCell) t;
            PathCell cell2 = (PathCell) t1;
            
            double cellWeight = distance.get(cell);
            double cell2Weight = distance.get(cell2);
            
            if (cellWeight < cell2Weight) {
                return -1;
            }
            else if (cellWeight > cell2Weight) {
                return 1;
            }
            
            return 0;
        }
        
    }
}
