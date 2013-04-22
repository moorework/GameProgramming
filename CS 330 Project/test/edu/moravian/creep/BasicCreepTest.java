/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.creep;

import edu.moravian.Settings;
import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import edu.moravian.projectile.BasicBullet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author moore
 */
public class BasicCreepTest {

    BasicCreep cr;

    public BasicCreepTest() {
    }

    @Before
    public void setUp() {
        cr = new BasicCreep(new Point2D(1, 1), new Point2D(100, 100));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testGet_dims() {
    }

    @Test
    public void testGetPosition() {
        assertEquals(new Point2D(1, 1), cr.getPos());
        assertEquals(new Point2D(1, 1), cr.getPosition());
    }

    @Test
    public void testGetDirection() {
        assertEquals(true, cr.getDirection().dot(new Vector2D(1, 1)) > 0);
    }

    @Test
    public void testRespondToColission() {

        cr = new BasicCreep(new Point2D(1, 1), new Point2D(1, 1));


        int healthBeg = cr.healthRemaining();

        if (healthBeg <= 1) {
            fail("This creep should have more than 1 health to begin with");
        }

        assertFalse(cr.isDead());


        cr.respondToColission(new BasicBullet(cr, healthBeg, 1, Point2D.zero, 10));

        assertFalse(cr.isDead());

        assertEquals(Settings.getInstance().getCreepLowestHealth(), cr.healthRemaining());

        cr.respondToColission(new BasicBullet(cr, healthBeg - 1, 10, Point2D.zero, 10));

        assertTrue(cr.isDead());
    }

    @Test
    public void actuallEffectiveTest() {
        cr = new BasicCreep(Point2D.zero, new Point2D(10, 0));
        assertFalse(cr.respondToColission(new BasicBullet(cr, 100000, 1, new Point2D(110, 0), 10)));
        assertFalse(cr.isDead());

        cr.update(1);

        System.out.println(cr.getPosition());
        assertTrue(cr.respondToColission(new BasicBullet(cr, 100000, 1, new Point2D(110, 0), 10)));

    }
}
