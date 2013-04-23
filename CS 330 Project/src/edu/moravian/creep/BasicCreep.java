/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.creep;

import edu.moravian.Ball;
import edu.moravian.CollisionDetector;
import edu.moravian.CollisionEngine;
import edu.moravian.WorldMap.NavGraph;
import edu.moravian.WorldMap.PathCell;
import edu.moravian.WorldMap.WorldMap;
import edu.moravian.creep.pathfinding.Path;
import edu.moravian.creep.pathfinding.PathFinder;
import edu.moravian.graphics.DrawLocation;
import edu.moravian.graphics.Drawable;
import edu.moravian.graphics.GraphicsIDHolder;
import edu.moravian.graphics.Sprite;
import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import edu.moravian.projectile.Projectile;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author moore
 */
public class BasicCreep implements Creep, Drawable
{
    private final double WAYPOINT_TOL = 0.5;
    
    private Path myPath;
    private Point2D currWayPoint;
    
    private Point2D objective;
    private Point2D position;
    private Vector2D movement;
    private Shape appearance;
    private boolean dead;
    private int health;

    //TODO remove this basic test code 
    public BasicCreep(NavGraph navGraph, Point2D origin, Point2D objective_in, double speed)
    {
        myPath = PathFinder.generatePath(navGraph, origin, objective_in);
        currWayPoint = myPath.getNextWayPoint();
        
        health = 2;
        dead = false;
        position = origin;
        objective = objective_in;
        appearance = new Rectangle(100, 100);
        this.movement = currWayPoint.minus(position);
        this.movement.normalize();
        this.movement.timesEquals(speed);
    }

    //TODO where on the cell will a creep spawn
    /**
     * Creates a creep on a pathcell and gives it an objective. Must be a
     * traversable cell, otherwise we got issues
     *
     * @param path The path cell on which the creep will spawn
     * @param objective_in
     */
    public BasicCreep(PathCell path, Point2D objective_in)
    {
        health = 2;
        dead = false;
        this.objective = objective_in;

        //TODO get position from path
        position = new Point2D(100, 100);
    

        //TODO this needs to be fixed 
        appearance = new Rectangle(100, 100);
        // movement = new Vector2D(10, 10);
        this.movement = objective.minus(position);
    }


    @Override
    public void update(double delta)
    {
        if (position.minus(position).getX() < WAYPOINT_TOL && position.minus(position).getY() < WAYPOINT_TOL) {
            position.scalePlus(delta, movement);
        }
    }

    @Override
    public Shape get_dims()
    {
        return appearance.getBounds();
    }

    @Override
    public Point2D getPosition()
    {
        return position;
    }

    @Override
    public Vector2D getDirection()
    {
        return movement.getNormalized();
    }

    @Override
    public boolean respondToColission(Projectile projectile)
    {

        Ball one = new Ball(position, new Vector2D(0,0), 1, this.get_dims().getBounds().width / 2, Color.yellow, 1);
        Ball two = projectile.get_dims();

        
//        if (CollisionDetector.twoSpheresColliding(one, two))
        if(CollisionEngine.testForCollision(one, two, 1))
        {   
            System.out.println(health);
            health -= projectile.getDamage();
            
            dead = health < 0;
            return true;
        }
        return false;
    }

    @Override
    public boolean isDead()
    {
        return dead;
    }

    @Override
    public int healthRemaining()
    {
        return health;
    }

    @Override
    public GraphicsIDHolder getGraphicsID()
    {
        return GraphicsIDHolder.BASICCREEP;
    }

    @Override
    public Point2D getPos()
    {
        return this.getPosition();
    }

    @Override
    public DrawLocation getDrawLocation()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sprite getCurrFrame()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void draw(WorldGraphics2D w2d)
    {
     

        w2d.fillCircle(position, this.get_dims().getBounds().height, new Color(100*health,100,100));
    }
}
//TODO get rid of confliting interface
//TODO do we still want this frame stuff?
//TODO make sure that the corner stuff is implemneted 
