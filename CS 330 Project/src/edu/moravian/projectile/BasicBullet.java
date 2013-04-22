package edu.moravian.projectile;

import edu.moravian.Ball;
import edu.moravian.creep.Creep;
import edu.moravian.graphics.DrawLocation;
import edu.moravian.graphics.Drawable;
import edu.moravian.graphics.GraphicsIDHolder;
import edu.moravian.graphics.Sprite;
import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import java.awt.Color;
import javax.swing.text.Position;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class BasicBullet extends Projectile implements Drawable
{

    private int Radius;

    public BasicBullet(Creep target_in, int damage_in, double speedscale_in, Point2D origin, int bulletRadius)
    {
        super(origin, target_in, damage_in, speedscale_in);
         Radius = bulletRadius;
    }

    @Override
    public Ball get_dims()
    {
        return new Ball(pos, getVelocity(), 1, Radius, Color.yellow, 1);
    }

    @Override
    public void update(double delta)
    {


        if (target.respondToColission(this))
        {
            this.doneVar = true;
        }
        else
        {

            Vector2D direction = target.getPosition().minus(this.pos).getNormalized();
            pos = pos.scalePlus(this.speedscale, direction);
        }
    }

    @Override
    public GraphicsIDHolder getGraphicsID()
    {
        return GraphicsIDHolder.BASICBULLET;
    }

    @Override
    public Point2D getPos()
    {
        return this.getPosition();
    }

    @Override
    public DrawLocation getDrawLocation()
    {
        return DrawLocation.CENTER;
    }

    @Override
    public Sprite getCurrFrame()
    {
        //TODO implement me
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
