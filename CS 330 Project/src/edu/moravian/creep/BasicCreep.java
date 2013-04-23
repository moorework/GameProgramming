
package edu.moravian.creep;

import edu.moravian.Ball;
import edu.moravian.CollisionEngine;
import edu.moravian.WorldMap.NavGraph;
import edu.moravian.creep.pathfinding.Path;
import edu.moravian.creep.pathfinding.PathFinder;
import edu.moravian.graphics.DrawLocation;
import edu.moravian.graphics.Drawable;
import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import edu.moravian.projectile.Projectile;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;

/**
 * The raddest creep.
 * 
 * @author moore
 */
public class BasicCreep implements Creep, Drawable {
    // defines the absolute difference that we are willing to accept between the
    // creep and his current objective before we deem that objective to have
    // been reached
    private static final double DEST_DIST_TOLERANCE = 0.5;
    
    // navigational data
    private NavGraph navGraph;
    private Path path;
    private Point2D currObjective;
    
    private Point2D objective;
    
    // locomotion
    private Point2D position;
    private Vector2D heading;
    private double speed;
    
    // visual data
    private Shape appearance;
    private int imageID;
    
    // details regarding health
    private boolean dead;
    private int health;

    
    //TODO remove this basic test code 
    public BasicCreep(NavGraph navigationalGraph, Point2D origin, Point2D objective_in,
                        double speed, int initHealth, int imageID) {
        navGraph = navigationalGraph;
        // generate our initial path as defined by the properties of the nav graph
        path = PathFinder.generatePath(navGraph, origin, objective_in);
        objective = objective_in;
        
        health = initHealth;
        dead = false;
        
        position = origin;
        appearance = new Rectangle(100, 100);
        this.imageID = imageID;
        
        // compute an initial heading
        currObjective = path.getNextWayPoint();
        this.heading = currObjective.minus(position); // compute velocity from me to my objective
        heading.normalize(); // normalizing gives us direction that we need to head,
                             // dissacociates our velocity from the distance to the target
    }

    @Override
    public void update(double delta) {
        if (health <= 0) { // if the agent has died since the last update...
            dead = true; // make a note of this
            
            return; // dead creeps don't have behavior (movement, etc.)
        }
        
        // move the agent by a distance that reflects their speed in the direction
        // of their heading, scaled by the delta to ensure consistent preformance
        // across all platforms
        position.scalePlus(delta * speed, heading);
        
        // update our objective if necessary given our recent movement
        updateObjective();
    }
    
    /*
     * Update the creep's current objective, if necessary.
     */
    private void updateObjective()
        {
        // utilize the distance function to determine how far we are from our
        // objective
        double diffX = currObjective.getX() - position.getX();
        double diffY = currObjective.getY() - position.getY();
        
        double diff = (diffX * diffX) + (diffY + diffY);
        
        // if our distance from the objective is insufficient for us to consider a
        // new objective...
        if (diff > DEST_DIST_TOLERANCE) {
            return; // don't modify our objective
        }
        
        // if we've reached this point then we have reached our objective and
        // are in need of another
        
        if (path.hasNextWayPoint()) { // if our path DOES have a new objective for us...
            // update our objective
            currObjective = path.getNextWayPoint();
        }
    }

    @Override
    public Shape get_dims() {
        return appearance.getBounds();
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public Vector2D getDirection() {
        return heading;
    }

    @Override
    public void respondToColision(Projectile projectile) {
        //TODO make sure ball is ok
        Ball one = new Ball(position, heading.times(speed), 1, this.get_dims().getBounds().width / 2, Color.yellow, 1);
        Ball two = projectile.get_dims();

        int damage;
        if (CollisionEngine.testForCollision(one, two, 1)) {
            damage = projectile.getDamage();
            health -= damage;
        }
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public int healthRemaining() {
        return health;
    }

    @Override
    public int getGraphicsID() {
        return imageID;
    }

    @Override
    public Point2D getPos() {
        return this.getPosition();
    }

    @Override
    public DrawLocation getDrawLocation() {
        return DrawLocation.CENTER;
    }

    public void draw(WorldGraphics2D w2d) {
        w2d.setColor(Color.red);

        w2d.fillCircle(position, this.get_dims().getBounds().height / 2, Color.YELLOW);
    }
}
//TODO get rid of confliting interface
//TODO do we still want this frame stuff?
//TODO make sure that the corner stuff is implemneted 
//TODO make this project good 