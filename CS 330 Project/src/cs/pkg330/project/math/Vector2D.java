
package cs.pkg330.project.math;

/**
 * This class represents a two-dimensional vector.
 * For effeciency, the class offers two versions of most operations:
 * one mutates the vector (this avoids the need to create a new object)
 * and the other computes the result as a new vector without changing
 * any components of the computation. In addition, because computations
 * of the form v + d * u are common (u, v are vectors; d is a scalar),
 * they are supported as a single method. The static class value TOL is
 * used in *all* computations to check for value close to zero.
 * (see note on this field)
 *
 *
 * @author barros
 */
public class Vector2D {

    /**
     * TOL is the threshold for determining whether a component "goes to zero."
     * In any vector where |x| < TOL or |y| < TOL, then that value becomes zero.
     */
    public static final double TOL = 1.0E-8;
    public static final Vector2D zero = new Vector2D(0.0, 0.0);
    
    // Velocity; vectors do not exist spatially.
    private double x;
    private double y;
    
    // flag value indicating cache data up-to-date
    private final boolean GOOD_CACHE_VAL = true;
    // flag value indicating cache data out-of-date
    private final boolean BAD_CACHE_VAL = false; 

    /**
     * Create a zero vector
     */
    public Vector2D() {
        x = 0.0;
        y = 0.0;
        
        // initialize caching flags
        magCacheFlag = BAD_CACHE_VAL;
    }

    /**
     * Create a vector with a specified value
     *
     * @param x the x component of the new vector
     * @param y the y component of the new vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
        
        // initialize caching flags
        magCacheFlag = BAD_CACHE_VAL;
        
        levelVelocity();
    }

    /**
     * Create a vector equal to the specified vector
     *
     * @param v the vector to "copy"
     */
    public Vector2D(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
        
        // initialize caching flags
        magCacheFlag = BAD_CACHE_VAL;
        
        levelVelocity();
    }

    /**
     * Get the x value of the vector
     *
     * @return the x component
     */
    public double getX() {
        return x;
    }

    /**
     * Get the y value of the vector
     * 
     * @return the y component
     */
    public double getY() {
        return y;
    }

    /**
     * Change the value of the vectors to the specified values
     *
     * @param x the new x value
     * @param y the new y value
     */
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
        
        // update caching flags
        magCacheFlag = BAD_CACHE_VAL;
        
