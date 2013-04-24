

package edu.moravian;

import edu.moravian.WorldMap.WorldMap;
import edu.moravian.creep.CreepManager;
import edu.moravian.creep.Wave;
import edu.moravian.creep.WaveCreator;
import edu.moravian.graphics.GraphicsRegistry;
import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import edu.moravian.tower.TowerManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;


/**
 *
 * @author myles
 */
public class TowerDefensePlayingState extends TowerDefenseState {

    private final String MAP_FILE = "map.txt";
    private final String WAVE_FILE = "waves.properties";

    private int worldWidth;
    private int worldHeight;
    
    private boolean endgame_met;
    
    private Color background;
    private Settings set;
    
    private Player player;
   
    private WorldMap worldMap;
    private WaveCreator waveCreator;
    
    private CreepManager creepManager;
    private TowerManager towerManager;
      /*
         * In general we are trying to delegate logic to others 
         * and keep rules to ourself
         */
    public TowerDefensePlayingState(int worldWidth, int worldHeight, TowerDefenseGame master)
      {
        super(master);
          
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        
        GraphicsRegistry.clearRegistry();
        
        worldMap = new WorldMap(MAP_FILE, worldWidth, worldHeight);
        GraphicsRegistry.registerDrawable(worldMap);
        waveCreator = new WaveCreator(WAVE_FILE);

        set = Settings.getInstance();
        set.setWorldSize(new Dimension(worldWidth, worldHeight));

        endgame_met = false;

        background = set.getBackgroundColor();

        towerManager = new TowerManager();
        
        Wave firstWave = waveCreator.getNextWave();
        creepManager = firstWave.getCreepManager(worldMap);
      }

    @Override
    public void update(double delta)
      {
        towerManager.update(delta);
        creepManager.update(delta);
        
        updateWave();
      }
    
    private void updateWave() {
        boolean needNewWave = false;
        
        if (creepManager.getNumCreeps() == 0) {
            needNewWave = true;
        }
        
        Wave newWave;
        if (waveCreator.hasMoreWaves() == true && needNewWave) {
            newWave = waveCreator.getNextWave();
            
            creepManager = newWave.getCreepManager(worldMap);
        }
    }
    
    private void updateState() {
        // the player has won the game!
        if (player.numLives() > 0 && waveCreator.hasMoreWaves() == false) {
            // they win!
        }
        else if (player.numLives() < 1) {
            master.setState(new TowerDefenseGameOverState(master));
        }
    }

    @Override
    public void draw(WorldGraphics2D Wg2D)
      {
        Wg2D.setColor(background);
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
        switch (ke.getKeyCode()) {
        case KeyEvent.VK_Q:
            endgame_met = true;
            break;
    }
    }

    @Override
    public void keyPressed(KeyEvent ke)
    {
        // like I give a damn
    }

    @Override
    public void keyReleased(KeyEvent ke)
    {
        // like I give a damn
    }
    
     private class Player {
         private int lives;
         private int money = 0;
         
         public Player(int initLives, int initMoney) {
             lives = initLives;
             
             money = initMoney;
         }
         
         public void decrementLives() {
             lives--;
         }
         
         public void decrementLives(int numLost) {
             lives = lives - numLost;
         }
         
         public void addMoney(int freshMoney) {
             money = money + freshMoney;
         }
         
         public void deductMoney(int lessMoney) {
             money = money - lessMoney;
         }
         
         public int numLives() {
             return lives;
         }
         
         public int cashOnHand() {
             return money;
         }
     }
}
