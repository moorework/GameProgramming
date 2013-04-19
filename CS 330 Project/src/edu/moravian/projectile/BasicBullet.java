package edu.moravian.projectile;

import edu.moravian.Ball;
import edu.moravian.creep.Creep;
import edu.moravian.graphics.DrawLocation;
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
public class BasicBullet extends Projectile
  {
    //TODO magic number

    private static final int Radius = 100;

    public BasicBullet(Creep target_in, int damage_in, double speedscale_in, Point2D origin)
      {
        super(origin, target_in, damage_in, speedscale_in);
      }

    @Override
    public Ball get_dims()
      {

        return new Ball(pos, getVelocity(), 1, Radius, Color.yellow, 1);
      }

    @Override
    public void update(double delta)
      {
        target.respondToColission(this);
        //TODO MN
        Vector2D direction = target.getPosition().minus(this.pos).getNormalized();
        pos = pos.scalePlus(this.speedscale, direction);
        if (target.isDead())
          {
            this.doneVar = true;
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
