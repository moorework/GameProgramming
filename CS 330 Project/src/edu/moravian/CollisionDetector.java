package edu.moravian;

import edu.moravian.math.Vector2D;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class CollisionDetector
{

    /**
     *  Static method that determines if two balls are going to collide 
     * @param ballA  First ball
     * @param ballB Second ball
     * @return Are the balls colliding now or will they between frames?
     */
    public static boolean twoSpheresColliding(Ball ballA, Ball ballB)
    {
        //double distance = Math.sqrt(Math.pow(ballA.getCenter().getX() - ballB.getCenter().getX(), 2)    + Math.pow(ballA.getCenter().getY() - ballB.getCenter().getY(), 2));
        double distanceSQ = Math.pow(ballA.getCenter().getX() - ballB.getCenter().getX(), 2)
                + Math.pow(ballA.getCenter().getY() - ballB.getCenter().getY(), 2);
        //Get the distance between the two centers 

        //Sum the radii 
        double sumRadii = (ballB.getRadius() + ballA.getRadius());

        Vector2D movecAtoB = ballA.getVelocity();
        

        //If the movement vectors is smaller than the distance between the two bodies..
        if (movecAtoB.magnitude() < (distanceSQ - (sumRadii * sumRadii)))
        {
            //We ain't colliding
            return false;
        }

        //Get a vector from B to A 
        Vector2D C = ballB.getCenter().minus(ballA.getCenter());
     

        //Compare that vector to a vector from A to B 
        double DP_of_directions = movecAtoB.getNormalized().dot(C);
        if (DP_of_directions < 0)
        {
            //If this is negative, A is not moving toward B 
            return false;
        }


        //Get the distance between the two bodies
        Double distance_from_B = C.magnitudeSq() - (DP_of_directions * DP_of_directions);
        

        //If that is bigger
        if (distance_from_B >= (sumRadii * sumRadii))
        {
            //We ain't colliding 
            return false;
        }

        Double T = (sumRadii * sumRadii) - distance_from_B;

        //If we have gone past the second circle 
        if (T < 0)
        {
            return false;
        }

        Double travelDistance = DP_of_directions - Math.sqrt(T);

        //If we can't reach
        if (movecAtoB.magnitude() < travelDistance)
        {
            return false;
        }

        return true;

    }
}
