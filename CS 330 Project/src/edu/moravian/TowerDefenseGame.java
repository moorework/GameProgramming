package edu.moravian;

import edu.moravian.SM.MainMenu;
import edu.moravian.SM.NullState;
import edu.moravian.SM.RunningState;
import edu.moravian.SM.TD_StateMach;
import edu.moravian.creep.CreepManager;
import edu.moravian.graphics.GraphicsManager;
import edu.moravian.graphics.GraphicsRegistry;
import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import edu.moravian.projectile.BulletManager;
import edu.moravian.tower.BasicTower;
import edu.moravian.tower.TowerManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class encapsulates and holds all of the rules for the game
 *
 * @author moore
 */
public class TowerDefenseGame implements KeyListener, Game, MouseListener
{
    private int worldWidth;
    private int worldHeight;
    private boolean endgame_met;
    private Settings set;
    private BulletManager projMan;
    private CreepManager creepMan;
    private TowerManager towMan;
    private TD_StateMach stateMac;
    private int Xsize;
    
    //temporary
    private GraphicsManager gManager;
    
    /*
     * In general we are trying to delegate logic to others 
     * and keep rules to ourself
     */

    public TowerDefenseGame(int worldWidth, int worldHeight)
    {

        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        endgame_met = false;

        projMan = new BulletManager(null);

        creepMan = new CreepManager(null);

        towMan = new TowerManager();
        towMan.addTower(new BasicTower(creepMan, projMan, new Point2D(500, 500), 500));
        towMan.addTower(new BasicTower(creepMan, projMan, new Point2D(500, 100), 100));

        stateMac = new TD_StateMach(this, towMan, projMan, creepMan);
        stateMac.setGlobalState(new RunningState());
        stateMac.setGameState(new NullState());
        
        // temp
        gManager = new GraphicsManager();
        
        BasicTower t = new BasicTower(creepMan, projMan, new Point2D(100, 200), 20);
        
        GraphicsRegistry.setGraphicsManager(gManager);
        GraphicsRegistry.registerDrawable(t);
    }

    @Override
    public void update(double delt)
    {
        //Delegate the game's behavior to the state machine
        //It holds all the revlevant crap

        stateMac.update();
    }

    @Override
    public void draw(WorldGraphics2D Wg2D)
    {
        
        
        gManager.draw(Wg2D);
        
        
        towMan.draw(Wg2D);

        creepMan.draw(Wg2D);
        
        projMan.draw(Wg2D);

        stateMac.draw(Wg2D);
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

    //TODO may want to redo whole state driven design 
    public BulletManager getProjMan()
    {
        return projMan;
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

    @Override
    public void mouseClicked(MouseEvent me)
    {
        stateMac.mouseClicked(me);
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


}
