/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author moore
 */
public class TimerTest
{

    public TimerTest()
    {
    }

    @Before
    public void setUp()
    {
    }

    @Test
    public void testTick() throws InterruptedException
    {
        Timer t = new Timer();
        t.tick();
       Thread.sleep(5000);
             
        assertEquals(true, t.getDelta() > 4.9);
    }

}
