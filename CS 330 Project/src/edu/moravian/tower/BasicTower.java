package edu.moravian.tower;

import edu.moravian.projectile.BulletManager;
import edu.moravian.creep.Creep;
import edu.moravian.creep.CreepManager;
import edu.moravian.graphics.DrawLocation;
import edu.moravian.graphics.GraphicsIDHolder;
import edu.moravian.graphics.Sprite;
import edu.moravian.math.Point2D;
import edu.moravian.projectile.BasicBullet;
import edu.moravian.tower.targettingSystem.ProximityTargetingSystem;
import edu.moravian.tower.targettingSystem.TargetingSystem;
import edu.moravian.util.Timer;
import java.util.LinkedList;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class BasicTower extends Tower
{
    TargetingSystem tar;
Timer time;
BulletManager bulletman;
double shotFrequency;
double speedScale;
int damage;

    public BasicTower(CreepManager man, BulletManager bul, Point2D pos)
    {
        super(man,bul, pos);
        
        //TODO magic number
        tar = new ProximityTargetingSystem(100, pos);
        time = new Timer();
        shotFrequency = 4;
        speedScale = 1;
        damage = 1;
        bulletman = bul;
    }

    @Override
    public void update(double delta)
    {
        
        if(time.getDelta() > shotFrequency){
         bulletman.shoot (new BasicBullet(tar.determineTarget(manager.getCreeps()), damage, speedScale, Position));
         //TODO add interaction with bulletManager here
          
        }
       
        
    }

    @Override
    public GraphicsIDHolder getGraphicsID()
    {
        return GraphicsIDHolder.BASICTOWER;
    }

    @Override
    public Point2D getPos()
    {
        return Position;
    }

    @Override
    public DrawLocation getDrawLocation()
    {
        return DrawLocation.CENTER;
    }

    @Override
    public Sprite getCurrFrame()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
