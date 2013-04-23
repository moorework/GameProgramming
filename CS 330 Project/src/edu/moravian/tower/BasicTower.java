package edu.moravian.tower;

import edu.moravian.creep.Creep;
import edu.moravian.creep.CreepManager;
import edu.moravian.graphics.DrawLocation;
import edu.moravian.graphics.GraphicsIDHolder;
import edu.moravian.graphics.Sprite;
import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import edu.moravian.projectile.BasicBullet;
import edu.moravian.projectile.BulletManager;
import edu.moravian.tower.targettingSystem.ProximityTargetingSystem;
import edu.moravian.tower.targettingSystem.TargetingSystem;
import edu.moravian.util.Timer;
import java.awt.Color;

/**
 * This tower shoots a basic homing projectile
 *
 * @author James Moore (moore.work@live.com)
 */
public class BasicTower extends Tower
  {
    private final int BULLET_IMAGE_ID = 1;
    
    private int imageID;
    
    private TargetingSystem tar;
    private Timer time;
    private BulletManager bulletman;
    private double shotFrequency;
    private int damage;
    private int speedScale;
    //TODO factor out magic number 
    private Point2D towerLoc;

    public BasicTower(CreepManager man, BulletManager bul, Point2D pos, int radius)
    {
        super(man, bul, pos, radius);

        //TODO magic number
        tar = new ProximityTargetingSystem(targetingRadius, pos);
        time = new Timer();
        shotFrequency = 2;
        speedScale = 1;
        damage = 1;
        bulletman = bul;
        time.tick();
        towerLoc = pos;
        manager = man;
    }

    @Override
    public void update(double delta)
    {

        //If we have not shot recently 
        //FIXME if there is an issue with a newly spawed tower
        if (time.getDelta() > shotFrequency)
        {

            if (tar.hasTarget(manager.getCreeps()))
            {

                //TODO magic number here 
                Creep target = tar.determineTarget(manager.getCreeps());
                BasicBullet bul = new BasicBullet(target, damage, 1, this.getPos(), BULLET_IMAGE_ID);
                bulletman.shoot(bul);
            }

            //Reset the time 
            time.tick();

          }
        
        bulletman.update(delta);
      }

    @Override
    public int getGraphicsID()
      {
        return imageID;
      }

    @Override
    public Point2D getPos()
    {
        return towerLoc;
    }

    @Override
    public DrawLocation getDrawLocation()
    {
        return DrawLocation.CENTER;
    }

    //TODO get rid of me, I am simply a testing method 
    public void draw(WorldGraphics2D w2d)
    {
        Color col = w2d.getColor();
        w2d.setColor(Color.red);
        Point2D tenPos = new Point2D(Position.getX() - 5, Position.getY() + 5);
        w2d.fillCircle(tenPos, 10, Color.CYAN);
        //w2d.fillCircle(Position, 10, Color.cyan);


        Point2D fooPos = new Point2D(Position.getX() - targetingRadius / 4, Position.getY() + targetingRadius / 4);
        w2d.drawCircle(fooPos, targetingRadius, Color.CYAN);
        w2d.fillCircle(Position, 1, Color.white);
        w2d.setColor(col);

    }
}
