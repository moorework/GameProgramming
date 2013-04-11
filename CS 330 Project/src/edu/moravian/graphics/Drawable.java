package edu.moravian.graphics;

import edu.moravian.math.Point2D;


/**
 * This interface defines the required elements for any subject that
 * expects to be drawn to the screen.
 * 
 * @author Myles
 */
public interface Drawable {
    
    /**
     * Retrieve the graphics id drawable object.
     * 
     * @return the graphics id of the object
     */
    public int getGraphicsID();
    
    /**
     * Retrieve a Point2D that describes the position of the drawable object.
     * This is where the object will be drawn, modified with respect to the
     * DrawLocation.
     * 
     * @return a Point2D that describe's the Drawable's position
     */
    public Point2D getPos();
    
    /**
     * Retrieve a DrawLocation describing how the Drawable's position should be used
     * to draw the image.
     * 
     * @return the DrawLocation describing where the Drawable expects to be drawn
     */
    public DrawLocation getDrawLocation();

    public Sprite getCurrFrame();
}
