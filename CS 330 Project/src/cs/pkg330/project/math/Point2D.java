package cs.pkg330.project.math;

/**
 * This class represents a basic two-dimensional point. Methods that correspond
 * to common operations with vectors are supported.
 *
 * @author barros
 */
public class Point2D {

    public static final double TOL = 1.0E-8;
    private double x;
    private double y;

    /**
     * Create a new point representing the origin.
     */
    public Point2D() {
        x = 0.0;
        y = 0.0;
    }

    /**
     * Create a new point using the specified values.
     *
     * @param x the x value of the point
     * @param y the y value of the point
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;

        levelXandY();
    }

    /**
     * Create a point with x and y values equal to the parameter.
     *
     * @param p the point to "copy"
     */
    public Point2D(Point2D p) {
        this.x = p.getX();
        this.y = p.getY();

        levelXandY();
    }

    /**
     * Get the x component of the point.
     *
     * @return the value of x
     */
    public double getX() {
        return x;
    }

    /**
     * Get the y component of the point.
     *
     * @return the value of y
     */
    public double getY() {
        return y;
    }

    /**
     * Change the x component of the point.
     *
     * @param x the new value
     */
    public void setX(double x) {
        this.x = x;

        levelXandY();
    }

    /**
     * Change the y component of the point.
     *
     * @param y the new value
     */
    public void setY(double y) {
        this.y = y;

        levelXandY();
    }

    /**
     * Change the value of the point to equal the parameters.
     *
     * @param x the new x value
     * @param y the new y value
     */
    public void set(double x, double y) {
        this.x = x;
        this.y = y;

        levelXandY();
    }

    /**
     * Make this point have the same value as the parameter
     *
     * @param p the point to "copy"
     */
    public void set(Point2D p) {
        this.x = p.getX();
        this.y = p.getY();

        levelXandY();
    }

    /**
     * Compute the vector representing this - rhs
     *
     * @param rhs the right hand side of the difference
     * @return the vector pointing from rhs to this
     */
    public Vector2D minus(Point2D rhs) {
        double xV = x - rhs.getX();
        double yV = y - rhs.getY();

        return new Vector2D(xV, yV);
    }

    /**
     * Add the vector to this point: this = this + rhs
     *
     * @param rhs the vector to add
     */
    public void plusEquals(Vector2D rhs) {
        x = x + rhs.getX();
        y = y + rhs.getY();

        levelXandY();
    }

    /**
     * Compute the sum of this point and the vector parameter. The point will
     * not be modified: return this + rhs
     *
     * @param rhs the vector to add
     * @return the sum of the point and the vector
     */
    public Point2D plus(Vector2D rhs) {
        double new_x = x + rhs.getX();
        double new_y = y + rhs.getY();

        return new Point2D(new_x, new_y);
    }

    /**
     * Compute the addition of this point and the scaled vector. This point is
     * not changed by this operation: return this + scalar * v
     *
     * @param scalar the scalar
     * @param v the vector
     * @return the sum of this point and the scaled vector.
     */
    public Point2D scalePlus(double scalar, Vector2D v) {
        double new_x = x + (scalar * v.getX());
        double new_y = y + (scalar * v.getY());

        return new Point2D(new_x, new_y);
    }

    /**
     * Modify this point by adding the scaled version of the vector: this = this
     * + scalar * v
     *
     * @param scalar the scalar
     * @param v the vector
     */
    public void scalePlusEquals(double scalar, Vector2D v) {
        double new_x = x + (scalar * v.getX());
        double new_y = y + (scalar * v.getY());

        set(new_x, new_y);
    }

    /**
     * Ensure that the point is within the range 0 <= x < xMax and 0 <= y <
     * yMax. If it isn't, wrap the point as though it is on a torus.
     *
     * @param xMax the upper bound on the x value
     * @param yMax the upper bound on the y value
     */
    public void modEquals(double xMax, double yMax) {
        // the client broke the function contract
        if (xMax == 0.0 || yMax == 0.0) {
            return;
        }

        while (x < 0) {
            x += xMax;
        }
        
        while (y < 0) {
            y += yMax;
        }

        double xDiff = xMax - x;
        double yDiff = yMax - y;

        if (xDiff <= -TOL) {
            x = x % xMax;
        }

        if (yDiff <= -TOL) {
            y = y % yMax;
        }

        levelXandY();
    }

    /**
     * Two points are equal if their x and y components are both within TOL of
     * each other
     *
     * @param obj the object to compare against
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }

        // if the object isn't a point, the notion of equality is meaningless
        if (!(obj instanceof Point2D)) {
            return false;
        }

        Point2D other = (Point2D) obj;

        // if the point's x & y values are within TOL of each of this point's...
        if ((Math.abs((other.x - x)) < TOL && Math.abs((other.y - y)) < TOL)) {
            return true; // they're equal
        }

        // else the other object is a point that is not equal to this one
        return false;
    }

    /**
     * Compute the hash code for this point
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int result;

        // Produce our hash code by converting fields to integers, summing
        // and multiplying by a prime number (produces better distribution).

        // convert Double to long
        long longDouble = Double.doubleToLongBits(x);
        // convert long to int by performing unsigned right shift and XORing
        // result with original long
        result = (int) (longDouble ^ (longDouble >>> 32));

        //repeat
        longDouble = Double.doubleToLongBits(y);
        result += (int) (longDouble ^ (longDouble >>> 32));

        return result * 7;
    }

    /**
     * Convert this point to a string of the form "(x, y)"
     *
     * @return the string representation of this point
     */
    @Override
    public String toString() {
        return "(" + Double.toString(x) + ", " + Double.toString(y) + ")";
    }

    /**
     * Create a random point in the range [xMin, xMax) and [yMin, yMax).
     *
     * @param xMin the (inclusive) minimum for x.
     * @param xMax the (exclusive) maximum for x.
     * @param yMin the (inclusive) minimum for y.
     * @param yMax the (exclusive) maximum for x.
     *
     * @return a random point in the specified range
     */
    public static Point2D randomPoint(double xMin, double xMax,
            double yMin, double yMax) {

        double newX = (Math.random() * (xMax - xMin)) + xMin;
        double newY = (Math.random() * (yMax - yMin)) + yMin;

        return new Point2D(newX, newY);
    }

    private void levelXandY() {
        if (Math.abs(x) < TOL) {
            x = 0.0;
        }

        if (Math.abs(y) < TOL) {
            y = 0.0;
        }
    }
}
