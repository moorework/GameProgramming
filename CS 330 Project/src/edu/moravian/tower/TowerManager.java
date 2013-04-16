package edu.moravian.tower;

import edu.moravian.graphics.WorldGraphics2D;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class TowerManager {

    LinkedList<Tower> towers;

    public TowerManager() {
        towers = new LinkedList<Tower>();
    }

    public void addTower(Tower t) {
        towers.add(t);
    }

    public Iterator<Tower> getTowers() {
        return towers.iterator();
    }
    
    public void update(double delta){
        for(Tower t : towers){
            //TODO is this delta usage correct?
            t.update(delta);
        }
    }
    
    //TODO draw
    public void draw(WorldGraphics2D g2d){
        for(Tower t: towers){
           ((BasicTower)t).draw(g2d);
        }
    }
    
}
