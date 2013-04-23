package edu.moravian.creep.pathfinding;

import edu.moravian.WorldMap.NavGraph;
import edu.moravian.WorldMap.PathCell;
import edu.moravian.math.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;


/**
 *
 * @author myles
 */
public class PathFinder {

    public static Path generatePath(NavGraph navGraph, Point2D startingPoint, Point2D targetPoint)
    {
        ArrayList<Point2D> path = new ArrayList<Point2D>();

        // we start by finding the PathCell closest to our startingPoint and targetPoints;
        // the closest startingPoint will also be our first waypoint.

        System.out.println("start point passed: " + startingPoint);

        PathCell startCell = getClosestCellToPoint(navGraph.vertexSet(), startingPoint);
        PathCell goalCell = getClosestCellToPoint(navGraph.vertexSet(), targetPoint);

        System.out.println("startCell: " + startCell.getCenterPoint());
        System.out.println("goalCell: " + goalCell.getCenterPoint());

        // for every PathCell in the shortest distance computed from the startCell
        // to the goalCell...
        for (PathCell pCell : computeShortestDist(navGraph, startCell, goalCell)) {
            // add that cell to our path
            path.add(pCell.getCenterPoint());
        }

        return new Path(path);
    }

    /*
     * Compute the shortest distance in the NavGraph between the startingNode and
     * the targetNode
     */
    private static Collection<PathCell> computeShortestDist(NavGraph graph, PathCell startingNode, PathCell targetNode)
    {
        // distance from start to a node
        HashMap<PathCell, Double> distance = new HashMap<PathCell, Double>();
        // the predecessor of a node in the shortest path
        HashMap<PathCell, PathCell> predecessor = new HashMap<PathCell, PathCell>();
        // priority queue
       
        BetterPriorityQueue bpQueue = new BetterPriorityQueue(new PathCellNodeComparator(distance));

        Iterator<PathCell> foo = graph.vertexSet().iterator();

        // for each vertex in the graph
        //for (PathCell pCell : graph.vertexSet()) {
        for (int i = 0; i < graph.vertexSet().size(); i++) {
            PathCell pCell = foo.next();
            // the distance is as far as possible
            distance.put(pCell, Double.MAX_VALUE);
            // there is no predecessor
            predecessor.put(pCell, null);

            bpQueue.offer(pCell);
        }



        // start the process by adding our startingNode to the graph. The distance
        // from it to itself is 0
        distance.put(startingNode, 0.0);

        PathCell currCell; // the cell in the queue with the lowest distance from
        // the starting cell
        double dist; // the distance between start and a neighbor of currCell
        while (bpQueue.isEmpty() == false) { // while the queue is not empty
            // pull out the cell with the lowest distance from the start
            currCell = (PathCell) bpQueue.getSomeShit();
            System.out.println("Polled: " + currCell.getCenterPoint() + " *dist*: " + distance.get(currCell));
            // for every neighbor of currCell
            for (PathCell adjCell : graph.edgesOf(currCell)) {
                // compute the distance between start and the neighbor to currCell, adjCell,
                // through currCell
                dist = distance.get(currCell) + graph.getEdgeWeight(currCell, adjCell);

                // if the distance to the neighbor is shorter through currCell than
                // the current path to it...
                if (distance.get(adjCell) > dist) {

                    //System.out.println("Better distance:  " + dist);
                    // the new distance from start to the neighbor is that distance
                    distance.put(adjCell, dist);
                    // the predecessor to the neighbor is currCell
                    predecessor.put(adjCell, currCell);
                }
            }
        }

        // the priority queue is now empty, so let's use the information to find
        // the shortest path by working backwards from the targetNode through
        // the predecessors

        ArrayList<PathCell> shortestPath = new ArrayList<PathCell>();


        currCell = targetNode;
        // while the current cell still has a predecessor...
        while (currCell != null) {
            // add the current cell to the beginning of the path
            shortestPath.add(0, currCell);
            // swap it out with its predecessor to repeat the process
            currCell = predecessor.get(currCell);
        }

        return shortestPath;
    }

    private static PathCell getClosestCellToPoint(Collection<PathCell> cells, Point2D point)
    {
        double dist; // distance between pCell and startingPoint
        Point2D tempCenterPoint; // pCell's centerPoint
        double closestDist = Double.MAX_VALUE; // distance of closest pCell yet found
        PathCell closestPathCell = null; // the closest PathCell we've found to
        // the startingPoint
        for (PathCell pCell : cells) { // for each PathCell...
            tempCenterPoint = pCell.getCenterPoint(); // retrieve the centerPoint

            // compute the distance between the pCell and the centerPoint
            dist = tempCenterPoint.minus(point).magnitude();

            // if the distance is less than the closest we've yet found then
            // we've found a new closestPoint
            if (dist < closestDist) {
                closestPathCell = pCell;
                closestDist = dist;
            }
        }

        return closestPathCell;
    }

    // private class that allows PathCells to be compared based on edge weight
    private static class PathCellNodeComparator implements Comparator<PathCell> {

        private HashMap<PathCell, Double> distance;

        public PathCellNodeComparator(HashMap<PathCell, Double> weightKey)
        {
            distance = weightKey;
        }

//        @Override
//        public int compare(Object t, Object t1)
//        {
//            PathCell cell = (PathCell) t;
//            PathCell cell2 = (PathCell) t1;
//
//            double cellWeight = distance.get(cell);
//            double cell2Weight = distance.get(cell2);
//
//            if (cellWeight < cell2Weight) {
//                return -1;
//            } else if (cellWeight > cell2Weight) {
//                return 1;
//            }
//            return 0;
//        }
        @Override
        public int compare(PathCell t, PathCell t1)
        {
            PathCell cell = t;
            PathCell cell2 = t1;

            double cellWeight = distance.get(cell);
            double cell2Weight = distance.get(cell2);

            if (cellWeight < cell2Weight) {
                return -1;
            } else if (cellWeight > cell2Weight) {
                return 1;
            }
            return 0;
        }
    }

    private static class BetterPriorityQueue {
        LinkedList<PathCell> list;
        PathCellNodeComparator comparator;
        
        boolean queuePrioritized;
        
        public BetterPriorityQueue(PathCellNodeComparator comparator_in)
        {
            list = new LinkedList<PathCell>();
            comparator = comparator_in;
            
            queuePrioritized = false;
        }
        
        public void offer(PathCell cell){
            list.add(cell);
            
            queuePrioritized = false;
        }
        
        public PathCell getSomeShit(){
            if (queuePrioritized = false) {
                Collections.sort(list, comparator);
                queuePrioritized = true;
            }
            
            return list.pop();
        }

        private boolean isEmpty()
        {
            return list.isEmpty();
        }
        
        
        
    }
}
