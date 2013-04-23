package edu.moravian.projectile;

import edu.moravian.Ball;
import edu.moravian.creep.Creep;
import edu.moravian.graphics.Drawable;
import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import java.awt.Color;

/**
 *
 * @author moore
 */
public abstract class Projectile  implements Drawable {

    protected Creep target;
    private int damage;
    protected double speedscale;
    protected Point2D pos;
    private Vector2D dir;
    protected boolean doneVar ;
    protected int imageID;

    public Projectile(Point2D position, Creep target_in, int damage_in, double speedscale_in, int imageID) {
        target = target_in;
        damage = damage_in;
        speedscale = speedscale_in;
        doneVar = false;
        pos = position;
        this.imageID = imageID;
    }

    public abstract Ball get_dims();

    public Point2D getPosition() {
        return pos;
    }

    public Vector2D getVelocity() {
        return this.pos.minus(target.getPosition());
    }

    public int getDamage() {
        return damage;
    }

    public abstract void update(double delta);

    public boolean isDone()
    {
       return doneVar;
    }

    void draw(WorldGraphics2D w2d) {
        //TODO circle 
        w2d.drawCircle(pos, 100, Color.red);
    }
}

//TODO make sure that there arn't duplicate get velo methods