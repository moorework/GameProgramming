package edu.moravian.math;



import java.util.Random;

/**
 * This class represents a 2D vector. 
 * 
 * @author James Moore (moore.work@live.com)
 */
public class Vector2D
{

    //TODO do not allow negative zeros 
    //TODO or just eliminate them in comparisons 
    private double x;
    private double y;
    public static final Vector2D zero = new Vector2D(0, 0);
    public static final double TOL = 1.0E-8;

    public Vector2D()
    {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(double x, double y)
    {
        this.set(x, y);
    }

    public Vector2D(Vector2D vect)
    {
        this.set(vect);
    }

    /**
     * Determine the angle of the vector as measured from the positive x axis.
     *
     * @return the angle of the vector as measured from the positive x axis.
     */
    public double angle()
    {
        if (this.equals(Vector2D.zero))
        {
            return 0.0;
        }

        if (x == 0)
        {
            if (y > 0)
            {
              //We are on the Q2 line
                return Math.PI / 2;
            }
            else
            {
              //Q4 line
                return - 1 * Math.PI / 2;
            }
        }
        if (y == 0)
        {
            if (x > 0)
            {
              //Q1 line 
                return 0.0;
            }
            else
            {
              //Q2 Line
                return Math.PI;
            }
        }

        double angle = Math.atan2(y, x);


        return angle;
    }

    /**
     * Compute the product of this vector and the inverse of a scalar without
     * changing this vector
     *
     * @param d The denominator of the scalar
     * @return 1/d * this
     */
    public Vector2D divide(double d)
    {
        if (d == 0)
        {
            throw new java.lang.IllegalArgumentException("Cannot divide by 0");
        }
        return sanitize_vector(new Vector2D(this.x / d, this.y / d));
    }

    /**
     * Make this vector equal to the product of itself and the inverse of the
     * scalar:
     *
     * @param d post_call = 1/d * pre_call
     */
    public void divideEquals(double d)
    {
        this.set(this.divide(d));
    }

    /**
     * Compute the dot product of this vector and the parameter:
     *
     * @param rhs The right hand side vector for the dot product
     * @return The dot product of this vector and the input vector
     */
    public double dot(Vector2D rhs)
    {
        return ((this.x * rhs.getX()) + (this.y * rhs.getY()));
    }

    /**
     * Two vectors are equal if both the x and y components are within TOL of
     * each other0
     *
     * @param obj The input vector
     * @return The equality status of the two vectors
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Vector2D other = (Vector2D) obj;

        other = sanitize_vector(other);
        this.set(sanitize_vector(this));

        double diff_x = this.x - other.x;
        double diff_y = this.y - other.y;

        if (TOL > Math.abs(diff_x))
        {
            if (TOL > Math.abs(diff_y))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Compute the left orthogonal vector without changing this vector
     *
     * @return the left orthogonal vector without changing this vector
     */
    public Vector2D getLeftOrtho()
    {
        // return this.sanitize_vector(new Vector2D((- y), x));
        Vector2D swap = new Vector2D(this);
        swap.set(-this.getY(), this.getX());
        return swap;
    }

    /**
     * Compute the normalized vector (same direction with magnitude 1) without
     * changing this vector.
     *
     * @return
     */
    public Vector2D getNormalized()
    {
        if (this.equals(Vector2D.zero))
        {
            return new Vector2D(0, 0);
        }
        return this.sanitize_vector(this.divide(this.magnitude()));
    }

    /**
     * Compute the right orthogonal vector without changing this vector
     *
     * @return
     */
    public Vector2D getRightOrtho()
    {
        //return this.sanitize_vector(new Vector2D(y, -x));
        Vector2D swap = new Vector2D(this);
        swap.set(this.getY(), -1 * this.getX());
        return swap;
    }

    /**
     * Compute a hashcode for this vector
     *
     * @return
     */
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }

    /**
     * get the X component of this vector
     *
     * @return
     */
    public double getX()
    {
        return x;
    }

    /**
     * Get the y component of this vector
     *
     * @return
     */
    public double getY()
    {
        return y;
    }

    /**
     * Get the magnitude of the vector
     *
     * @return
     */
    public double magnitude()
    {
        return Math.sqrt((x * x) + (y * y));
    }

    /**
     * Get the square of the magnitude of the vector
     *
     * @return
     */
    public double magnitudeSq()
    {
        return x * x + y * y;
    }

    /**
     * Compute the difference of this vector and the parameter without changing
     * this vector
     *
     * @param rhs right hand side vector
     * @return this - rhs
     */
    public Vector2D minus(Vector2D rhs)
    {
        return sanitize_vector(new Vector2D(x - rhs.x, y - rhs.y));
    }

    /**
     * Make this vector equal to the difference of itself and the specified
     * vector
     *
     * @param rhs right hand side vector
     * @return this = this - rhs
     */
    public void minusEquals(Vector2D rhs)
    {
        this.set(minus(rhs));
    }

