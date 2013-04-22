
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
    
    public int getCreepLowestHealth(){return 0;}
    
  }
