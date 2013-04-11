/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.creep;

import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import edu.moravian.projectile.Projectile;
import java.util.LinkedList;
import java.awt.Shape;

/**
 *
 * @author moore
 */
public interface Creep {

    public Shape get_dims();

    public Point2D getPosition();

    public Vector2D getDirection();

    /**
     *
     * @param par0
     * @return
     */
}
