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
     * Retrieve the current frame of the drawable object.
     * 
     * @return the current frame of the object
     */
    public Sprite getCurrFrame();
    
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
}
