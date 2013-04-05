package edu.moravian;

import edu.moravian.math.Point2D;
import java.awt.Point;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author barros
 */
public class CoordinateTranslatorTest {
    
    public CoordinateTranslatorTest() {
    }

    
    @Test
    public void testTranslateWorldToScreen() {
        int screenWidth = 640;
        int screenHeight = 480;
        double worldWidth = 10;
        double worldHeight = 10;
        Point2D lowerLeft = new Point2D(0, 0);
        
        CoordinateTranslator ct = new CoordinateTranslator(screenWidth, screenHeight,
                                            worldWidth, worldHeight, lowerLeft);
        
        Point expScreenPoint;
        int expX = 0;
        int expY = screenHeight;
        Point2D input;
        Point ctOutput;
        
        int xIncrease = (int)(screenWidth / 8.0);
        int yDecrease = (int)(screenHeight / 8.0);
        for (double x = 0; x <= worldWidth; x += (worldWidth / 8.0)) {
            for (double y = 0; y <= worldHeight; y += (worldHeight / 8.0)) {
                input = new Point2D(x, y);
                ctOutput = ct.convertWorldToScreen(input);
                
                expScreenPoint = new Point(expX, expY);
                
                assertEquals(expScreenPoint, ctOutput);
                
                expY -= yDecrease;
            }
            expX += xIncrease;
            expY = screenHeight;
        }
    }
    
    @Test
    public void testTranslateWorldToScreenIrregular() {
        int screenWidth = 1280;
        int screenHeight = 1024;
        double worldWidth = 2.5;
        double worldHeight = 2;
        Point2D lowerLeft = new Point2D(-1.25, -1);
        
        CoordinateTranslator ct = new CoordinateTranslator(screenWidth, screenHeight,
                                            worldWidth, worldHeight, lowerLeft);
        
        Point expScreenPoint;
        int expX = 0;
        int expY = 1024;
        Point2D input;
        Point ctOutput;
        
        int xIncrease = (int)(screenWidth / 8.0);
        int yDecrease = (int) (screenHeight / 8.0);
        for (double x = -1.25; x <= 1.25; x += (worldWidth / 8.0)) {
            for (double y = -1; y <= 1; y += (worldHeight / 8.0)) {
                input = new Point2D(x, y);
                ctOutput = ct.convertWorldToScreen(input);
                
                expScreenPoint = new Point(expX, expY);
                
                assertEquals(expScreenPoint, ctOutput);
                
                expY -= yDecrease;
            }
            expX += xIncrease;
            expY = screenHeight;
        }
    }
    
    @Test
    public void testTranslateScreenToWorld() {
        int screenWidth = 640;
        int screenHeight = 480;
        double worldWidth = 10;
        double worldHeight = 10;
        Point2D lowerLeft = new Point2D(0, 0);
        
        CoordinateTranslator ct = new CoordinateTranslator(screenWidth, screenHeight,
                                            worldWidth, worldHeight, lowerLeft);
        
        Point input;
        Point2D ctOutput;
        
        double expX = 0;
        double expY = 0;
        Point2D expWorldPoint;
        
        double xIncrease = worldWidth / 8.0;
        double yIncrease = worldHeight / 8.0;
        for (int x = 0; x <= screenWidth; x += (screenWidth / 8.0)) {
            for (int y = screenHeight; y >= 0; y -= (screenHeight / 8.0)) {
                expWorldPoint = new Point2D(expX, expY);
                
                input = new Point(x, y);
                ctOutput = ct.convertScreenToWorld(input);
                
                assertEquals(expWorldPoint, ctOutput);
                
                expY += yIncrease;
            }
            
            expX += xIncrease;
            expY = 0;
        }
    }
    
    @Test
    public void testTranslateScreenToWorldIrregular() {
        int screenWidth = 1280;
        int screenHeight = 1024;
        double worldWidth = 2.5;
        double worldHeight = 2;
        Point2D lowerLeft = new Point2D(-1.25, -1);
        
        CoordinateTranslator ct = new CoordinateTranslator(screenWidth, screenHeight,
                                            worldWidth, worldHeight, lowerLeft);
        
        Point input;
        Point2D ctOutput;
        
        double expX = -1.25;
        double expY = -1;
        Point2D expWorldPoint;
        
        double xIncrease = worldWidth / 8.0;
        double yIncrease = worldHeight / 8.0;
        for (int x = 0; x <= screenWidth; x += (screenWidth / 8.0)) {
            for (int y = screenHeight; y >= 0; y -= (screenHeight / 8.0)) {
                expWorldPoint = new Point2D(expX, expY);
                
                input = new Point(x, y);
                ctOutput = ct.convertScreenToWorld(input);
                
                assertEquals(expWorldPoint, ctOutput);
                
                expY += yIncrease;
            }
            
            expX += xIncrease;
            expY = -1;
        }
    }
}
