package edu.moravian.util;

/**
 * This class represents a basic timer designed to provide timing
 * data for applications supporting a GUI.
 * 
 * @author barros
 */
public class DeltaTimer {
    private long beginTime;
    private long frameLength;
    
    // a second measured in milliseconds
    private final double SECOND = 1000.0;
    
    /**
     * Create a Timer that has not yet begun timing.
     */
    public DeltaTimer() {
        tick();
    }
    
    /**
     * Begin timing from the moment that this method is called.
     */
    public void tick() {
        // save the previous start time for use in computing our frameLength
        long initialTime = beginTime;
        // begin timing from the moment tick() is called
        beginTime = System.currentTimeMillis();
        
        // the length of a frame is the gap between timing sessions
        frameLength = beginTime - initialTime;
    }
    
    /**
     * Retrieves a double representing the number of frames capable of being
     * drawn in a second using the time between timing sessions as a measure of
     * the length of a single frame.
     * 
     * @return number of frames drawable per second
     */
    public double getFPS() {
        return SECOND / frameLength;
    }
    
    /**
     * Retrieve the span of time to compute a frame in seconds.
     * 
     * @return the span of time to compute a frame in seconds
     */
    public double getDelta() {
        
        return frameLength / SECOND;
    }
}
