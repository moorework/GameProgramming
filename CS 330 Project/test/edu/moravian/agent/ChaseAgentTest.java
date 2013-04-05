/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.agent;

import edu.moravian.EntityManager;
import edu.moravian.Settings;
import edu.moravian.math.Point2D;
import java.awt.Rectangle;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Shape;

/**
 *
 * @author moore
 */
public class ChaseAgentTest
  {

    @Test
    public void touching_test()
      {
        ChaseAgent ag = new ChaseAgent(new Point2D(100, 100));
        assertEquals(true, ag.touching(new Rectangle(0, 1, 1000, 100)));
        assertEquals(false, ag.touching(new Rectangle(0, 1, 10, 10)));
        assertEquals(false, ag.touching(new Rectangle(0, 0, 100, 100)));
        assertEquals(true, ag.touching(new Rectangle(0, 0, 101, 101)));

      }

    @Test
    public void testDraw() throws InterruptedException
      {
        PlayerAgent player = PlayerAgent.instance();
        player.setPlayer_position(new Point2D(10000, 10000));
        //EntityManager.instance.addFood(new FoodEntity(new Point2D(0,0), foodvalue));
        ChaseAgent chase = new ChaseAgent();

        
        chase.update();
      

        assertEquals(true, chase.getPosition().getX() > Settings.getInstance().getSafePoint().getX());
        assertEquals(true, chase.getPosition().getY() > Settings.getInstance().getSafePoint().getY());
        
        //Thread.sleep((long) Settings.getInstance().getDefaultChaseEnergyLevel() * 1001);
        
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
    public void testTouching()
      {
      }

    @Test
    public void testReset()
      {
      }

    @Test
    public void testChase()
      {
      }

    @Test
    public void testGetBehaviorManager()
      {
      }

    @Test
    public void testSetApearance()
      {
      }

    @Test
    public void testResetAppearance()
      {
      }

    @Test
    public void testGetPosition()
      {
      }

    @Test
    public void testGetDirection()
      {
      }
  }
