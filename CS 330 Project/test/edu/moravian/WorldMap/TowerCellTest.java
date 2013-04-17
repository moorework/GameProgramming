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
public class TowerCellTest {
    
 @Test
 public void testTowerCreation() {
     TowerCell tCell = new TowerCell();
     
     assertTrue(tCell.canBeOccupied());
     assertTrue(tCell.isPathable() == false);
     assertTrue(tCell.isOccupied() == false);
 }
 
 @Test
 public void testTowerOccupation() {
     TowerCell tCell = new TowerCell();
     
     assertTrue(tCell.canBeOccupied());
     
     assertTrue(tCell.isOccupied() == false);
     tCell.setOccupied();
     assertTrue(tCell.isOccupied() == true);
     tCell.setUnOccupied();
     assertTrue(tCell.isOccupied() == false);
 }
}
