/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.WorldMap;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Myles
 */
public class PathCellTest {
    
    
    @Test
    public void PathCellCreationTest() {
        PathCell pCell = new PathCell();
        
        assertTrue(pCell.canBeOccupied() == false);
        assertTrue(pCell.isPathable());
    }
}
