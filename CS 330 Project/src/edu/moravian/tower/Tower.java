/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.tower;

import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import java.awt.Shape;

/**
 *
 * @author moore
 */
public interface Tower {

    public Point2D getPosition();
    
    public Shape getRange();
    
    public void shoot();
}
