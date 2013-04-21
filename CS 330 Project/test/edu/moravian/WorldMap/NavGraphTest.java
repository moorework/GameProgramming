/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.WorldMap;

import edu.moravian.math.Point2D;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Myles
 */
public class NavGraphTest {

    @Test
    public void testAddRemoveVertex() {
        NavigationalGraph navGraph = new NavigationalGraph();
        
        PathCell pCell = new PathCell();
        pCell.setCenterPoint(new Point2D(1.0, 2.0));
        
        navGraph.addVertex(pCell);
        
        assertTrue(navGraph.containsVertex(pCell));
        
        navGraph.removeVertex(pCell);
        
        assertTrue(navGraph.containsVertex(pCell) == false);
    }
    
    @Test
    public void addEdgesRemoveTest() {
        NavigationalGraph navGraph = new NavigationalGraph();
        
        PathCell pCellA = new PathCell();
        pCellA.setCenterPoint(new Point2D(1.0, 2.0));
        
        PathCell pCellB = new PathCell();
        pCellB.setCenterPoint(new Point2D(8.0, -1.0));
        
        navGraph.addEdge(pCellA, pCellB, 1.0);
        
        assertTrue(navGraph.containsVertex(pCellA));
        assertTrue(navGraph.containsVertex(pCellB));
        
        assertTrue(navGraph.containsEdge(pCellA, pCellB));
        assertTrue(navGraph.containsEdge(pCellB, pCellA));
        
        navGraph.removeEdge(pCellA, pCellB);
        assertTrue(navGraph.containsEdge(pCellA, pCellB) == false);
        assertTrue(navGraph.containsEdge(pCellB, pCellA) == false);
    }
    
    @Test
    public void retrieveVerticesTest() {
        NavigationalGraph navGraph = new NavigationalGraph();
        
        PathCell pCellA = new PathCell();
        pCellA.setCenterPoint(new Point2D(1.0, 2.0));
        
        PathCell pCellB = new PathCell();
        pCellB.setCenterPoint(new Point2D(8.0, -1.0));
        
        navGraph.addVertex(pCellA);
        navGraph.addVertex(pCellB);
        
        assertEquals(2, navGraph.vertexSet().size());
        navGraph.addEdge(pCellA, pCellB, 1.0);
        assertEquals(2, navGraph.vertexSet().size());
    }
}
