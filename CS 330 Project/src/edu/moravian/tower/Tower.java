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
 * This class represents a tower.  It's targeting is delegated to a targeting system
 * @author moore
 */
public abstract class Tower implements Drawable
{

    protected CreepManager manager;
   protected Point2D Position;
 protected   int targetingRadius;
    public Tower(CreepManager manager, BulletManager bul, Point2D Position, int targetRadius)
    {
        this.manager = manager;
        this.Position = Position;
        targetingRadius = targetRadius;
    }

    public abstract void update(double delta);
}
