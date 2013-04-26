
package edu.moravian;

import edu.moravian.graphics.GraphicsManager;
import edu.moravian.graphics.GraphicsRegistry;
import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import edu.moravian.tower.BasicTower;
import edu.moravian.tower.Tower;
import edu.moravian.util.CoordinateTranslator;
import edu.moravian.util.GraphicsDataParser;
import edu.moravian.util.Timer;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 *
 * @author myles
 */
public class Controller implements KeyListener, MouseListener {
    private UserInterfaceController uiController;
    private TowerDefenseGame tdGame;
    private CoordinateTranslator coordTrans;
    
    private BasicTower currentlySelectedTower;
    private boolean towerCurrentlySelected;
    
    private boolean aboutToBuildTower;
    
    private int DEFAULT_TOWER_RADIUS = 200;
    
    private BufferedImage towerImage;
    
    private Timer timer;
    Point2D drawPoint;
    
    public Controller(UserInterfaceController uic, TowerDefenseGame towerDefenseGame, CoordinateTranslator ct) {
        uiController = uic;
        tdGame = towerDefenseGame;
        
        coordTrans = ct;
        
        currentlySelectedTower = null;
        towerCurrentlySelected = false;
        
        aboutToBuildTower = false;
        
        towerImage = GraphicsDataParser.readInSprite("tower.png").getBackingImage();
        timer = new Timer();
        drawPoint = new Point2D(0,0);
    }
    
    public boolean done() {
        return tdGame.done();
    }
    
    public void update(double delta) {
        tdGame.update(delta);
        
        if(timer.getDelta() > 15){
            System.exit(1);
        }
        
    }
    
    public void draw(WorldGraphics2D g2d) {
        tdGame.draw(g2d);
        
        if (aboutToBuildTower) {
         
          
            g2d.fillOval(new Point2D(drawPoint.getX() - DEFAULT_TOWER_RADIUS / 2, drawPoint.getY() + DEFAULT_TOWER_RADIUS / 2), DEFAULT_TOWER_RADIUS * 2, DEFAULT_TOWER_RADIUS * 2, new Color(215, 74, 74, 180));
            
              g2d.drawImage(towerImage, new Point2D(drawPoint.getX() + towerImage.getWidth() / 2, drawPoint.getY() - towerImage.getHeight() / 2), null);
           
        }
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
        //
    }

    @Override
    public void keyPressed(KeyEvent ke)
    {
        //
    }

    @Override
    public void keyReleased(KeyEvent ke)
    {
        //
    }

    @Override
    public void mouseClicked(MouseEvent me)
    {
        Point2D clickLocation = coordTrans.screenToWorld(new Point2D(me.getX(), me.getY()));
        
        boolean clickInBuildable = tdGame.pointDescribesBuildableArea(clickLocation);
        
        // if we clicked a unbuildable area...
        if (clickInBuildable == false) {
            System.out.println("Not buildable!");
            currentlySelectedTower = null;
            aboutToBuildTower = false;
        }
        // if we're about to build but we clicked on an already occupied cell
        else if (aboutToBuildTower == true && tdGame.pointIsOccupied(clickLocation) == true) {
            aboutToBuildTower = false;
        }
        
        // if we clicked on an occupied cell, that tower is now selected
        else if (aboutToBuildTower == false && clickInBuildable && tdGame.pointIsOccupied(clickLocation)) {
            currentlySelectedTower = (BasicTower) tdGame.getTowerAtPoint(clickLocation);
            towerCurrentlySelected = true;
        }
        // if we're about to build a tower and the point is not occupied
        else if (aboutToBuildTower == true && tdGame.pointIsOccupied(clickLocation) == false && clickInBuildable) {
            System.out.println("Building tower");
            tdGame.addTower(tdGame.getCornerPoint(clickLocation), DEFAULT_TOWER_RADIUS);
            
            aboutToBuildTower = false;
        }
        // the player is an idiot
        else {
            currentlySelectedTower = null;
            towerCurrentlySelected = false;
        }
    }
    
    public BasicTower getCurrentlySelectedTower() {
        return currentlySelectedTower;
    }
    
    public boolean towerCurrentlySelected() {
        return towerCurrentlySelected;
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
    
    public void mouseMoved(Point2D mouse) {
        if (aboutToBuildTower == true) {
            drawPoint = coordTrans.screenToWorld(mouse);
            drawPoint = tdGame.getCornerPoint(coordTrans.screenToWorld(mouse));
            
            // transparent 16 x 16 pixel cursor image
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // create a new blank cursor
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        
        uiController.getContentPane().setCursor(blankCursor);
        }
        else {
            drawPoint = Point2D.zero;
            
            uiController.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
