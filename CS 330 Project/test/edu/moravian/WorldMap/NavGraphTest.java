/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.WorldMap;

import edu.moravian.math.Point2D;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author myles
 */
public class NavGraphTest {
    
    @Test
    public void testProperVertexAdding() {
        PathCell pCell1 = new PathCell();
        PathCell pCell2 = new PathCell();
        
        pCell1.setCenterPoint(new Point2D(50, 70));
        pCell2.setCenterPoint(new Point2D(120.5, 1.1));
        
        NavGraph ngraph = new NavGraph();
        
        ngraph.addVertex(pCell1);
        ngraph.addVertex(pCell2);
        
        ngraph.addEdge(pCell1, pCell2, 1.5);
        
        assertEquals(1.5, ngraph.getEdgeWeight(pCell1, pCell2), 0.0);
        assertEquals(1.5, ngraph.getEdgeWeight(pCell2, pCell1), 0.0);
    }
    
    @Test
    public void testBadVertexAdding() {
        PathCell pCell1 = new PathCell();
        PathCell pCell2 = new PathCell();
        
        pCell1.setCenterPoint(new Point2D(50, 70));
        pCell2.setCenterPoint(new Point2D(120.5, 1.1));
        
        NavGraph ngraph = new NavGraph();
        
        ngraph.addEdge(pCell1, pCell2, 1.5);
        
        assertEquals(1.5, ngraph.getEdgeWeight(pCell1, pCell2), 0.0);
        assertEquals(1.5, ngraph.getEdgeWeight(pCell2, pCell1), 0.0);
    }
    
    @Test
    public void testNaiveVertexAdding() {
        PathCell pCell1 = new PathCell();
        PathCell pCell2 = new PathCell();
        
        pCell1.setCenterPoint(new Point2D(50, 70));
        pCell2.setCenterPoint(new Point2D(120.5, 1.1));
        
        NavGraph ngraph = new NavGraph();
        
        ngraph.addEdge(pCell1, pCell2);
        
        assertEquals(1.0, ngraph.getEdgeWeight(pCell1, pCell2), 0.0);
        assertEquals(1.0, ngraph.getEdgeWeight(pCell2, pCell1), 0.0);
    }
    
    @Test
    public void testEdgesOf() {
        PathCell pCell1 = new PathCell();
        PathCell pCell2 = new PathCell();
        PathCell pCell3 = new PathCell();
        PathCell pCell4 = new PathCell();
        PathCell pCell5 = new PathCell();
        
        pCell1.setCenterPoint(new Point2D(50, 70));
        pCell2.setCenterPoint(new Point2D(120.5, 1.1));
        pCell3.setCenterPoint(new Point2D(0, -2));
        pCell4.setCenterPoint(new Point2D(-89, 12));
        pCell5.setCenterPoint(new Point2D(100, 100));
        
        NavGraph ngraph = new NavGraph();
        
        ngraph.addEdge(pCell1, pCell2);
        ngraph.addEdge(pCell1, pCell3);
        ngraph.addEdge(pCell1, pCell4);
        
        ngraph.addEdge(pCell2, pCell3);
        ngraph.addEdge(pCell3, pCell4);
        
        ngraph.addEdge(pCell4, pCell5);
        
        ArrayList<PathCell> neighborsToPCell1 = new ArrayList<PathCell>();
        neighborsToPCell1.add(pCell2);
        neighborsToPCell1.add(pCell3);
        neighborsToPCell1.add(pCell4);
        
        for (PathCell pCell : ngraph.edgesOf(pCell1)) {
            assertTrue(neighborsToPCell1.contains(pCell));
        }
        
        ArrayList<PathCell> neighborsToPCell2 = new ArrayList<PathCell>();
        neighborsToPCell1.add(pCell1);
        neighborsToPCell1.add(pCell3);
        for (PathCell pCell : ngraph.edgesOf(pCell2)) {
            assertTrue(neighborsToPCell1.contains(pCell));
        }
        
        for (PathCell pCell : ngraph.edgesOf(pCell5)) {
            assertTrue(pCell.equals(pCell4));
        }
    }
}
