/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.projectile;

import edu.moravian.Ball;
import edu.moravian.creep.Creep;
import edu.moravian.graphics.Drawable;
import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import java.awt.Shape;

/**
 *
 * @author moore
 */
public abstract class Projectile  implements Drawable{

    private Creep target;
    private int damage;
    private double speedscale;
    private Point2D pos;
    private Vector2D dir;
    private boolean flying ;

    public Projectile(Creep target_in, int damage_in, double speedscale_in) {
        target = target_in;
        damage = damage_in;
        speedscale = speedscale_in;
        flying = true;
    }

    public abstract Ball get_dims();

    public Point2D getPosition() {
        return pos;
    }

    public Vector2D getVelocity() {
        return dir;
    }

    public int getDamage() {
        return damage;
    }

    public abstract void update();

    public boolean isDone()
    {
       return flying;
    }
}
