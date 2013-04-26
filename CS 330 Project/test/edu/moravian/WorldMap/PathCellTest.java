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
public class PathCellTest {
    
    
    @Test
    public void PathCellCreationTest() {
        PathCell pCell = new PathCell(false, false);
        
        assertTrue(pCell.canBeOccupied() == false);
        assertTrue(pCell.isPathable());
    }
    
    @Test
    public void testCenterPoint() {
        PathCell pCell = new PathCell(false, false);
        
        assertEquals(null, pCell.getCenterPoint());
        
        Point2D centerPoint = new Point2D(20.5, 300.62);
        pCell.setCenterPoint(centerPoint);
        
        assertEquals(centerPoint, pCell.getCenterPoint());
        
        pCell.setCenterPoint(Point2D.zero);
        
        assertEquals(Point2D.zero, pCell.getCenterPoint());
    }
}
