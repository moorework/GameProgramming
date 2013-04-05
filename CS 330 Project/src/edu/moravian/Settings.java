
package edu.moravian;

import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import java.awt.Color;
import java.awt.Dimension;

/**
 * Singleton settings class
 * @author moore
 */
public class Settings
  {
    /*
     * Setting to demonstrate sleep food loop 
     * 3 food reserves 
     * 7 chase energy 
     */

    private static Settings instance = null;
    private boolean debug;
    private Dimension res;
    private Dimension worldSize;

    protected Settings()
      {
        // Exists only to defeat instantiation.
        debug = false;
      }

    public static Settings getInstance()
      {
        if (instance == null)
          {
            instance = new Settings();
          }
        return instance;
      }

    //The number of lives that a player has in the game
    public int getLives()
      {
        return 5;
      }

    public Color getBackgroundColor()
      {
        return Color.gray;
      }

    public double getDesiredFPS()
      {
        return 60;
      }

    /**
     *
     * @return The default chase time in seconds
     */
    public double getDefaultChaseEnergyLevel()
      {
        return 7;
      }

    /**
     *
     * @return returns the default food reserves in seconds
     */
    public double getDefaultFoodReserves()
      {
        return 10;
      }

    //This is just a safe point that is always inside the world
    public Point2D getSafePoint()
      {
        return new Point2D(100, 100);
      }

    public double getChaseSpeed()
      {
        return 1;
      }

    public Point2D getPlayerPosInit()
      {
        return new Point2D(200, 0);
      }

    public Vector2D getPlayerMovementDown()
      {
        return new Vector2D(0, 30);
      }

    public Vector2D getPlayerMovementUp()
      {
        return new Vector2D(0, -30);
      }

    public Vector2D getPlayerMovementRight()
      {
        return new Vector2D(30, 0);
      }

    public Vector2D getPlayerMovementLeft()
      {
        return new Vector2D(-30, 0);
      }

    public double getDefaultEatTime()
      {
        return 4;
      }

    boolean getDebug()
      {
        return false;
      }

    void setDebug()
      {
        debug = !debug;
      }

    public double getDefaultSleepTime()
      {
        return 2;
      }

    public void setResolution(Dimension resolution)
      {
        res =resolution;
      }

    public Dimension getResolution()
      {
        return res;
      }

    public Dimension getWorldSize()
      {
        return worldSize;
      }

    public void setWorldSize(Dimension worldSize)
      {
        this.worldSize = worldSize;
      }

    public double getFullStateRestore()
    {
        return 1;
    }
    
    
  }
