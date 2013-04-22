/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.SM;

import edu.moravian.TowerDefenseGame;
import edu.moravian.math.Point2D;
import java.awt.Color;
import java.awt.Dimension;

/**
 * This is an intentionally boring state to pause the game. 
 * @author moore
 */
public class PauseState  implements TowerDefenseGameState{

    @Override
    public void Enter(TowerDefenseGame ag) {
      
    }

    @Override
    public void Execute(TowerDefenseGame ag) {
       
    }

    @Override
    public void Leave(TowerDefenseGame ag) {
      
    }

    @Override
    public void draw(edu.moravian.graphics.WorldGraphics2D par0)
    { par0.fillRect(new Point2D(0,par0.getTrans().getScreenHeight()),new Dimension(10000,10000), new Color(10, 10, 10, 100));   
        //Draw something fun to look at 
    }
    
}
