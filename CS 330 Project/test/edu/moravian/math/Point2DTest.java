/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.math;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author moore
 */
public class Point2DTest
{

    Point2D fiveFive = new Point2D(5, 5);
    Point2D negfiveFive = new Point2D(-5, 5);
    Point2D fiveNegFive = new Point2D(5, -5);
    Point2D negFiveNegFive = new Point2D(-5, -5);

    public Point2DTest()
    {
    }

    //Constructor tests
    @Test
    public void test_reg_contructor()
    {
        Point2D pt = new Point2D(1, -2);
        assertEquals(1, pt.getX(), Point2D.TOL);
        assertEquals(-2, pt.getY(), Point2D.TOL);


        pt = new Point2D(new Point2D(-3, -4));

        assertEquals(-3, pt.getX(), Point2D.TOL);
        assertEquals(-4, pt.getY(), Point2D.TOL);

    }

    @Test
    public void test_blank_con()
    {
        Point2D pt = new Point2D();
        assertEquals(0.0, pt.getX(), 0.0);
        assertEquals(0.0, pt.getY(), 0.0);
    }

    //Set and get tests
    @Test
    public void test_Sets_Gets()
    {
        Point2D pt = new Point2D();
        pt.setX(202);

        assertEquals(202, pt.getX(), 0.0);
        assertEquals(0.0, pt.getY(), 0.0);

        pt.setY(101.2);

        assertEquals(101.2, pt.getY(), 0.0);
    }

    //Hashcode Creation
    @Test
    public void testmakehashcode()
    {
        Point2D pt = new Point2D();
        pt.hashCode();
        //If this doesnt throw exception
    }

    //Equals tests 
    @Test
    public void testEqualsSimple()
    {
        Point2D orig = new Point2D(1, 5);
        Point2D y_big = new Point2D(1, 100);
        Point2D x_big = new Point2D(100, 1);
        Point2D far_off_vect = new Point2D(10, 10);

        assertTrue(orig.equals(orig));
        assertEquals(false, orig.equals(far_off_vect));
        assertEquals(false, orig.equals(y_big));
        assertEquals(false, orig.equals(x_big));
        assertEquals(false, y_big.equals(x_big));

        //Spot test 

    }

    @Test
    public void testEqualsExactOnTol()
    {

        Point2D orig = new Point2D(1, 5);

        //Test the exact line of the tolerance 
        Point2D on_Tol_X = new Point2D(1 + Point2D.TOL, 5);
        Point2D on_Tol_Y = new Point2D(1, Point2D.TOL + 5);

        assertEquals(true, orig.equals(on_Tol_X));
        assertEquals(true, orig.equals(on_Tol_Y));

    }

    @Test
    public void equalsCloseTol()
    {
        Point2D orig = new Point2D(1, 5);

        Point2D slightly_further_X = new Point2D(1 + (2 * Point2D.TOL), 5);
        Point2D slightly_further_Y = new Point2D(1, (2 * Point2D.TOL) + 5);

        assertEquals(false, orig.equals(slightly_further_X));
        assertEquals(false, orig.equals(slightly_further_Y));
    }

    @Test
    public void equalsDifferentClass()
    {
        Point2D swap = new Point2D(2, 1);
        String s = "2";
        assertEquals(false, swap.equals(null));
        assertEquals(false, swap.equals(s));
    }

    @Test
    public void equalsWithinTol()
    {

        double smaller_than_Tol = Point2D.TOL / 10;
        Point2D orig = new Point2D(1, 5);
        double approach_small = 9.0E-9;


        Point2D too_close_X = new Point2D(1 + approach_small, 5);
        Point2D too_close_Y = new Point2D(1 + approach_small, 5);


        assertEquals(true, orig.equals(too_close_X));
        assertEquals(true, orig.equals(too_close_Y));

        Point2D too_close_both = new Point2D(1 + smaller_than_Tol, 5 + smaller_than_Tol);

        assertEquals(true, orig.equals(too_close_both));


        Point2D just_smaller_than_TOL_X = new Point2D(1 + smaller_than_Tol, 5);
        Point2D just_smaller_than_TOL_Y = new Point2D(1, smaller_than_Tol + 5);


        assertEquals(true, orig.equals(just_smaller_than_TOL_X));
        assertEquals(true, orig.equals(just_smaller_than_TOL_Y));
    }

//Subtraction tests
    @Test
    public void simpleMinus()
    {

        assertEquals(new Vector2D(0, 0), fiveFive.minus(fiveFive));
        assertEquals(new Vector2D(2, 1), fiveFive.minus(new Point2D(3, 4)));
        assertEquals(new Vector2D(-5, -5), fiveFive.minus(new Point2D(10, 10)));
        assertEquals(new Vector2D(-5, -5), negFiveNegFive.minus(new Point2D(0, 0)));
    }

