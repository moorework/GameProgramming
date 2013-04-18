/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.tower;

import edu.moravian.projectile.BulletManager;
import edu.moravian.creep.CreepManager;
import edu.moravian.graphics.Drawable;
import edu.moravian.math.Point2D;

/**
 *
 * @author moore
 */
public abstract class Tower implements Drawable
{

    CreepManager manager;
    Point2D Position;

    public Tower(CreepManager manager, BulletManager bul, Point2D Position)
    {
        this.manager = manager;
        this.Position = Position;
    }

    public abstract void update(double delta);
}
