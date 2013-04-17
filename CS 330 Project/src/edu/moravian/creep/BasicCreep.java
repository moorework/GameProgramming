/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.creep;

import edu.moravian.Ball;
import edu.moravian.CollisionDetector;
import edu.moravian.WorldMap.PathCell;
import edu.moravian.WorldMap.WorldMap;
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
public class BasicCreep implements Creep, Drawable {

    private PathCell currLoc;
    private Point2D objective;
    private Point2D position;
    private Vector2D movement;
    private Shape appearance;
    private boolean dead;

    public BasicCreep(PathCell path, Point2D objective_in) {
        dead = false;
        currLoc = path;
        this.objective = objective_in;
        position = new Point2D(200, 200);

        //TODO this needs to be fixed 
        this.objective = new Point2D(1000, 1000);
        appearance = new Rectangle(100, 100);
        // movement = new Vector2D(10, 10);



    }

    @Override
    public void update(double delta) {

        this.position = position.scalePlus(1, objective.minus(position).getNormalized());

    }

    @Override
    public Shape get_dims() {
        return appearance.getBounds();
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public Vector2D getDirection() {
        return movement.getNormalized();
    }

    @Override
    public void respondToColission(Projectile projectile) {
        Ball one = new Ball(position, movement, 1, this.get_dims().getBounds().width / 2, Color.yellow, 1);


        if (CollisionDetector.twoSpheresColliding(one, projectile.get_dims())) {
            System.out.println("HIT");

        }


    }

    @Override
    public boolean isDead() {
        if (dead) {
            System.out.println("DEATH");
        }
        return dead;
    }

    @Override
    public int healthRemaining() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GraphicsIDHolder getGraphicsID() {
        return GraphicsIDHolder.BASICCREEP;
    }

    @Override
    public Point2D getPos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DrawLocation getDrawLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sprite getCurrFrame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void draw(WorldGraphics2D w2d) {
        w2d.setColor(Color.red);

        w2d.fillCircle(position, this.get_dims().getBounds().height / 2, Color.YELLOW);
    }
}
//TODO get rid of confliting interface
//TODO do we still want this frame stuff?
//TODO make sure that the corner stuff is implemneted 
//TODO make this project good 