package edu.moravian.SM;

import edu.moravian.TowerDefenseGame;
import edu.moravian.creep.CreepManager;
import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import edu.moravian.projectile.BulletManager;
import edu.moravian.projectile.Projectile;
import edu.moravian.tower.TowerManager;
import edu.moravian.util.Timer;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author James Moore (moore.work@live.com)
 */
public class TD_StateMach implements MouseListener
{

    private TowerDefenseGame game;
    private TowerDefenseGameState globalState;
    private TowerDefenseGameState gameState;
    private boolean pause;
    private RunningState mainState;

/**
 * This class holds all of the information about the game's state.  
 * @param game_in
 * @param tm
 * @param pm
 * @param cm 
 */
    public TD_StateMach(TowerDefenseGame game_in)
    {
        game = game_in;
        gameState = new NullState();
        globalState = new NullState();
        pause = false;
        mainState = new RunningState();
    }

    /**
     * Exit the old state (running it's exit method) and enters the new state.
     *
     * @param new_state
     */
    public void setGameState(TowerDefenseGameState new_state)
    {
        gameState.Leave(game);
        gameState = new_state;
        gameState.Enter(game);
    }

    public void update()
    {
        /*
         * The problem we face here is that changing states is gonna get really 
         * messy if we keep it here 
         */
        globalState.Execute(game);
        gameState.Execute(game);

    }

    /**
     * Exit the old state (running it's exit method) and enters the new state.
     *
     * @param new_state
     */
    public void setGlobalState(TowerDefenseGameState state_in)
    {
        globalState.Leave(game);
        globalState = state_in;
        globalState.Enter(game);
    }

    public void pause()
    {
        if (pause)
        {
            mainState = (RunningState) globalState;
            this.setGlobalState(new PauseState());
        }
        else
        {
            this.setGlobalState(mainState);
        }

        pause = !pause;
    }

    public TowerDefenseGameState getGlobalState()
    {
        return globalState;
    }

    public TowerDefenseGameState getGameState()
    {
        return gameState;
    }

    public void draw(WorldGraphics2D Wg2D)
    {
        globalState.draw(Wg2D);
        gameState.draw(Wg2D);

    }

    @Override
    public void mouseClicked(MouseEvent me)
    {
    }

    @Override
    public void mousePressed(MouseEvent me)
    {
    }

    @Override
    public void mouseReleased(MouseEvent me)
    {
    }

    @Override
    public void mouseEntered(MouseEvent me)
    {
    }

    @Override
    public void mouseExited(MouseEvent me)
    {
    }

    public boolean isPaused()
    {
        return globalState instanceof PauseState;
    }

    /**
     * This allows the easy creation of managers for time based game metrics.
     *
     */
    private class TimeBasedMeter
    {

        Timer time;
        private double offset;
        private double cutoff;

        public TimeBasedMeter(double cutoff)
        {
            this.cutoff = cutoff;
            offset = 0;
            time = new Timer();
        }

        public void pause()
        {
            offset += time.getDelta();
        }

        public void unPause()
        {
            time.tick();
        }

        public boolean isDueForReset()
        {
            return (time.getDelta() + offset) > cutoff;
        }

        public void reset()
        {
            time.tick();
            offset = 0;
        }

        public void reset(int scale)
        {
            time.tick();
            offset = 0;
            offset += scale;

        }

        public double getOffset()
        {
            return offset;
        }
    }
}