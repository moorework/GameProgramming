package edu.moravian;

import edu.moravian.SM.MainMenu;
import edu.moravian.SM.NullState;
import edu.moravian.SM.RunningState;
import edu.moravian.SM.TD_StateMach;
import edu.moravian.WorldMap.NavGraph;
import edu.moravian.WorldMap.WorldMap;
import edu.moravian.creep.BasicCreep;
import edu.moravian.creep.Creep;
import edu.moravian.creep.CreepManager;
import edu.moravian.creep.Wave;
import edu.moravian.creep.WaveCreator;
import edu.moravian.graphics.GraphicsManager;
import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import edu.moravian.tower.BasicTower;
import edu.moravian.tower.Tower;
import edu.moravian.tower.TowerManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class encapsulates and holds all of the rules for the game
 *
 * @author moore
 */
public class TowerDefenseGame implements KeyListener, Game
{
    private final String WAVE_DIRECTORY = "waves.properties";
    private final String MAP_DIRECTORY = "maps/basicMap.txt";
    
    private int worldWidth;
    private int worldHeight;
    private boolean endgame_met;
    private Settings set;
    private CreepManager creepMan;
    private TowerManager towMan;
    private TD_StateMach stateMac;
    
    private WorldMap worldMap;
    
    private WaveCreator waveCreator;
    private Wave currWave;
    
    /*
     * In general we are trying to delegate logic to others 
     * and keep rules to ourself
     */

    public TowerDefenseGame(int worldWidth, int worldHeight)
    {
        
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        
        worldMap = new WorldMap(MAP_DIRECTORY, worldWidth, worldHeight);
        
        waveCreator = new WaveCreator(WAVE_DIRECTORY);
        currWave = waveCreator.getNextWave();

        endgame_met = false;
        
        creepMan = currWave.getCreepManager(worldMap);

        towMan = new TowerManager();

        stateMac = new TD_StateMach(this);
        stateMac.setGlobalState(new RunningState());
        stateMac.setGameState(new NullState());

        
    }

    @Override
    public void update(double delt)
    {
        //Delegate the game's behavior to the state machine
        //It holds all the revlevant crap
        
        towMan.update(delt);
        creepMan.update(delt);
        
        stateMac.update();
    }

    @Override
    public void draw(WorldGraphics2D Wg2D)
    {
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
            case KeyEvent.VK_Q:
                endgame_met = true;
                break;
            case KeyEvent.VK_SPACE:

                stateMac.pause();
                break;
            case KeyEvent.VK_M:
                stateMac.setGlobalState(new MainMenu());
                break;


            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke)
    {
    }

    public CreepManager getCreepMan()
    {
        return creepMan;
    }

    public TowerManager getTowMan()
    {
        return towMan;
    }

    public TD_StateMach getStateMac()
    {
        return stateMac;
    }

    public boolean pointDescribesBuildableArea(Point2D point) {
        return worldMap.canBeOccupied(point);
    }
    
    public Point2D getCornerPoint(Point2D point) {
        return worldMap.getCornerPoint(point);
    }
    
    public boolean pointIsOccupied(Point2D point) 
    {
        return worldMap.isOccupied(point);
    }
    
    public Tower getTowerAtPoint(Point2D point) {
        return worldMap.getTowerAtPoint(point);
    }

    public void setOccupied(Point2D point, Tower t) {
        worldMap.setOccupied(point, t);
<<<<<<< HEAD
    }
    
    public void addTower(Point2D topLeftCorner, int towerRadius) {
        // TODO magic number - given our current project structure, there is no place for this
        Point2D towerLocation = new Point2D(topLeftCorner.getX() + towerRadius / 2, topLeftCorner.getY() + towerRadius / 2);
        Tower newTower = new BasicTower(creepMan, topLeftCorner, 200);
        
        towMan.addTower(newTower);
        
        worldMap.setOccupied(towerLocation, newTower);
    }
=======
    }
    
    public void addTower(Point2D topLeftCorner, int towerRadius) {
        System.out.println("Being called");
        // TODO magic number - given our current project structure, there is no place for this
        Point2D towerLocation = new Point2D(topLeftCorner.getX() + towerRadius / 2, topLeftCorner.getY() + towerRadius / 2);
        Tower newTower = new BasicTower(creepMan, topLeftCorner, 200);
        
        towMan.addTower(newTower);

        worldMap.setOccupied(towerLocation, newTower);
    }
>>>>>>> e95bf10e4d8e648f063bddc74ce56a4581d58b10
}
