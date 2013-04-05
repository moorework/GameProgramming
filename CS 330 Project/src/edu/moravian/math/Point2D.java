package edu.moravian.math;

import java.util.Random;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class Point2D
  {

    private double x;
    private double y;
    public static final double TOL = 1.0E-8;
    public static final Point2D zero = new Point2D(0, 0);

    public Point2D()
      {
        this.x = 0.0;
        this.y = 0.0;
      }

    public Point2D(Point2D pt)
      {
        this.x = pt.getX();
        this.y = pt.getY();
      }

    public Point2D(double x, double y)
      {
        this.x = x;
        this.y = y;
      }

    public static double distanceSQ(Point2D a, Point2D b)
      {
        return (b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y);
      }

    public double getX()
      {
        return x;
      }

    public void setX(double x)
      {
        this.x = x;
      }

    public double getY()
      {
        return y;
      }

    public void setY(double y)
      {
        this.y = y;
      }

    @Override
    public int hashCode()
      {
        int hash = 7;
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
      }

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
        final Point2D other = (Point2D) obj;

        sanitize_point(other);

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
     * Two points are equal if their x and y components are both within TOL of
     * each other Compute the vector representing this - rhs
     *
     * @param rhs
     * @return
     */
    public Vector2D minus(Point2D rhs)
      {
        return new Vector2D(this.x - rhs.getX(), this.y - rhs.getY());
      }

    /**
     * Ensure that the point is within the range 0 <= x < xMax and 0 <= y <
     * yMax.
     *
     *
     *

     *
     * @param xMax
     * @param yMax
     */
    public void modEquals(double xMax, double yMax)
      {
        while (x < 0)
          {
            x += xMax;
          }

        while (y < 0)
          {
            y += yMax;
          }

        if (xMax == 0)
          {
          }
        else
          {

            this.x = Math.abs(this.x) % xMax;

          }
        if (yMax == 0)
          {
          }
        else
          {
            this.y = Math.abs(this.y) % yMax;
          }
      }

    /**
     * Compute the sum of this point and the vector parameter.
     *
     * @param rhs
     * @return
     */
    public Point2D plus(Vector2D rhs)
      {
        return new Point2D(this.x + rhs.getX(), this.y + rhs.getY());
      }

    /**
     * Add the vector to this point: this = this + rhs
     *
     * @param rhs
     */
    void plusEquals(Vector2D rhs)
      {
        this.x += rhs.getX();
        this.y += rhs.getY();
      }

    /**
     * Create a random point in the range [xMin, xMax) and [yMin, yMax).
     *
     * @param xMin
     * @param xMax
     * @param yMin
     * @param yMax
     * @return
     */
    public static Point2D randomPoint(double xMin, double xMax, double yMin, double yMax)
      {
        Random rand = new Random(System.currentTimeMillis());

        double x_pt = xMin + rand.nextDouble() * (xMax - xMin);
        double y_pt = yMin + rand.nextDouble() * (yMax - yMin);
        return new Point2D(x_pt, y_pt);
      }

    /**
     * Compute the addition of this point and the scaled vector.
     *
     * @param scalar
     * @param v
     * @return this + vector * scalar
     */
    public Point2D scalePlus(double scalar, Vector2D v)
      {
        return this.plus(v.times(scalar));
      }

    /**
     * Modify this point by adding the scaled version of the vector: this = this
     * + scalar * v
     *
     * @param scalar
     * @param v
     */
    void scalePlusEquals(double scalar, Vector2D v)
      {
        this.plusEquals(v.times(scalar));
      }

    /**
     * Make this point have the same value as the parameter
     *
     * @param p
     */
    public void set(Point2D p)
      {
        this.x = p.getX();
        this.y = p.getY();
      }

    @Override
    public String toString()
      {
        return "(" + x + ", " + y + ")";
      }

    private Point2D sanitize_point(Point2D other)
      {
        Point2D temp = new Point2D(other);
        if (other.x == -0)
          {
            temp.x = 0;
          }
        if (other.y == -0)
          {
            temp.y = 0;
          }

        return temp;
      }
  }
