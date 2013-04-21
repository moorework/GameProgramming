
package edu.moravian.SM;

import edu.moravian.TowerDefenseGame;
import edu.moravian.math.Point2D;
import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class MainMenu implements TowerDefenseGameState {

    @Override
    public void Enter(TowerDefenseGame ag)
    {
        
    }

    @Override
    public void Execute(TowerDefenseGame ag)
    {
        
    }

    @Override
    public void Leave(TowerDefenseGame ag)
    {
        
    }

    @Override
    public void draw(edu.moravian.graphics.WorldGraphics2D par0)
    {
     par0.fillRect(new Point2D(0,par0.getTrans().getScreenHeight()),new Dimension(1000,10000), new Color(5, 5, 5, 100));   
    }

}
