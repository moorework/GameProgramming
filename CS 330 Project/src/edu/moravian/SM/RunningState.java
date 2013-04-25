/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.SM;

import edu.moravian.TowerDefenseGame;
import edu.moravian.creep.CreepManager;
import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.projectile.BulletManager;
import edu.moravian.tower.TowerManager;

/**
 * This state is the main running state in the game.  This is a global state,
 * game states shall layer on top of this to modify the behavior. 
 * @author moore
 */
public class RunningState implements TowerDefenseGameState {

    TowerManager towMan;
    CreepManager creMan;

    @Override
    public void Enter(TowerDefenseGame ag) {
        towMan = ag.getTowMan();
        creMan = ag.getCreepMan();
        
    }

    @Override
    public void Execute(TowerDefenseGame ag) {
        //TODO this needs to be delta
        towMan.update(10);
        creMan.update(10);
    }

    @Override
    public void Leave(TowerDefenseGame ag) {
        
    }

    @Override
    public void draw(WorldGraphics2D par0)
    {

        
              //par0.fillRect(new Point2D(0,par0.getTrans().getScreenHeight()),new Dimension(1000,10000), new Color(10, 10, 10, 100));
    }
}
