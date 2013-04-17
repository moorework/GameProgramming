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
 * @author myles
 */
public class NavPathTest {
    
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
}
