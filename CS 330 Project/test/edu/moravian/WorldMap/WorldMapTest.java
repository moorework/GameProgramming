/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.WorldMap;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author myles
 */
public class WorldMapTest {
    
@Test
public void testNavGraphGeneration() {
    WorldMap worldMap = new WorldMap("testMaps/simpleMap", 1000, 1000);
    
    NavGraph navGraph = worldMap.getNavPath();
   assertEquals(navGraph.vertexSet().size(), 5);
}
}
