package edu.moravian;

import edu.moravian.graphics.WorldGraphics2D;

/**
 * This interface defines the required elements for any game that uses
 * the VideoController
 * 
 * @author mebjc01
 */
public interface Game
{
    /**
     * Update the Game state by one frame
     */
    void update();

    /**
     * Draw the current state of the game
     *
     * @param g2d the graphics with which to draw
     */
    void draw(WorldGraphics2D wG2d);

    /**
     * Determine whether the game is complete (the program should
     * terminate)
     * @return true if the game is complete
     */
    boolean done();





}
