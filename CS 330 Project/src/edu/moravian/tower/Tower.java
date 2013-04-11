/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.tower;

import edu.moravian.creep.Creep;
import edu.moravian.creep.CreepManager;
import edu.moravian.graphics.Drawable;
import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import java.awt.Shape;

/**
 *
 * @author moore
 */
public abstract class Tower implements Drawable
{

    CreepManager manager;
    Point2D Position;

    public Tower(CreepManager manager, Point2D Position)
    {
        this.manager = manager;
        this.Position = Position;
    }

    public void update(double delta)
    {
        //TODO add delta support 
        for (Creep cr : manager.getCreeps())
        {
            
        }
    }
}
