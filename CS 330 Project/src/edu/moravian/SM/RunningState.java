/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.SM;

import edu.moravian.TowerDefenseGame;
import edu.moravian.creep.CreepManager;
import edu.moravian.projectile.BulletManager;
import edu.moravian.tower.TowerManager;

/**
 *
 * @author moore
 */
public class RunningState implements TowerDefenseGameState {

    TowerManager towMan;
    BulletManager bulMan;
    CreepManager creMan;

    @Override
    public void Enter(TowerDefenseGame ag) {
        towMan = ag.getTowMan();
        bulMan = ag.getProjMan();
        creMan = ag.getCreepMan();
        System.out.println("Running");
    }

    @Override
    public void Execute(TowerDefenseGame ag) {
        towMan.update(10);
        bulMan.update(10);
        creMan.update(10);
    }

    @Override
    public void Leave(TowerDefenseGame ag) {
        System.out.println("Done running for now");
    }
}
