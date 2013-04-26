
package edu.moravian;

import edu.moravian.graphics.GraphicsManager;
import edu.moravian.graphics.GraphicsRegistry;
import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import edu.moravian.tower.Tower;
import edu.moravian.util.CoordinateTranslator;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author myles
 */
public class Controller implements KeyListener, MouseListener {
    private UserInterfaceController uiController;
    private TowerDefenseGame tdGame;
    private CoordinateTranslator coordTrans;
    
    private Tower currentlySelectedTower;
    private boolean towerCurrentlySelected;
    
    private boolean aboutToBuildTower;
    
    private int DEFAULT_TOWER_RADIUS = 200;
    
    public Controller(UserInterfaceController uic, TowerDefenseGame towerDefenseGame, CoordinateTranslator ct) {
        uiController = uic;
        tdGame = towerDefenseGame;
        
        coordTrans = ct;
        
        currentlySelectedTower = null;
        towerCurrentlySelected = false;
        
        aboutToBuildTower = false;
    }
    
    public boolean done() {
        return tdGame.done();
    }
    
    public void update(double delta) {
        // draw tower placement stuff
    }
    
    public void draw(WorldGraphics2D g2d) {
        tdGame.draw(g2d);
    }
    
    public boolean gameIsPaused() {
        return false;
    }
    
    public void resetTowerDefense(int width, int height) {
        tdGame = new TowerDefenseGame(width, height);
    }
    
    public void pause() {
        tdGame.getStateMac().pause();
    }

    @Override
    public void keyTyped(KeyEvent ke)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent ke)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent ke)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseClicked(MouseEvent me)
    {
        Point2D clickLocation = coordTrans.screenToWorld(new Point2D(me.getX(), me.getY()));
        
        boolean clickInBuildable = tdGame.pointDescribesBuildableArea(clickLocation);
        
        // if we clicked a unbuildable area...
        if (clickInBuildable == false) {
            currentlySelectedTower = null;
            aboutToBuildTower = false;
        }
        // if we're about to build but we clicked on an already occupied cell
        else if (aboutToBuildTower == true && tdGame.pointIsOccupied(clickLocation) == true) {
            aboutToBuildTower = false;
        }
        // if we clicked on an occupied cell, that tower is now selected
        else if (clickInBuildable && tdGame.pointIsOccupied(clickLocation)) {
            currentlySelectedTower = tdGame.getTowerAtPoint(clickLocation);
            towerCurrentlySelected = true;
        }
        // if we're about to build a tower and the point is not occupied
        else if (aboutToBuildTower == true && tdGame.pointIsOccupied(clickLocation) == false && clickInBuildable) {
            tdGame.addTower(tdGame.getCornerPoint(clickLocation), DEFAULT_TOWER_RADIUS);
            
            aboutToBuildTower = false;
        }
        // the player is an idiot
        else {
            currentlySelectedTower = null;
            towerCurrentlySelected = false;
        }
    }
    
    public Tower getCurrentlySelectedTower() {
        return currentlySelectedTower;
    }

    @Override
    public void mousePressed(MouseEvent me)
    {
        //
    }

    @Override
    public void mouseReleased(MouseEvent me)
    {
        //
    }

    @Override
    public void mouseEntered(MouseEvent me)
    {
        //
    }

    @Override
    public void mouseExited(MouseEvent me)
    {
        //
    }
    
    public void setAboutToBuildTower() {
        aboutToBuildTower = true;
    }
}
