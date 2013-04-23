package edu.moravian.projectile;

import edu.moravian.Ball;
import edu.moravian.creep.Creep;
import edu.moravian.graphics.DrawLocation;
import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import java.awt.Color;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class BasicBullet extends Projectile
  {
    private static final int Radius = 100;

    public BasicBullet(Creep target_in, int damage_in, double speedscale_in, Point2D origin, int imageID)
      {
        super(origin, target_in, damage_in, speedscale_in, imageID);
      }

    @Override
    public Ball get_dims()
      {

        return new Ball(pos, getVelocity(), 1, Radius, Color.yellow, 1);
      }

    @Override
    public void update(double delta)
      {
        target.respondToColision(this);
        //TODO MN
        Vector2D direction = target.getPosition().minus(this.pos).getNormalized();
        
        pos = pos.scalePlus(this.speedscale * delta, direction);
        
        if (target.isDead())
          {
            this.doneVar = true;
          }
      }

    @Override
    public int getGraphicsID()
      {
        return imageID;
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
  }
