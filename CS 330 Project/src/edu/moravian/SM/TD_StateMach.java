package edu.moravian.SM;

import edu.moravian.TowerDefenseGame;
import edu.moravian.creep.CreepManager;
import edu.moravian.projectile.BulletManager;
import edu.moravian.projectile.Projectile;
import edu.moravian.tower.TowerManager;
import edu.moravian.util.Timer;

/**
 * @author James Moore (moore.work@live.com)
 */
public class TD_StateMach {

    private TowerDefenseGame game;
    private TowerDefenseGameState globalState;
    private TowerDefenseGameState gameState;
    private TowerManager towMan;
    private BulletManager bulMan;
    private CreepManager creMan;
    private boolean pause;
    private RunningState mainState;

    public TD_StateMach(TowerDefenseGame game_in, TowerManager tm, BulletManager pm, CreepManager cm) {
        game = game_in;
        towMan = tm;
        bulMan = pm;
        creMan = cm;
        gameState = new NullState();
        globalState = new NullState();
        pause = false;
        mainState = new RunningState();
    }

    public void setGameState(TowerDefenseGameState new_state) {
        gameState.Leave(game);
        gameState = new_state;
        gameState.Enter(game);
    }

    public void update() {
        /*
         * The problem we face here is that changing states is gonna get really 
         * messy if we keep it here 
         */
        globalState.Execute(game);
        gameState.Execute(game);

    }

    public void RevertToPreviousState() {
        //TODO may want queue of previous states 
    }

    public void setGlobalState(TowerDefenseGameState state_in) {
        globalState.Leave(game);
        globalState = state_in;
        globalState.Enter(game);
    }

    public void pause() {
        if(pause){
            mainState = (RunningState) globalState;
            this.setGlobalState(new NullState());
        } else
        {
            this.setGlobalState(mainState);
        }
        
        pause  = !pause;
    }

    /**
     * This allows the easy creation of managers for time based game metrics.
     *
     */
    private class TimeBasedMeter {

        Timer time;
        private double offset;
        private double cutoff;

        public TimeBasedMeter(double cutoff) {
            this.cutoff = cutoff;
            offset = 0;
            time = new Timer();
        }

        public void pause() {
            offset += time.getDelta();
        }

        public void unPause() {
            time.tick();
        }

        public boolean isDueForReset() {
            return (time.getDelta() + offset) > cutoff;
        }

        public void reset() {
            time.tick();
            offset = 0;
        }

        public void reset(int scale) {
            time.tick();
            offset = 0;
            offset += scale;

        }

        public double getOffset() {
            return offset;
        }
    }
}