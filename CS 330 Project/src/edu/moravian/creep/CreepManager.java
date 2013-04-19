package edu.moravian.creep;

import edu.moravian.graphics.WorldGraphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *  This clas manages the living and dead creeps.  It automatically trims the creeps on their deaths 
 * @author myles
 */
public class CreepManager {

    ArrayList<Creep> creepsAlive;
    ArrayList<Creep> creepsDead;

    public CreepManager(ArrayList<Creep> initialCreeps) {
        if(initialCreeps != null){
        this.creepsAlive = initialCreeps;
        }else{
            creepsAlive = new ArrayList<Creep>();
        }
        this.creepsDead = new ArrayList<Creep>();
    }

    public void addCreep(Creep newCreep) {
        this.creepsAlive.add(newCreep);
    }

    public int getNumCreeps() {
        return creepsAlive.size();
    }

    public Iterable<Creep> getCreeps() {
        return (Iterable<Creep>) creepsAlive.iterator();
    }

    public void update(double delta) {
       
        pruneCreeps();
        
        for (Creep c : creepsAlive) {
            //TODO update?
            c.update(delta);
            if (c.isDead()) {
                creepsDead.add(c);
            }
        }
    }

    private void pruneCreeps() {
        //TODO change this to a state change when appropriate 
        creepsAlive.removeAll(creepsDead);
    }
    
    public void draw (WorldGraphics2D w2d){
       for(Creep cr: creepsAlive){

           ((BasicCreep)cr).draw(w2d);
       }
    }
}
