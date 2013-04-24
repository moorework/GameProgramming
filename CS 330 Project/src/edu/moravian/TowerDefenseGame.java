package edu.moravian;

import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//TODO make sure the coordinates are world coordinates 
/**
 * This class encapsulates and holds all of the rules for the game
 *
 * @author moore
 */
public class TowerDefenseGame implements KeyListener, Game
  {

    private int worldWidth;
    private int worldHeight;
    private boolean endgame_met;
    
    TowerDefenseState currState;
      /*
         * In general we are trying to delegate logic to others 
         * and keep rules to ourself
         */
    public TowerDefenseGame(int worldWidth, int worldHeight)
      {

        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        endgame_met = false;
        
        // TODO first state
      }

    @Override
    public void update(double delta)
      {
          currState.update(delta);
      }
    
    public void setState(TowerDefenseState newState) {
        currState = newState;
    }

    @Override
    public void draw(WorldGraphics2D Wg2D)
      {
          
        Wg2D.fillRect(new Point2D(0, 0), worldWidth, worldHeight);

      }

    @Override
    public boolean done()
      {
        return endgame_met;
      }

    @Override
    public void keyTyped(KeyEvent ke)
      {
      }

    /**
     * This is where we handle input. If any future keyboard input needs to be
     * added, here is the place.
     *
     * @param ke
     */
    @Override
    public void keyPressed(KeyEvent ke)
      {
        switch (ke.getKeyCode())
          {
            case KeyEvent.VK_SPACE:
                endgame_met = true;
          }
      }

    @Override
    public void keyReleased(KeyEvent ke)
      {
      }
  }
