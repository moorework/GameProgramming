/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.entities;

import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import java.awt.Shape;

/**
 *
 * @author moore
 */
public interface Projectile {

    public Shape get_dims();

    public Point2D getPosition();

    public Vector2D getDirection();

    public boolean touching(Shape shp);
}
