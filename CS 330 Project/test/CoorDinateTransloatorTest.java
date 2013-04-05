/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import edu.moravian.math.Point2D;
import edu.moravian.util.CoordinateTransloator;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class CoorDinateTransloatorTest
{

    private static final int ScreenHeight = 1024;
    public static final int ScreenWidth = 1280;
    public static final int worldWidth = 150;
    public static final int worldHeight = 200;
    public static final int numtest = 100;
    CoordinateTransloator trans;
    LinkedList<Point2D> worlds;
    LinkedList<Point2D> screens;

    public CoorDinateTransloatorTest()
    {
        trans = new CoordinateTransloator(ScreenWidth, ScreenHeight, worldWidth, worldHeight);

        worlds = new LinkedList<Point2D>();
        screens = new LinkedList<Point2D>();





    }

    @Test
    public void testScreenToWorld()
    {
        assertEquals(Point2D.zero, trans.screenToWorld(new Point2D(0, ScreenHeight)));
        assertEquals(new Point2D(0, worldHeight), trans.screenToWorld(new Point2D(0, 0)));
        assertEquals(new Point2D(worldWidth, worldHeight), trans.screenToWorld(new Point2D(ScreenWidth, 0)));
        assertEquals(new Point2D(worldWidth, 0), trans.screenToWorld(new Point2D(ScreenWidth, ScreenHeight)));
    }

    @Test
    public void testWorldtoScreen()
    {
        assertEquals(Point2D.zero, trans.worldtoScreen(new Point2D(0, worldHeight)));
        assertEquals(new Point2D(0, ScreenHeight), trans.worldtoScreen(new Point2D(0, 0)));
        assertEquals(new Point2D(ScreenWidth, 0), trans.worldtoScreen(new Point2D(worldWidth, worldHeight)));
        assertEquals(new Point2D(ScreenWidth, ScreenHeight), trans.worldtoScreen(new Point2D(worldWidth, 0)));
    }

    @Test
    public void spotTests()
    {
        for(double i = 1; i < numtest; i++){

        assertEquals(new Point2D(ScreenWidth/i, ScreenHeight/i), trans.worldtoScreen(new Point2D(worldWidth/i, worldHeight - worldHeight/i)));

        assertEquals(new Point2D(worldWidth/i, worldHeight - worldHeight/i), trans.screenToWorld(new Point2D(ScreenWidth/i, ScreenHeight/i)));
        }
        
    }
}