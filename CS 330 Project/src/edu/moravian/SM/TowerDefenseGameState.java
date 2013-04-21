/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.SM;

import edu.moravian.TowerDefenseGame;



/**
 *
 * @author James Moore (moore.work@live.com)
 */
public interface TowerDefenseGameState
{
    public void Enter(TowerDefenseGame ag);

    public void Execute(TowerDefenseGame ag);

    public void Leave(TowerDefenseGame ag);
    
    //TODO temporary testing method 
    
    public void draw(edu.moravian.graphics.WorldGraphics2D par0);
}
