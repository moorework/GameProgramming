package edu.moravian;

import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import java.awt.Color;

/**
 * This class encapsulates a ball. It stores it's own velocity and
 *
 * @author James Moore (moore.work@live.com)
 */
public class Ball
{

    private Point2D location;
    private Vector2D velocity;
    private double mass;
    private double radius;
    private Color color;
    public static final int PERFECTLY_ELASTIC = 1;
    public static final int PERFECTLY_INELASTIC = 0;
    private double elasticity;

    /**
     * This constructs a ball with the given parameters, WITH a specified
     * elasticity. If one does not specify it here, it will be retrieved from
     * settings.
     *
     * @param location The location (preferably within the world) for the upper
     * left corner of the ball
     * @param velocity The initial velocity of the ball
     * @param mass The mass of the ball
     * @param radius the radius in world units of the ball
     * @param color the color of the ball. the ball will be solid
     * @param elasticity_in the elasticity of the ball. 0 is perfectly elastic,
     * 1 is perfectly inelastic. Values outside this are allowed but not
     * recommended.
     */
    public Ball(Point2D location, Vector2D velocity, double mass, double radius, Color color, double elasticity_in)
    {
        this.location = location;
        this.velocity = velocity;
        this.mass = mass;
        this.radius = radius;
        this.color = color;
        elasticity = elasticity_in;

    }


    /**
     * The balls handle their own drawing for now. Colors are safe
     *
     * @param g2d
     */
    public void draw(WorldGraphics2D g2d)
    {
        Color old = g2d.getColor();
        g2d.setColor(color);

        g2d.fillOval(location, (int) radius * 2, (int) radius * 2);
        g2d.setColor(old);
    }

    //TODO make sure the elasticity is in the file 
    /**
     * Updates the position of the ball within the world
     */
    public void update()
    {
        this.location.set(this.location.scalePlus(1, velocity));

    }

    public Vector2D getVelocity()
    {
        return velocity;
    }

    public void setVelocity(Vector2D velocity)
    {
        this.velocity = velocity;
    }

    public double getMass()
    {
        return mass;
    }

    public double getRadius()
    {
        return radius;
    }

    public Color getColor()
    {
        return color;
    }

    public void setLocation(Point2D location)
    {
        this.location = location;
    }

    public Point2D getLocation()
    {
        return location;
    }

    public Point2D getCenter()
    {
        return new Point2D(location.getX() + (radius), location.getY() + (radius));
    }

    /**
     * Responds to a collision between this ball and the input ball; BOTH ARE
     * MODIFIED.
     *
     * @param other
     * @return
     */
    public void respondColission(Ball other)
    {

        if (CollisionDetector.twoSpheresColliding(this, other))
        {
            Vector2D collissionNormal = this.getCenter().minus(other.getCenter());

            //The distance between the two centers
            double distance = collissionNormal.magnitude();

            //Once we have the distance, we want to normalize the vector so it is true to its name
            collissionNormal.normalize();
            double radiusSum = this.getRadius() + other.getRadius();

            /*
             * On a colission event the bodies will not be exactly touching
             * this is the distance between an exact colission of the two 
             * bodies
             */
            double penetration = radiusSum - distance;

            //"Push" the balls out so they touch 
            this.setLocation(this.getLocation().plus(collissionNormal.times(penetration * .5)));
            other.setLocation(other.getLocation().plus(collissionNormal.times(penetration * -.5)));

            //Get the relative velocity 
            Vector2D new_Velocity = this.getVelocity().minus(other.getVelocity());


            double vDotN = new_Velocity.dot(collissionNormal);
            //Determine the direction of the new  velocity and the old velocity 

            //Compute the impulse factor
            double num = vDotN * -(1 + elasticity);

            double denominator = (collissionNormal.dot(collissionNormal));

            denominator *= (1 / this.getMass() + 1 / other.getMass());

            double impulse_factor = num / denominator;

            //Modify the velocities 
            this.setVelocity(this.getVelocity().plus(collissionNormal.times(impulse_factor / this.getMass())));
            other.setVelocity(other.getVelocity().minus(collissionNormal.times(impulse_factor / other.getMass())));

        }
    }
}
