package edu.moravian.util;

import edu.moravian.Settings;

/**
 * Timer class that handles time.  The external unit of this class is seconds.  
 * @author moore
 */
public class Timer
{

    double beginTime;
    double frameLength;

   /**
    * 
    */
    public Timer()
    {
        beginTime = System.currentTimeMillis();
    }

    /**
     * Start new timer cycle.  
     */
    public void tick()
    {
        beginTime = System.currentTimeMillis();
    }

    /**
     * Get the ideal FPS for the game 
     * @return 
     */
    public double getFPS()
    {
        return 1000.0/Settings.getInstance().getDesiredFPS();
    }

   /**
    * Get time since last tick, in seconds 
    * @return  Delta in seconds 
    */
    public double getDelta()
    {
        return (System.currentTimeMillis() - beginTime) / 1000;
    }
}