    /**
     * Change this vector to the negation of itself: this = -1 * this
     */
    public void negate()
    {
        this.set(-1 * this.x, - 1 * this.y);
    }

    /**
     * Change this vector to one in the same direction with magnitude of 1.
     */
    public void normalize()
    {
        this.set(this.getNormalized());
    }

    /**
     * Compute the sum of this vector and the parameter without changing this
     * vector:
     *
     * @param rhs Right hand side vector
     * @return this + rhs
     */
    public Vector2D plus(Vector2D rhs)
    {
        return new Vector2D(this.x + rhs.getX(), this.y + rhs.getY());
    }

    /**
     * Make this vector equal to the sum of itself and the specified vector
     *
     * @param rhs Right hand side vector
     */
    public void plusEquals(Vector2D rhs)
    {

        x += rhs.x;
        y += rhs.y;
    }

    /**
     * Generate a random vector with a fixed magnitude
     *
     * @param magnitude
     * @return
     */
    static Vector2D randomVectorFixedMagnitude(double magnitude)
    {

        if (magnitude == 0)
        {
            return new Vector2D(Vector2D.zero);
        }

        double mag_sq = magnitude * magnitude;
        Random rand = new Random(System.currentTimeMillis());
        double portion = rand.nextDouble();

        double first = Math.sqrt(portion * mag_sq);

        double second = Math.sqrt(mag_sq * (1 - portion));
        return new Vector2D(first, second);
    }

    /**
     * Generate a random vector with a magnitude no more than the given value
     *
     * @param maxMagnitude
     * @return
     */
    static Vector2D randomVectorMaxMagnitude(double maxMagnitude)
    {
        if (maxMagnitude == 0)
        {
            return new Vector2D(Vector2D.zero);
        }
        Random rand = new Random(System.currentTimeMillis());

        double first = rand.nextDouble() * maxMagnitude * maxMagnitude;
        double second = rand.nextDouble() * (maxMagnitude * maxMagnitude - first);
        return sanitize_vector(new Vector2D(Math.sqrt(first), Math.sqrt(second)));
    }

    /**
     * Reflect the X component
     */
    public void reflectX()
    {
        if (this.x != 0)
        {
            this.x *= -1;
        }
    }

    /**
     * Reflect the Y component
     */
    public void reflectY()
    {
        if (this.y != 0)
        {
            y = - 1 * y;
        }
    }

    /**
     * Compute the sum of this vector and v scaled by "scalar". This computation
     * will not change this vector
     *
     * @param scalar
     * @param v
     * @return this + scalar * v
     */
    public Vector2D scalePlus(double scalar, Vector2D v)
    {

        return this.plus(v.times(scalar));
    }

    /**
     * Change this vector to the sum of this vector and the scaled vector v.
     * this = this + scalar * v
     *
     * @param scalar Scalar to scale the input vector by
     * @param v Input Vector
     */
    public void scalePlusEquals(double scalar, Vector2D v)
    {
        this.set(this.scalePlus(scalar, v));
    }

    /**
     * Set the vector's parameters
     *
     * @param x_in x comp
     * @param y_in y comp
     */
    public void set(double x_in, double y_in)
    {
        if (Math.abs(x_in) > TOL)
        {
            this.x = x_in;
        }
        else
        {
            this.x = 0.0;
        }
        if (Math.abs(y_in) > TOL)
        {
            this.y = y_in;
        }
        else
        {
            this.y = 0.0;
        }
    }

    /**
     * Set the parameters of this vector to be equal to the input
     *
     * @param vect
     */
    public void set(Vector2D vect)
    {
        this.set(vect.getX(), vect.getY());
    }

    /**
     * Set the vector to equal it's own left orthogonal vector
     */
    public void setLeftOrtho()
    {
        this.set(this.getLeftOrtho());
    }

    /**
     * Set the vector to equal it's own right orthogonal vector
     */
    public void setRightOrtho()
    {
        this.set(this.getRightOrtho());
    }

    /**
     * Compute the product of this vector and a scalar without changing this
     * vector
     *
     * @param d
     * @return d * this
     */
    public Vector2D times(double d)
    {
        return new Vector2D(this.x * d, this.y * d);
    }

    /**
     * Make this vector equal to the product of itself and the scalar: this = d
     * * this
     *
     * @param d
     */
    public void timesEquals(double d)
    {
        this.set(this.times(d));
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    /**
     * If the magnitude of the vector is less than max, then the vector is
     * unchanged.
     *
     * @param max
     */
    public void truncate(double max)
    {
        if (this.magnitude() > max)
        {
            this.timesEquals(max / this.magnitude());
        }
    }

    private static Vector2D sanitize_vector(Vector2D other)
    {
        Vector2D swap = new Vector2D(other);
        if (swap.getX() == -0)
        {
            swap.set(0, swap.getY());
        }
        if (swap.getY() == -0)
        {
            swap.set(swap.getX(), 0);
        }

        return other;
    }
}
