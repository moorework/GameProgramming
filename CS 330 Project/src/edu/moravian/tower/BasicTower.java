package edu.moravian.tower;

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
 *
 * @author James Moore (moore.work@live.com)
 */
public class BasicTower extends Tower {

    private TargetingSystem tar;
    private Timer time;
    private BulletManager bulletman;
    private double shotFrequency;
    private double speedScale;
    private int damage;
    private static final int targetingRadius = 100;
    private Point2D towerLoc;

    public BasicTower(CreepManager man, BulletManager bul, Point2D pos) {
        super(man, bul, pos);

        //TODO magic number
        tar = new ProximityTargetingSystem(targetingRadius, pos);
        time = new Timer();
        shotFrequency = 1;
        speedScale = 1;
        damage = 1;
        bulletman = bul;
        time.tick();
        towerLoc = pos;
    }

    @Override
    public void update(double delta) {

        if (time.getDelta() > shotFrequency) {
            System.out.println("Shot out");
            if (tar.hasTarget(manager.getCreeps())) {
                bulletman.shoot(new BasicBullet(tar.determineTarget(manager.getCreeps()), damage, 1, Position));
            }
            //TODO add interaction with bulletManager here
            time.tick();
        }
    }

    @Override
    public GraphicsIDHolder getGraphicsID() {
        return GraphicsIDHolder.BASICTOWER;
    }

    @Override
    public Point2D getPos() {
        return towerLoc;
    }

    @Override
    public DrawLocation getDrawLocation() {
        return DrawLocation.CENTER;
    }

    @Override
    public Sprite getCurrFrame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //TODO get rid of me, I am simply a testing method 
    public void draw(WorldGraphics2D w2d) {


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
