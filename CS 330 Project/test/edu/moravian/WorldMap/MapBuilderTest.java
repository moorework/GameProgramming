/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.WorldMap;

import edu.moravian.math.Point2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author moore
 */
public class MapBuilderTest {

    private final static String testDir = "testMaps/";

    public MapBuilderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void trivialTest() throws FileNotFoundException {


        String filedir = (testDir + "tileTypes.txt");

        ArrayList<ArrayList<WorldCell>> map = MapBuilder.getMapRepresentation(filedir);

        assertNotNull(map);

        assertTrue(map.get(0).get(0) instanceof PathCell);
        assertTrue(map.get(0).get(1) instanceof EndCell);
        assertTrue(map.get(0).get(2) instanceof StartCell);
        assertTrue(map.get(0).get(3) instanceof TowerCell);

        assertFalse(map.get(0).get(0) instanceof EndCell);
        assertFalse(map.get(0).get(0) instanceof StartCell);
        assertFalse(map.get(0).get(0) instanceof TowerCell);

        assertFalse(map.get(0).get(1) instanceof PathCell);
        assertFalse(map.get(0).get(1) instanceof StartCell);
        assertFalse(map.get(0).get(1) instanceof TowerCell);

        assertFalse(map.get(0).get(2) instanceof PathCell);
        assertFalse(map.get(0).get(2) instanceof EndCell);
        assertFalse(map.get(0).get(2) instanceof TowerCell);

        assertFalse(map.get(0).get(3) instanceof PathCell);
        assertFalse(map.get(0).get(3) instanceof EndCell);
        assertFalse(map.get(0).get(3) instanceof StartCell);

    }

    @Test
    public void multiLine() throws FileNotFoundException {


        String filedir = (testDir + "simpleMap");

        ArrayList<ArrayList<WorldCell>> map = MapBuilder.getMapRepresentation(filedir);

        assertNotNull(map);

        int len = map.get(0).size();
        for (int i = 1; i < map.size(); i++) {
            assertTrue(map.get(i).size() == len);
        }

        for (int i = 0; i < map.get(0).size(); i++) {
            assertTrue(map.get(0).get(i) instanceof TowerCell);
        }

        assertTrue(map.get(1).get(0) instanceof StartCell);
        for (int i = 1; i < map.get(0).size() - 2; i++) {
            assertTrue(map.get(1).get(i) instanceof PathCell);
        }
        assertTrue(map.get(1).get(map.get(1).size() - 1) instanceof EndCell);

        for (int i = 0; i < map.get(2).size(); i++) {
            assertTrue(map.get(0).get(i) instanceof TowerCell);
        }

    }
}
