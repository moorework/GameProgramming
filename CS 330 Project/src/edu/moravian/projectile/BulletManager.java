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

    LinkedList<Projectile> bullets;

    public BulletManager(LinkedList<Projectile> bullets)
    {
        this.bullets = bullets;
    }

    public void update()
    {
        LinkedList<Projectile> deadBullets = new LinkedList<Projectile>();
        for (Projectile proj : bullets)
        {
            proj.update();
            if(proj.isDone()){
                //TODO some code or whatever here 
            }
        }
        
        bullets.removeAll(bullets);
    }

    public void shoot(BasicBullet basicBullet)
    {
        bullets.add(basicBullet);
    }
}
