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
public class BasicCreepTest
{

    BasicCreep cr;
    
    public BasicCreepTest()
    {
    }
    
    @Before
    public void setUp()
    {
        cr = new BasicCreep(new Point2D(1, 1), new Point2D(100, 100));
    }
    
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void testUpdate()
    {
    }
    
    @Test
    public void testGet_dims()
    {
    }
    
    @Test
    public void testGetPosition()
    {
        assertEquals(new Point2D(1, 1), cr.getPos());
        assertEquals(new Point2D(1, 1), cr.getPosition());
    }
    
    @Test
    public void testGetDirection()
    {
        assertEquals(true, cr.getDirection().dot(new Vector2D(1, 1)) > 0);
    }
    
    @Test
    public void testRespondToColission()
    {
        
        cr = new BasicCreep(new Point2D(1, 1), new Point2D(100, 100));
        
        
        int healthBeg = cr.healthRemaining();

        if (healthBeg <= 1)
        {
            fail("This creep should have more than 1 health to begin with");
        }
        
        assertFalse(cr.isDead());
        
        
        cr.respondToColission(new BasicBullet(cr, healthBeg, 1, Point2D.zero, 1000));
        
        assertFalse(cr.isDead());
        
        assertEquals(Settings.getInstance().getCreepLowestHealth(), cr.healthRemaining());
        
        cr.respondToColission(new BasicBullet(cr, healthBeg - 1, 10000, Point2D.zero, 1000));
        
        assertTrue(cr.isDead());
    }

}