        levelVelocity();
    }

    /**
     * Change the value of the vector to equal the parameter
     *
     * @param v the vector to "copy"
     */
    public void set(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
        
        // update caching flags
        magCacheFlag = BAD_CACHE_VAL;
        
        levelVelocity();
    }

    /**
     * Get the magnitude of the vector
     *
     * @return the magnitude
     */
    public double magnitude() {
        // if cached magnitude data out-of-date
        if (magCacheFlag == BAD_CACHE_VAL) {
            // update it...
            cachedMag = Math.sqrt((x * x) + (y * y));
            
            //  and now the cached magnitude data is up-to-date
            magCacheFlag = GOOD_CACHE_VAL;
        }
        
        // distance formula
        return cachedMag;
    }

    /**
     * Get the square of the magnitude
     *
     * @return the square of the magnitude
     */
    public double magnitudeSq() {
        double magSq = (x * x) + (y * y);
        
        // return square of magnitude
        return magSq;
    }

    /**
     * Determine the angle of the vector as measured from the positive x axis.
     * By definition, the angle of a 0 vector is zero.
     *
     * @return the angle in radians from -PI to PI
     */
    public double angle() {
        if (this == zero) {
            return 0.0;
        }
        
        return Math.atan2(y, x);
        
    }

    /**
     * Compute the right orthogonal vector without changing this vector
     *
     * @return the right orthogonal vector
     */
    public Vector2D getRightOrtho() {
        return new Vector2D(y, -x);
    }

    /**
     * Change this vector to equal its right orthogonal vector
     */
    public void setRightOrtho() {
        set(y, -x);
    }

    /**
     * Compute the left orthogonal vector without changing this vector
     *
     * @return the left orthogonal vector
     */
    public Vector2D getLeftOrtho() {
        return new Vector2D(-y, x);
    }

    /**
     * Change this vector to equal its left orthogonal vector
     */
    public void setLeftOrtho() {
        set(-y, x);
    }

    /**
     * Change this vector to one in the same direction with magnitude of 1.
     * If the vector is the zero vector, it is unchanged.
     */
    public void normalize() {
        if (this.equals(zero)) {
            return;
        }
        
        double magnitude = magnitude();
        
        set(x / magnitude, y / magnitude);
    }

    /**
     * Compute the normalized vector (same direction with magnitude 1) without
     * changing this vector. If this vector is the zero vector, a zero
     * vector will be returned (a new object).
     * 
     * @return the normalized vector
     */
    public Vector2D getNormalized() {
        if (this.equals(zero)) {
            return new Vector2D(zero);
        }
        
        double magnitude = magnitude();
        
        return new Vector2D(x / magnitude, y / magnitude);
    }

    /**
     * Compute the sum of this vector and the parameter without changing this
     * vector: return this + sum
     *
     * @param rhs the right hand side of the addition
     * @return the sum of the two vectors
     */
    public Vector2D plus(Vector2D rhs) {
        return new Vector2D(x + rhs.x, y + rhs.y);
    }
    
    /**
     * Make this vector equal to the sum of itself and the specified vector:
     * this = this + rhs
     * 
     * @param rhs the vector to add (right hand side of the sum)
     */
    public void plusEquals(Vector2D rhs) {
        set(x + rhs.x, y + rhs.y);
    }
    
    /**
     * Compute the difference of this vector and the parameter
     * without changing this vector: return this - rhs
     * 
     * @param rhs the right hand side of the difference
     * @return the difference of the two vectors
     */
    public Vector2D minus(Vector2D rhs) {
        return new Vector2D(x - rhs.x, y - rhs.y);
    }
    
    /**
     * Make this vector equal to the difference of itself and the specified
     * vector: this = this - rhs
     * 
     * @param rhs the vector to subtract (right hand side of the difference)
     */
    public void minusEquals(Vector2D rhs) {
        set(x - rhs.x, y - rhs.y);
    }
    
    /**
     * Compute the product of this vector and a scalar without changing this
     * vector: return d * this
     * 
     * @param d the scalar
     * @return the product of the scalar and the vector
     */
    public Vector2D times(double d) {
        return new Vector2D(d * x, d * y);
    }
    
    /**
     * Make this vector equal to the product of itself and the scalar:
     * this = d * this
     * 
     * @param d the scalar
     */
    public void timesEquals(double d) {
        set(x * d, y * d);
    }
    
    /**
     * Compute the product of this vector and the inverse of a scalar without
     * changing this vector: return 1/d * this
     * 
     * @param d the scalar
     * @return the product of the inverse of the scalar and the vector
     * @throws java.lang.IllegalArgumentException if d is 0 (using == comparison)
     */
    public Vector2D divide(double d) throws java.lang.IllegalArgumentException {
        if (d == 0.0) {
            throw new java.lang.IllegalArgumentException("Zero arg");
        }
        
        double dInverse = 1 / d;
        return new Vector2D(x * dInverse, y * dInverse);
    }
    
    /**
     * Make this vector equal to the product of itself and the inverse of the
     * scalar: this = 1/d * this
     * 
     * @param d the scalar
     * @throws java.lang.IllegalArgumentException if d is 0 (using == comparison)
     */
    public void divideEquals(double d) throws java.lang.IllegalArgumentException {
        if (d == 0.0) {
            throw new java.lang.IllegalArgumentException("Zero arg");
        }
        
        double dInverse = 1 / d;
        set(x * dInverse, y * dInverse);
    }
    
    /**
     * Change this vector to the negation of itself: this = -1 * this
     */
    public void negate() {
        set(-x, -y);
    }
    
    /**
     * Change the sign on the x component. If v = (x, y), then this method
     * will make it v = (-x, y)
     */
    public void reflectX() {
        set(-x, y);
    }
    /**
     * Change the sign on the y component. If v = (x, y), then this method
     * will make it v = (x, -y)
     */
    public void reflectY() {
        set(x, -y);
    }
    
    /**
     * Compute the dot product of this vector and the parameter:
     * return this * rhs (where '*' is the dot product)
     * 
     * @param rhs the right hand side of the dot product
     * @return the dot product of the two vectors
     */
    public double dot(Vector2D rhs) {
        return ((x * rhs.x) + (y * rhs.y));
    }
    
    /**
     * Compute the sum of this vector and v scaled by "scalar":
     * return this + scalar * v - This computation will not change this vector
     * 
     * @param scalar the scalar
     * @param v the vector
     * @return the Vector result from the sum
     */
    public Vector2D scalePlus(double scalar, Vector2D v) {
        return new Vector2D(x + (scalar * v.x), y + (scalar * v.y));
    }
    
    /**
     * Change this vector to the sum of this vector and the scaled vector v:
     * this = this + scalar * v
     * 
     * @param scalar the scalar
     * @param v the vector
     */
    public void scalePlusEquals(double scalar, Vector2D v) {
        set(x + (scalar * v.x), y + (scalar * v.y));
    }
    
    /**
     * If the magnitude of the vector is less than max, then the vector is
     * unchanged. Otherwise, the vector is truncated so that it points in
     * the same direction but has a magnitude of max
     * 
     * @param max the maximum allowed magnitude
     */
    public void truncate(double max) {
        if (max == 0.0) {
            set(0.0, 0.0);
            return;
        }
        
        double magnitude = magnitude();
        
        if (magnitude < max) {
            // do nothing; that's absolutely fine
        }
        else {
            // compute the ratio of the max to the present magnitude;
            // this tells us how much to scale the vector by
            double scalar = max / magnitude;
            set(x * scalar, y * scalar); // scale the vector
        }
    }
    
    /**
     * Two vectors are equal if both the x and y components are within TOL of
     * each other
     * 
     * @param obj the object to compare
     * @return true of they are the same, false otherwise
     */
    @Override
    public boolean equals (java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        
        // if the object isn't a vector, the notion of equality is meaningless
        if (!(obj instanceof Vector2D)) {
            return false;
        }
        
        Vector2D other = (Vector2D) obj;
        
        // if the vector's velocity values are within TOL of this vector's...
        if ((Math.abs((other.x - x)) < TOL && Math.abs((other.y - y)) < TOL)) {
            return true; // they're equal
        }
        
        // else the other object is a vector that is not equal to this one
        return false;
    }
    
    /**
     * Compute the hash value for this vector
     * 
     * @return the hash value
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
        result = (int)(longDouble ^ (longDouble >>> 32));
        
        //repeat
        longDouble = Double.doubleToLongBits(y);
        result += (int)(longDouble ^ (longDouble >>> 32));
        
        return result * 7;
    }
    
    /**
     * Converts the vector to a string of the form "(x, y)"
     * 
     * @return the string representing the vector
     */
    @Override
    public java.lang.String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    /**
     * Generate a random vector with a fixed magnitude
     * 
     * @param magnitude the magnitude of the vector returned
     * @return a random vector with the given magnitude
     */
    public static Vector2D randomVectorFixedMagnitude(double magnitude) {
        if (magnitude == 0.0) {
            return new Vector2D(0, 0);
        }
        
        double magnitudeSq = magnitude * magnitude;
        
        // square the magnitude to leverage the Pytahgorean theorem to determine
        // our x and y values
        double xVal = Math.sqrt(Math.random() * magnitudeSq);
        
        // given our computed xVal, we can determine the yVal by computing the
        // angle between x and the hypotenuse and, using the sine of that angle,
        // determine the yVal by scaling it by the hypotenuse
        double angle = Math.acos(xVal / magnitude);
        double yVal = (magnitude * Math.sin(angle));
        
        return new Vector2D(xVal, yVal);
    }
    
    /**
     * Generate a random vector with a magnitude no more than the given value
     * 
     * @param maxMagnitude the maximum magnitude
     * @return a random vector
     */
    public static Vector2D randomVectorMaxMagnitude(double maxMagnitude) {
        if (maxMagnitude == 0.0) {
            return new Vector2D(0.0, 0.0);
        }
        
        // determine a magnitude up to but exluding the maximum
        double magnitude = Math.random() * maxMagnitude;
        
        // square the magnitude to leverage the Pytahgorean theorem to determine
        // our x and y values
        double magnitudeSq = magnitude * magnitude;
        
        // compute an xValue up to but excluding the squared magnitude; the
        // xValue will be the square root of the result to accomodate our
        // use of the Pythagorean theorem to determine the xVal
        double xVal = Math.sqrt(Math.random() * magnitudeSq);
        
        // given our computed xVal, we can determine the yVal by computing the
        // angle between x and the hypotenuse and, using the sine of that angle,
        // determine the yVal by scaling it by the hypotenuse
        double angle = Math.acos(xVal / magnitude);
        double yVal = (magnitude * Math.sin(angle));
        
        return new Vector2D(xVal, yVal);
    }
    
    private void levelVelocity() {
        if (Math.abs(x) < TOL) {
            x = 0.0;
        }
        
        if (Math.abs(y) < TOL) {
            y = 0.0;
        }
    }
    
    /*
     * Variables that follow exist for the sake of caching
     */
    
    // Flags
    private boolean magCacheFlag;
    
    // Cache data
    private double cachedMag;
}
