package edu.moravian.projectile;

import edu.moravian.Ball;
import edu.moravian.creep.Creep;
import edu.moravian.graphics.DrawLocation;
import edu.moravian.graphics.GraphicsIDHolder;
import edu.moravian.graphics.Sprite;
import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class BasicBullet extends Projectile {

Point2D pos;     
double speedScale; 
int damage;
    
    public BasicBullet(Creep target_in, int damage_in, double speedscale_in, Point2D origin) {
        super(target_in, damage_in, speedscale_in);
        pos = origin;
        
    }

    @Override
    public Ball get_dims() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GraphicsIDHolder getGraphicsID() {
      return GraphicsIDHolder.BASICBULLET;
    }

    @Override
    public Point2D getPos() {
        return pos;
    }

    @Override
    public DrawLocation getDrawLocation() {
        return DrawLocation.CENTER;
    }

    @Override
    public Sprite getCurrFrame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