    //Mod Equals tests
    @Test
    public void modEqualsSimple()
    {
        Point2D foo = new Point2D(5, 4);
        Point2D foo_old = new Point2D(5, 4);


        foo.modEquals(400, 500);
        assertEquals(foo_old, foo);

        //This should be false because we are modifying foo
        foo = new Point2D(5, 4);
        foo.modEquals(5, 5);
        assertEquals(false, foo_old.equals(foo));
        assertEquals(new Point2D(0, 4), foo);
    }



    @Test
    public void modEqualsBigWrap()
    {
        Point2D foo = new Point2D(500, 112);


        foo.modEquals(5, 1);
        assertEquals(new Point2D(0, 0), foo);

        Point2D bar = new Point2D(1.1, -5);
        bar.modEquals(1, 5);

        assertEquals(new Point2D(.1, 0), bar);

        foo = new Point2D(-111, 0);
        foo.modEquals(11, 111);


    }

    //Plus tests 
    @Test
    public void plusTests()
    {
        assertEquals(new Point2D(-4, -3), new Point2D(1, 2).plus(new Vector2D(-5, -5)));
        assertEquals(Point2D.zero, fiveFive.plus(new Vector2D(-5, -5)));
        assertEquals(new Point2D(10, 7), fiveFive.plus(new Vector2D(5, 2)));
        assertEquals(Point2D.zero, new Point2D(0, 0).plus(new Vector2D(0, 0)));
    }

    @Test
    public void plusEqualsTest()
    {
        Point2D tmpPt = new Point2D(0, 0);

        assertEquals(Point2D.zero, tmpPt);

        tmpPt.plusEquals(new Vector2D(1, 2));

        assertEquals(tmpPt, new Point2D(1, 2));

        tmpPt.plusEquals(new Vector2D(-9, -4));

        assertEquals(new Point2D(-8, -2), tmpPt);
    }

    @Test
    public void randomPt()
    {
        Point2D pt = new Point2D();
        assertEquals(Point2D.zero, pt);
        pt = Point2D.randomPoint(0.0, 1.0, 0.0, 1.1);
        pt.getX();
    }

    @Test
    public void scalePlusTest()
    {
        Point2D orig = new Point2D(1, 1);
        assertEquals(new Point2D(10, -20), orig.scalePlus(3, new Vector2D(3, -7)));
    }

    @Test
    public void scalePlusEquals()
    {
        Point2D orig = new Point2D(1, 2);

        orig.plusEquals(new Vector2D(-2, 0));

        assertEquals(new Point2D(-1, 2), orig);
    }

    @Test
    public void setTest()
    {
        Point2D pt = new Point2D(1, 1);
        pt.set(new Point2D(101, 1012));
        assertEquals(new Point2D(101, 1012), pt);
    }

    @Test
    public void scalePlusEqualsTest()
    {
        Point2D swap = new Point2D(fiveFive);
        swap.scalePlusEquals(2, Vector2D.zero);
        assertEquals(fiveFive, swap);
        swap.scalePlusEquals(2, new Vector2D(2, 4));
        assertEquals(new Point2D(9, 13), swap);
    }

    @Test
    public void toStringTest()
    {
        assertEquals("(5.0, -5.0)", fiveNegFive.toString());
    }

    
    //-1, 5).modEquals(10, 10) is (9, 5).
    @Test
    public void modEqualsSpecificTest()
    {
        Point2D pt = new Point2D(3, 3);
        pt.modEquals(5, 5);
        assertEquals(new Point2D(3, 3), pt);
        
        pt = new Point2D(7, 8);
        pt.modEquals(5, 5);
        assertEquals(new Point2D(2, 3), pt);
        
        pt = new Point2D(-3,-3);
        pt.modEquals(5, 5);
        assertEquals(new Point2D(2,2), pt);
        
        pt = new Point2D(-1,5);
        pt.modEquals(10, 10);
        assertEquals(new Point2D(9,5), pt);
    }
    
    @Test
    public void distanceSQTest()
      {
        assertEquals(200, Point2D.distanceSQ(fiveFive, negFiveNegFive), Point2D.TOL);
      }
      
}
