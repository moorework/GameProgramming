package edu.moravian.projectile;

import edu.moravian.Ball;
import edu.moravian.creep.Creep;
import edu.moravian.graphics.DrawLocation;
import edu.moravian.graphics.Sprite;
import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class BasicBullet extends Projectile {

    public BasicBullet(Creep target_in, int damage_in, double speedscale_in) {
        super(target_in, damage_in, speedscale_in);
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
    public int getGraphicsID() {
        throw new UnsupportedOperationException("Not supported yet.");
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
}
