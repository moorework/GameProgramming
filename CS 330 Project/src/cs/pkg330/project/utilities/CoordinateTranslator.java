package cs.pkg330.project.utilities;

import cs.pkg330.project.math.Point2D;
import java.awt.Point;

/**
 *
 * @author barros
 */
public class CoordinateTranslator {
    private final double WORLD_WIDTH;
    private final double WORLD_HEIGHT;
    
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    
    private final Point2D WORLD_LOWER_LEFT;
    
    private final double worldToScreenWidth;
    private final double worldToScreenHeight;
    private final double screenToWorldWidth;
    private final double screenToWorldHeight;
    
    private final double screenToWorldDeltaMag;
    private final double worldToScreenDeltaMag;
    
    /**
     * Create a CoordinateTranslator that will translate bi-directionally
     * between the screen and world defined in the constructor. It is assumed
     * 
     * 
     * @param screenWidth
     * @param screenHeight
     * @param worldWidth
     * @param worldHeight 
     */
    public CoordinateTranslator(int screenWidth, int screenHeight,
                                double worldWidth, double worldHeight,
                                Point2D worldLowerLeft) {
        this.SCREEN_WIDTH = screenWidth;
        this.SCREEN_HEIGHT = screenHeight;
        
        this.WORLD_WIDTH = worldWidth;
        this.WORLD_HEIGHT = worldHeight;
        
        worldToScreenWidth = ((double) screenWidth) / worldWidth;
        worldToScreenHeight = ((double) screenHeight) / worldHeight;
        
        screenToWorldWidth = ((double) worldWidth) / screenWidth;
        screenToWorldHeight = ((double) worldHeight) / screenHeight;
        
        this.WORLD_LOWER_LEFT = worldLowerLeft;
        
        screenToWorldDeltaMag = worldWidth / screenWidth;
        worldToScreenDeltaMag = screenWidth / worldWidth;
    }
    
    /**
     * Converts a Cartesian point representing a discreet point on a
     * screen to a continuous point representing that same point in the world.
     * 
     * @param screenPoint
     * @return 
     */
    public Point2D convertScreenToWorld(Point screenPoint) {
        double wX = WORLD_LOWER_LEFT.getX() + screenToWorldWidth * screenPoint.x;
        double wY = WORLD_LOWER_LEFT.getY() + WORLD_HEIGHT -
                                          (screenToWorldHeight * screenPoint.y);
        
        return new Point2D(wX, wY);
    }
    
    public Point convertWorldToScreen(Point2D worldPoint) {
        int sX = (int)(worldToScreenWidth * (worldPoint.getX()
                                                    - WORLD_LOWER_LEFT.getX()));
        int sY = (int)(SCREEN_HEIGHT - (worldToScreenHeight * (worldPoint.getY()
                                                    - WORLD_LOWER_LEFT.getY())));
        
        return new Point(sX, sY);
    }
    
    public double convertScreenLengthToWorldLength(double len) {
        return screenToWorldDeltaMag * len;
    }
    
    public double convertWorldLengthToScreenLength(double len) {
        return worldToScreenDeltaMag * len;
    }
    
    public double getWorldWidth() {
        return WORLD_WIDTH;
    }
    
    public double getWorldHeight() {
        return WORLD_HEIGHT;
    }
}
