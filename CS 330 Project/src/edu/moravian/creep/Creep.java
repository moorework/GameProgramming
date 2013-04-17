package edu.moravian.creep;

import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import edu.moravian.projectile.Projectile;
import java.awt.Shape;

/**
 *
 * @author moore
 */
public interface Creep
{
        
    
    public void update(double delta);

    public Shape get_dims();

    public Point2D getPosition();

    public Vector2D getDirection();

    /**
     *
     * @param par0
     * @return
     */
    public void respondToColission(Projectile projectile);

    public boolean isDead();

    public int healthRemaining();
}
