/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.math;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class Vector2DTest
{
// <editor-fold defaultstate="collapsed" desc=" Vectors ">
    public static final Vector2D zeroVect = new Vector2D(0, 0);
    public static final Vector2D zeroNegFiveVect = new Vector2D(0, -5);
    public static final Vector2D zeroFiveVect = new Vector2D(0, 5);
    public static final Vector2D fiveFiveVect = new Vector2D(5, 5);
    public static final Vector2D fiveZeroVect = new Vector2D(5, 0);
    public static final Vector2D fiveNegfiveVect = new Vector2D(5, -5);
    public static final Vector2D negFiveFiveVect = new Vector2D(-5, 5);
    public static final Vector2D negFiveZeroVect = new Vector2D(-5, 0);
    public static final Vector2D negFivenegFiveVect = new Vector2D(-5, -5);
//
    public static final Vector2D oneThreeVect = new Vector2D(1, 3);
    public static final Vector2D tenTwoVect = new Vector2D(10, 2);
    //
    public static final Vector2D negSixTwoVect = new Vector2D(-6, 2);
    public static final Vector2D negFiveTwoVect = new Vector2D(-5, 2);
    //
    public static final Vector2D negThreeNegTwo = new Vector2D(-3, -2);
    public static final Vector2D negNineNegOne = new Vector2D(-9, -1);
//
    public static final Vector2D twelveNegFourVect = new Vector2D(12, -4);
    public static final Vector2D elevenNegOneFourtyTwoVect = new Vector2D(11, -142);
//
// </editor-fold>
    public Vector2DTest()
    {
        
    }

    //Contructor tests
    @Test
    public void contructTestBlank()
    {
        Vector2D vect = new Vector2D();

        boolean status = vect.equals(new Vector2D());

        assertEquals(zeroVect, vect);
        assertEquals(false, vect.equals(new Vector2D(1, 1)));
    }

    @Test
    public void constructTestPoints()
    {
        Vector2D vect = new Vector2D(0, 5.0);
        assertEquals(vect, zeroFiveVect);
        assertEquals(false, new Vector2D().equals(vect));
        assertEquals(0.0, vect.getX(), 0.0);
        assertEquals(5.0, vect.getY(), 0.0);
    }

    @Test
    public void constructVector2D()
    {
        Vector2D vect = new Vector2D(zeroFiveVect);
        assertEquals(zeroFiveVect, vect);
        assertEquals(false, vect.equals(new Vector2D()));
        assertEquals(0.0, vect.getX(), 0.0);
        assertEquals(5.0, vect.getY(), 0.0);
    }

    //Equality tests
    @Test
    public void equalsSimple()
    {

        Vector2D orig = new Vector2D(1, 5);
        Vector2D y_big = new Vector2D(1, 100);
        Vector2D x_big = new Vector2D(100, 5);
        Vector2D far_off_vect = new Vector2D(10, 10);

        assertTrue(orig.equals(orig));
        assertEquals(false, orig.equals(far_off_vect));
        assertEquals(false, orig.equals(y_big));
        assertEquals(false, orig.equals(x_big));

        //Spot test 

    }

    @Test
    public void equalsExactOnTol()
    {
        Vector2D orig = new Vector2D(1, 5);

        //Test the exact line of the tolerance 
        Vector2D on_Tol_X = new Vector2D(1 + Vector2D.TOL, 5);
        Vector2D on_Tol_Y = new Vector2D(1, Vector2D.TOL + 5);

        assertEquals(true, orig.equals(on_Tol_X));
        assertEquals(true, orig.equals(on_Tol_Y));

    }

    @Test
    public void equalsCloseTol()
    {
        Vector2D orig = new Vector2D(1, 5);

        Vector2D slightly_further_X = new Vector2D(1 + (2 * Vector2D.TOL), 5);
        Vector2D slightly_further_Y = new Vector2D(1, (2 * Vector2D.TOL) + 5);

        assertEquals(false, orig.equals(slightly_further_X));
        assertEquals(false, orig.equals(slightly_further_Y));



    }

    @Test
    public void equalsWithinTol()
    {

        double smaller_than_Tol = Vector2D.TOL / 10;
        Vector2D orig = new Vector2D(1, 5);
        double approach_small = 9.0E-9;


        Vector2D too_close_X = new Vector2D(1 + approach_small, 5);
        Vector2D too_close_Y = new Vector2D(1 + approach_small, 5);


        assertEquals(true, orig.equals(too_close_X));
        assertEquals(true, orig.equals(too_close_Y));

        Vector2D too_close_both = new Vector2D(1 + smaller_than_Tol, 5 + smaller_than_Tol);

        assertEquals(true, orig.equals(too_close_both));


        Vector2D just_smaller_than_TOL_X = new Vector2D(1 + smaller_than_Tol, 5);
        Vector2D just_smaller_than_TOL_Y = new Vector2D(1, smaller_than_Tol + 5);


        assertEquals(true, orig.equals(just_smaller_than_TOL_X));
        assertEquals(true, orig.equals(just_smaller_than_TOL_Y));
    }

    @Test
    public void equalsDifferentClass()
    {
        Vector2D swap = new Vector2D(2, 1);
        String s = "2";
        assertEquals(false, swap.equals(null));
        assertEquals(false, swap.equals(s));
    }

    @Test
    public void GetX_GetY_test()
    {
        assertEquals(0.0, zeroFiveVect.getX(), 0.0);
        assertEquals(5.0, zeroFiveVect.getY(), 0.0);
    }

    //Angle tests 
    @Test
    public void angleFourDirections()
    {
        //Test the four directions 
        assertEquals(0.0, fiveZeroVect.angle(), 0.0);
        assertEquals(Math.PI / 2, zeroFiveVect.angle(), 0.0);
        assertEquals(-Math.PI / 2, zeroNegFiveVect.angle(), 0.0);
        assertEquals(Math.PI, negFiveZeroVect.angle(), 0.0);
    }

    @Test
    public void zeroAngle()
    {
        assertEquals(0.0, new Vector2D(Vector2D.zero).angle(), Vector2D.TOL);
    }

    @Test
    public void angleSpotTestQ1()
    {
        assertEquals(1.249045772, oneThreeVect.angle(), Vector2D.TOL);
        assertEquals(0.19739555, tenTwoVect.angle(), Vector2D.TOL);
    }

    @Test
    public void angleSpotTestQ2()
    {

        assertEquals(2.761086276, negFiveTwoVect.angle(), Vector2D.TOL);
        assertEquals(2.81984209, negSixTwoVect.angle(), Vector2D.TOL);
    }

    @Test
    public void angleSpotTestQ3()
    {
        assertEquals(-3.03093543, negNineNegOne.angle(), Vector2D.TOL);
        assertEquals(3.72959526 - 2 * Math.PI, negThreeNegTwo.angle(), Vector2D.TOL);
    }

    @Test
    public void angleSpotTestQ4()
    {
        assertEquals(5.96143475 - 2 * Math.PI, twelveNegFourVect.angle(), Vector2D.TOL);
        assertEquals(4.789699374 - 2 * Math.PI, elevenNegOneFourtyTwoVect.angle(), Vector2D.TOL);
    }

    //Divide Tests
    @Test
    public void divideTest()
    {
        assertEquals(new Vector2D(5, 1), tenTwoVect.divide(2.0));

        assertEquals(new Vector2D(6, -2), twelveNegFourVect.divide(2.0));
    }

    @Test
    public void divideZerosTest()
    {
        Vector2D five = new Vector2D(fiveFiveVect);
        try
        {
            assertEquals(Vector2D.zero, five.divide(0.0));
            fail("This should throw IllegalArguementException");
        }
        catch (IllegalArgumentException e)
        {//Good}
        }
    }

    //Divide equals tests
    @Test
    public void divideEqualsTest()
    {
        Vector2D swap = new Vector2D(tenTwoVect);
        swap.divideEquals(2);
        assertEquals(new Vector2D(5, 1), swap);
        try
        {
            swap = new Vector2D(Vector2D.zero);
            swap.divideEquals(0);
            fail("Should have thrown Exeeption");
        }
        catch (IllegalArgumentException e)
        {
            //Good
        }
    }

//Dot product tests
    @Test
    public void dotSimple()
    {
        assertEquals(25.0, zeroFiveVect.dot(zeroFiveVect), 0.0);
        assertEquals(25.0, zeroFiveVect.dot(fiveFiveVect), 0.0);
        assertEquals(0, negFiveFiveVect.dot(fiveFiveVect), 0.0);
        assertEquals(0, new Vector2D().dot(new Vector2D()), 0.0);
        assertEquals(0, twelveNegFourVect.dot(new Vector2D()), 0.0);
        assertEquals(10, negFiveFiveVect.dot(new Vector2D(oneThreeVect)), 0.0);
    }

    @Test
    public void negativeDot()
    {
        assertEquals(-70, new Vector2D(-100, 10).dot(new Vector2D(oneThreeVect)), 0.0);
    }

    //Equals Tests 
    @Test
    public void equalsTrivial()
    {
        assertEquals(true, new Vector2D().equals(new Vector2D()));
        assertEquals(true, zeroFiveVect.equals(new Vector2D(0, 5)));
        assertEquals(true, (new Vector2D(0, 5).equals(zeroFiveVect)));
    }

    //get Left Ortho Tests
    @Test
    public void simpleGetLeftOrtho()
    {
        //Test the four directions 
        assertEquals(fiveZeroVect, zeroNegFiveVect.getLeftOrtho());
        assertEquals(zeroFiveVect, fiveZeroVect.getLeftOrtho());
        assertEquals(negFiveZeroVect, zeroFiveVect.getLeftOrtho());
        assertEquals(fiveZeroVect, zeroNegFiveVect.getLeftOrtho());
    }

    @Test
    public void leftOrthoSpotTestQ1()
    {
        assertEquals(2.819842099, oneThreeVect.getLeftOrtho().angle(), Vector2D.TOL);
        assertEquals(1.76819188, tenTwoVect.getLeftOrtho().angle(), Vector2D.TOL);
    }

    @Test
    public void leftOrthoSpotTestQ2()
    {
        assertEquals(4.331882603 - 2 * Math.PI, negFiveTwoVect.getLeftOrtho().angle(), Vector2D.TOL);
        assertEquals(4.39063842 - 2 * Math.PI, negSixTwoVect.getLeftOrtho().angle(), Vector2D.TOL);
    }

    @Test
    public void leftOrthoSpotTestQ3()
    {
        assertEquals(4.82304620 - 2 * Math.PI, negNineNegOne.getLeftOrtho().angle(), Vector2D.TOL);
        assertEquals(5.30039158393 - 2 * Math.PI, negThreeNegTwo.getLeftOrtho().angle(), Vector2D.TOL);
    }

    @Test
    public void leftOrthoSpotTestQ4()
    {
        assertEquals(1.249045772, twelveNegFourVect.getLeftOrtho().angle(), Vector2D.TOL);
        assertEquals(0.07731039, elevenNegOneFourtyTwoVect.getLeftOrtho().angle(), Vector2D.TOL);
    }

    //get right Ortho Tests
    @Test
    public void simpleGetrightOrtho()
    {
        //Test the four directions 
        //Q1
        assertEquals(zeroNegFiveVect, fiveZeroVect.getRightOrtho());

        //Q2
        assertEquals(fiveZeroVect, zeroFiveVect.getRightOrtho());

        //Q3
        assertEquals(zeroFiveVect, negFiveZeroVect.getRightOrtho());

        //Q4
        assertEquals(negFiveZeroVect, zeroNegFiveVect.getRightOrtho());
    }

    @Test
    public void rightOrthoSpotTestQ1()
    {
        assertEquals(5.961434752 - 2 * Math.PI, oneThreeVect.getRightOrtho().angle(), Vector2D.TOL);
        assertEquals(new Vector2D(3, -1), oneThreeVect.getRightOrtho());

        assertEquals(4.909784540 - 2 * Math.PI, tenTwoVect.getRightOrtho().angle(), Vector2D.TOL);
    }

    @Test
    public void rightOrthoSpotTestQ2()
    {
        assertEquals(1.1902899496, negFiveTwoVect.getRightOrtho().angle(), Vector2D.TOL);
        assertEquals(new Vector2D(2, 5), negFiveTwoVect.getRightOrtho());

        assertEquals(1.249045772, negSixTwoVect.getRightOrtho().angle(), Vector2D.TOL);
    }

    @Test
    public void rightOrthoSpotTestQ3()
    {
        assertEquals(1.68145354, negNineNegOne.getRightOrtho().angle(), Vector2D.TOL);
        assertEquals(2.15879893, negThreeNegTwo.getRightOrtho().angle(), Vector2D.TOL);
    }

    @Test
    public void rightOrthoSpotTestQ4()
    {
        assertEquals(4.39063842 - 2 * Math.PI, twelveNegFourVect.getRightOrtho().angle(), Vector2D.TOL);
        assertEquals(3.21890304 - 2 * Math.PI, elevenNegOneFourtyTwoVect.getRightOrtho().angle(), Vector2D.TOL);
    }

    @Test
    public void LeftRightReverse()
    {
        assertEquals(true, fiveZeroVect.getRightOrtho().getLeftOrtho().equals(fiveZeroVect));
        assertEquals(true, zeroFiveVect.getRightOrtho().getLeftOrtho().equals(zeroFiveVect));
        assertEquals(true, negFiveZeroVect.getRightOrtho().getLeftOrtho().equals(negFiveZeroVect));
        assertEquals(true, zeroNegFiveVect.getRightOrtho().getLeftOrtho().equals(zeroNegFiveVect));

        assertEquals(true, oneThreeVect.getRightOrtho().getLeftOrtho().equals(oneThreeVect));
        assertEquals(true, tenTwoVect.getRightOrtho().getLeftOrtho().equals(tenTwoVect));

        assertEquals(true, negFiveTwoVect.getRightOrtho().getLeftOrtho().equals(negFiveTwoVect));
        assertEquals(true, negSixTwoVect.getRightOrtho().getLeftOrtho().equals(negSixTwoVect));

        assertEquals(true, negNineNegOne.getRightOrtho().getLeftOrtho().equals(negNineNegOne));
        assertEquals(true, negThreeNegTwo.getRightOrtho().getLeftOrtho().equals(negThreeNegTwo));

        assertEquals(true, twelveNegFourVect.getRightOrtho().getLeftOrtho().equals(twelveNegFourVect));
        assertEquals(true, elevenNegOneFourtyTwoVect.getRightOrtho().getLeftOrtho().equals(elevenNegOneFourtyTwoVect));
    }

    //Get Normalized tests 
    @Test
    public void getNormalizedSimple()
    {
        Vector2D normal = elevenNegOneFourtyTwoVect.getNormalized();
        assertEquals(elevenNegOneFourtyTwoVect.angle(), normal.angle(), Vector2D.TOL);
        assertEquals(1.0, normal.magnitude(), Vector2D.TOL);
        assertEquals(elevenNegOneFourtyTwoVect.angle(), normal.angle(), Vector2D.TOL);

        Vector2D swap = new Vector2D(.1, .2).getNormalized();

        normal = swap.getNormalized();
        assertEquals(swap.angle(), normal.angle(), Vector2D.TOL);
        assertEquals(1.0, normal.magnitude(), Vector2D.TOL);
    }

    @Test
    public void getNormalizedZero()
    {
        Vector2D swap = new Vector2D(Vector2D.zero);

        assertEquals(Vector2D.zero, swap.getNormalized());
    }

    //Get Magnitude tests
    @Test
    public void getMagnitude()
    {
        Vector2D swap = new Vector2D(.2, .3);

        assertEquals(.3605551275, swap.magnitude(), Vector2D.TOL);

        swap = new Vector2D(1, 24);
        assertEquals(Math.sqrt(577), swap.magnitude(), Vector2D.TOL);
    }

    //Magnatude Square tests
    @Test
    public void getMagnitudeSquare()
    {
        Vector2D swap = new Vector2D(.2, .3);
        assertEquals(0.13, swap.magnitudeSq(), Vector2D.TOL);

        assertEquals(Math.pow(100 * Math.sqrt(2), 2), new Vector2D(100, 100).magnitudeSq(), Vector2D.TOL);
    }

    //Minus tests 
    @Test
    public void minus()
    {
        Vector2D ten_vect = new Vector2D(10, 10);
        Vector2D five_vect = new Vector2D(5, 5);
        Vector2D swap = ten_vect.minus(five_vect);

        assertEquals(true, ten_vect.minus(ten_vect).equals(new Vector2D()));
        assertEquals(true, swap.equals(five_vect));
        assertEquals(-16.0, negSixTwoVect.minus(ten_vect).getX(), 0.0);
        assertEquals(-8.0, negSixTwoVect.minus(ten_vect).getY(), 0.0);
    }

    //a - b != b - a
    @Test
    public void transativityTest()
    {
        Vector2D ten_vect = new Vector2D(10, 10);
        Vector2D fiveVectminTenVect = fiveFiveVect.minus(ten_vect);
        Vector2D tenVectminFiveVect = ten_vect.minus(fiveFiveVect);
        assertEquals(false, fiveVectminTenVect.equals(tenVectminFiveVect));
    }

    //Minus Equals test 
    @Test
    public void minusEqualsTest()
    {
        Vector2D newVect = new Vector2D(fiveFiveVect);
        newVect.minusEquals(negThreeNegTwo);
        assertEquals(new Vector2D(8, 7), newVect);
    }

    //negate tests 
    @Test
    public void negateTest()
    {
        Vector2D swap = new Vector2D(negFivenegFiveVect);
        swap.negate();
        assertEquals(true, fiveFiveVect.equals(swap));

        swap = new Vector2D(negSixTwoVect);
        swap.negate();
        assertEquals(true, new Vector2D(6, -2).equals(swap));
    }

    @Test
    public void negateWithZeros()
    {
        Vector2D swap = new Vector2D(zeroFiveVect);
        swap.negate();
        assertEquals(zeroNegFiveVect, swap);
    }

    @Test
    public void negateZeroTest()
    {
        Vector2D temp = Vector2D.zero;
        temp.negate();
        assertEquals(true, temp.equals(Vector2D.zero));
    }

    //Addition tests
    @Test
    public void additionSimple()
    {
        assertEquals(new Vector2D(13, -3), twelveNegFourVect.plus(new Vector2D(1, 1)));
        assertEquals(new Vector2D(11, -5), twelveNegFourVect.plus(new Vector2D(-1, -1)));
        assertEquals(new Vector2D(-1, -1), Vector2D.zero.plus(new Vector2D(-1, -1)));
        assertEquals(new Vector2D(0, 0), Vector2D.zero.plus(new Vector2D(0, 0)));
        assertEquals(new Vector2D(1, -1), Vector2D.zero.plus(new Vector2D(1, -1)));
    }

    @Test
    public void additionMultiple()
    {
        Vector2D swap = new Vector2D(100, 100);
        for (int i = 0; i < 101; i++)
        {
            assertEquals(new Vector2D(100 + i, 100 - i), swap.plus(new Vector2D(i, -i)));
        }
    }

    //Addition equals tests
    @Test
    public void addEqualsSimple()
    {
        Vector2D swap = new Vector2D(oneThreeVect);
        swap.plusEquals(new Vector2D(10, 10));
        assertEquals(new Vector2D(11, 13), swap);

        swap.plusEquals(new Vector2D(-100, -121));

        assertEquals(new Vector2D(-89, -108), swap);
    }

    //Random vector fixed magnitude test
    @Test
    public void randomFixedMagTestSimple()
    {
        assertEquals(20.9, Vector2D.randomVectorFixedMagnitude(20.9).magnitude(), Vector2D.TOL);
        assertEquals(Vector2D.zero, Vector2D.randomVectorFixedMagnitude(0));
    }

    //Random vector max magnitude 
    //This is diffucult to test, but whatever
    @Test
    public void randomMaxMagTestSimple()
    {
        for (int i = 0; i < 100; i++)
        {
            Vector2D vect = Vector2D.randomVectorMaxMagnitude(20.0);

            assertEquals(true, (vect.magnitude() < 20));
        }

        assertEquals(Vector2D.zero, Vector2D.randomVectorMaxMagnitude(0));
    }

    //Reflection tests
    @Test
    public void reflectionSimple()
    {
        Vector2D vect = new Vector2D(fiveNegfiveVect);
        vect.reflectX();
        vect.reflectY();
        assertEquals(-5.0, vect.getX(), 0.0);
        assertEquals(5.0, vect.getY(), 0.0);
        vect.reflectX();
        vect.reflectY();
        assertEquals(5.0, vect.getX(), 0.0);
        assertEquals(-5.0, vect.getY(), 0.0);
    }

    @Test
    public void reflectZero()
    {
        Vector2D vect = new Vector2D();
        vect.reflectX();
        vect.reflectY();
        assertEquals(Vector2D.zero, vect);
    }

    ///////////////////////////////////////////////////
    //Scale tests
    @Test
    public void testScalePlus()
    {
        //result = c + b *a 
        Vector2D res = new Vector2D(6.0, 12.0);
        Vector2D temp = new Vector2D(2.0, 3.0);

        assertEquals(new Vector2D(10.0, 18.0), res.scalePlus(2.0, temp));

        assertEquals(res, res.scalePlus(0.0, temp));
        assertEquals(Vector2D.zero, Vector2D.zero.scalePlus(0.0, new Vector2D(0, 0)));
    }

    //Scale equals test
    @Test
    public void testScaleEqual()
    {
        //result = c + b *a 
        Vector2D res = new Vector2D(6.0, 12.0);
        Vector2D old_res = new Vector2D(res);
        Vector2D duck = new Vector2D(2.0, 3.0);

        res.scalePlusEquals(2.0, duck);

        assertEquals(new Vector2D(10.0, 18.0), res);

        res = new Vector2D(6.0, 12.0);

        //These two shouldn't change  the vector 
        res.scalePlus(0.0, duck);

        assertEquals(old_res, res);

    }

    //Set tests
    @Test
    public void setTest()
    {
        Vector2D vect = new Vector2D(fiveFiveVect);
        vect.set(2, 2);
        assertEquals(new Vector2D(2, 2), vect);

        vect.set(new Vector2D(-1, 1));


        assertEquals(new Vector2D(-1, 1), vect);
    }

    @Test
    public void setOrtho()
    {
        Vector2D vect = new Vector2D(fiveZeroVect);

        vect.setLeftOrtho();
        assertEquals(zeroFiveVect, vect);

        vect.setRightOrtho();
        assertEquals(fiveZeroVect, vect);
    }

    @Test
    public void times_test()
    {

        Vector2D vect = new Vector2D(-2, 4).times(.1);
        assertEquals(new Vector2D(-.2, .4), vect);
        assertEquals(new Vector2D(0.0, 0.0), vect.times(0.0));
        assertEquals(vect, vect.times(1));
    }

    @Test
    public void timesEquals()
    {
        Vector2D vect = new Vector2D(-2, 4);
        vect.timesEquals(1.5);
        assertEquals(-3, vect.getX(), 0.0);
        assertEquals(6, vect.getY(), 0.0);
    }

    @Test
    public void stringTest()
    {
        Vector2D swap = new Vector2D(5, -10);

        assertEquals(0, swap.toString().compareTo("(5.0, -10.0)"));

        swap = new Vector2D(Vector2D.zero);
        assertEquals(0, new Vector2D(0, 0).toString().compareTo("(0.0, 0.0)"));
    }

    @Test
    public void truncTest()
    {
        Vector2D vect = new Vector2D(fiveFiveVect);

        assertEquals(7.0710678118, vect.magnitude(), Vector2D.TOL);

        vect.truncate(8.0);

        assertEquals(7.0710678118, vect.magnitude(), Vector2D.TOL);

        vect.truncate(6.0);

        assertEquals(6.0, vect.magnitude(), Vector2D.TOL);


        vect.truncate(4.2);

        assertEquals(4.2, vect.magnitude(), Vector2D.TOL);


    }

    //Normalize tests
    @Test
    public void normalizeTest()
    {
        Vector2D temp = new Vector2D(negNineNegOne);
        assertEquals(false, temp.magnitude() < 1.0);
        temp.normalize();
        assertEquals(1.0, temp.magnitude(), Vector2D.TOL);
        assertEquals(negNineNegOne.angle(), temp.angle(), Vector2D.TOL);

        temp = new Vector2D(0, 1);
        assertEquals(1.0, temp.magnitude(), Vector2D.TOL);
        temp.normalize();
        assertEquals(1.0, temp.magnitude(), Vector2D.TOL);
        assertEquals(new Vector2D(0, 1).angle(), temp.angle(), Vector2D.TOL);

        temp = new Vector2D(elevenNegOneFourtyTwoVect);
        assertEquals(true, temp.magnitude() > 1.0);
        temp.normalize();
        assertEquals(1.0, temp.magnitude(), Vector2D.TOL);
        assertEquals(elevenNegOneFourtyTwoVect.angle(), temp.angle(), Vector2D.TOL);
    }

    @Test
    public void normalizeZero()
    {
        Vector2D swap = new Vector2D(0, 0);
        swap.normalize();
        assertEquals(Vector2D.zero, swap);
    }

    @Test
    public void doeshashcode()
    {
        int hashCode = fiveFiveVect.hashCode();
        fiveFiveVect.times((double) hashCode);
    }
}