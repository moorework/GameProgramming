package edu.moravian.projectile;

import edu.moravian.graphics.WorldGraphics2D;
import java.util.LinkedList;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class BulletManager
  {

    private LinkedList<Projectile> bullets;

    public BulletManager()
      {
        bullets = new LinkedList<Projectile>();
      }

    public BulletManager(LinkedList<Projectile> bullets_in)
      {
        if (bullets != null)
          {
            this.bullets = bullets_in;
          }
        else
          {
            bullets = new LinkedList<Projectile>();
          }
      }

    public void update(double delta)
      {
        LinkedList<Projectile> deadBullets = new LinkedList<Projectile>();
        for (Projectile proj : bullets)
          {
            proj.update(delta);
            
            //This kind of projectile dies on impact 
            if (proj.isDone())
              {
                deadBullets.add(proj);
              }
          }

        bullets.removeAll(deadBullets);
      }

    public void shoot(BasicBullet basicBullet)
      {
        bullets.add(basicBullet);
      }

    //TODO targetting system test
    //TODO make sure the bullets seek properly
    //TODO test the data driven crap 
    public void draw(WorldGraphics2D w2d)
      {
        for (Projectile proj : bullets)
          {
            proj.draw(w2d);
          }
      }
  }
