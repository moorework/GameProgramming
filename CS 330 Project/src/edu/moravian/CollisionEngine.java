package edu.moravian;

import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;

/**
 *
 * @author Myles
 */
public class CollisionEngine {

    public static boolean testForCollision(Ball a, Ball b, double delta)
    {
        /**
         * Collision detection is costly while the vast majority of potential
         * collisions aren't within the realm of feasibility. As such we perform
         * a series of four early escape tests in order to eliminate obvious
         * poseurs and preserve our final computations for the very small number
         * of collisions that may in fact be legitimate.
         *
         * We are insurance agents in a world of scam artists.
         */
        /*
         * INITIAL preperation: It is often the case that, when two bodies
         * collide, they are both in motion. Collision detection is far simpler
         * when one of the bodies is stationary, however. As such if both balls
         * *are* moving then we will translate a's movement such that Ball 'b'
         * can be considered stationary and perform the collision detection with
         * this relative movement.
         */
        // the amount by which balls A and B will move during the present frame
        Vector2D movementVectorA = a.getVelocity();
        Vector2D movementVectorB = b.getVelocity();

        // a vector that describes the movement of Ball 'a'
        Vector2D moveVector;
        // the movement vector now describes the relevent movement of the two
        moveVector = movementVectorA.minus(movementVectorB);
        moveVector.timesEquals(delta);

        /*
         * FIRST early escape test: If the length of the movement vector that
         * defines the change in Ball 'a' is less than the distance between the
         * outer edges of these two circles (distance between center points minus
         * sum of the radii) then a collision is not possible.
         */
        Point2D centerA = a.getCenter(); // a's center point
        Point2D centerB = b.getCenter(); // b's center point

        // a vector that describes the distance between 'a' and b's center points;
        // the magnitude of this vector is the distance between their center points
        Vector2D distVector = centerB.minus(centerA);

        // the distance between Ball 'a' and Ball 'b'. Unlike the distVector,
        // this distance will take the radii of the Balls into account.
        double distance = distVector.magnitude();

        // 'a' and 'b' are not mere points but rather occupy a physical space;
        // as such we must exclude their radii from the distance
        double sumRadii = a.getRadius() + b.getRadius();
        double trueDistance = distance - sumRadii; // true distance between 'a' and b

        // the squred magnitude of the distance vector; we'll need this later
        double moveVectorMagSq = moveVector.magnitudeSq();

        if (trueDistance < 0) {
            return true;
        }
        else if (moveVectorMagSq < (trueDistance * trueDistance)) {
            // then they're incapable of colliding this frame
            return false;
        }

        /*
         * SECOND early escape test: We now know that the two Balls are within
         * striking distance of each other. But that doesn't necessarily entail
         * an imminent collision. Ball 'a' may not be travelling anywhere near
         * Ball b! In which case, a collision is infeasible.
         * 
         * We'll determine whether or not 'a' is travelling towards 'b' by
         * exploiting the fact that the dot product of two normal vectors is the
         * cosine of the angle between them. Cosines are negative if the angle
         * provided to them is greater than ±90 degrees. In other words, the
         * cosine is negative when Ball 'a' is not travelling towards 'b'.
         */

        // we can preserve operations by recognizing that the magnitude of a
        // vector, even wholly negative vectors, are positive. As such they will
        // have no bearing on the sign of the result and thus will not obscure
        // the relevent data provided by the cos of the angle.

        // we will compute the dot product of the normalized moveVector and
        // the distVector, however, as we will need this value later; it will
        // save a dot product calculation. Note that it's variable name is
        // more relevent to its later usage, as d represents the distance
        // between the center of 'a' and the closest point on the moveVector
        // to Ball 'b'
        Vector2D normMoveVector = moveVector.getNormalized();
        double d = normMoveVector.dot(distVector);

        // if the dot product is less than 0, revealing that the angle between
        // them is greater than 90 degrees...
        if (d <= 0) {
            // a collision is impossible
            return false;
        }

        /*
         * THIRD early escape test: The "flyby" test. Given that Ball 'a' is
         * almost certainly not bearing down directly on 'b', it is possible that
         * the closest that 'a' will get to 'b' is still more than the sum of
         * their radii. In which case, there will be no collision.
         */

        // compute the distance from b's center point to the closest point on
        // the the movement vector (distance function w/o square root to
        // preserve computations)
        double distToPointClosestToBSq = (distance * distance) - (d * d);

        // the square of the sum of the radii; they're squared to accomodate
        // comparisons to other values that are themselves squared
        double sumRadiiSq = sumRadii * sumRadii;

        // if the closest that the movement vector comes to b's center point is
        // greater than the sum of the radii...
        if (distToPointClosestToBSq >= sumRadiiSq) {
            // then the two will not collide
            return false;
        }

        /*
         * FINAL test: This test will perform the actual collision evaluation.
         * If we've reached this point we already know that the closest point on
         * a's movement vector to 'b' is less than the sum of their radii. Given
         * that we also know that 'a' is travelling towards 'b', we now merely
         * need to find the point at which 'a' will be colliding with 'b'.
         * This can be found by treating distToPointClosestToB and the sum of the
         * radii as two legs of a right angled triangle. The final leg describes
         * the distance between the closest point on the movement vector to 'b'
         * and the point that 'a' will be at when it strikes 'b'. We can then
         * subtract the length of this third leg from the magnitude of the distance
         * vector to discover how far 'a' will travel before hitting 'b'.
         */
        // c^2 - a^2 = b^2 (the square of our third leg)
        double thirdLegSq = sumRadiiSq - distToPointClosestToBSq;

        // in the unlikely event that the right-angled triangle hitherto described
        // does not exist, then our thirdLeg will most likely have a negative
        // values. We'll take a precaution and ensure that this is not true
        // before utilizing it in sensitive calculations.
        if (thirdLegSq < 0) {
            return false;
        }

        // the distance that 'a' must travel before it hits 'b' is...
        double distanceToCollision = d - Math.sqrt(thirdLegSq);

        // this is the actual length of a's movement vector
        double moveVectorMag = Math.sqrt(moveVectorMagSq);

        // if the distance that 'a' must travel to collide with 'b' is greater
        // than the magnitude of the movement vector...
        if (moveVectorMag < distanceToCollision) {
            // a collision is impossible; 'a' simply won't reach
            return false; 
        }

        return true;
    }

    public static void respondToCollision(Ball a, Ball b, double epsilon)
    {
        Point2D aCenter = a.getCenter(); // retrieve location of A
        Point2D bCenter = b.getCenter(); // retrieve the location of B

        // compute the sum of the radii of the two balls
        double sumRadii = a.getRadius() + b.getRadius();

        Vector2D collisionNormal = bCenter.minus(aCenter);

        double distance = collisionNormal.magnitude();
        double penetration = sumRadii - distance;

        collisionNormal.normalize();
        
        Vector2D relVect = collisionNormal.times(penetration * 0.5);

        a.getVelocity().minusEquals(relVect);
        b.getVelocity().plusEquals(relVect);

        Vector2D relativeVelocity = a.getVelocity().minus(b.getVelocity());

        double vDotN = relativeVelocity.dot(collisionNormal);

        if (vDotN < 0) {
            return;
        }

        double numerator = -(1 + epsilon) * vDotN;
        double denominator = (1 / a.getMass() + 1 / b.getMass());
        double j = numerator / denominator;

        Vector2D deltaA = collisionNormal.times(j / a.getMass());
        a.getVelocity().plusEquals(deltaA);

        Vector2D deltaB = collisionNormal.times(j / b.getMass());
        b.getVelocity().minusEquals(deltaB);


    }
}
