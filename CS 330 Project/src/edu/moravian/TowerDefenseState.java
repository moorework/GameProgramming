
package edu.moravian;

import edu.moravian.graphics.WorldGraphics2D;

/**
 *
 * @author myles
 */
public abstract class TowerDefenseState implements Game {
    private TowerDefenseGame master;

    public TowerDefenseState(TowerDefenseGame master) {
        this.master = master;
    }
    
    /**
     * Update the Game state by one frame
     */
    @Override
    public abstract void update(double delta);

    /**
     * Draw the current state of the game
     *
     * @param g2d the graphics with which to draw
     */
    @Override
    public abstract void draw(WorldGraphics2D wG2d);

    /**
     * Determine whether the game is complete (the program should
     * terminate)
     * @return true if the game is complete
     */
    @Override
    public abstract boolean done();
}
