package edu.moravian.projectile;

import edu.moravian.projectile.BasicBullet;
import edu.moravian.projectile.Projectile;
import java.util.LinkedList;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class BulletManager
{

    private LinkedList<Projectile> bullets;

    public BulletManager(LinkedList<Projectile> bullets)
    {
        this.bullets = bullets;
    }

    public void update(double delta)
    {
        LinkedList<Projectile> deadBullets = new LinkedList<Projectile>();
        for (Projectile proj : bullets)
        {
            proj.update(delta);
            if(proj.isDone()){
                deadBullets.add(proj);
            }
        }
        
        bullets.removeAll(bullets);
    }

    public void shoot(BasicBullet basicBullet)
    {
        bullets.add(basicBullet);
    }
    
    //TODO targetting system test
    //TODO make sure the bullets seek properly
    //TODO test the data driven crap 
}
