package edu.moravian.creep;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author myles
 */
public class CreepManager {

    ArrayList<Creep> creepsAlive;
    ArrayList<Creep> creepsDead;

    public CreepManager(ArrayList<Creep> initialCreeps) {
        this.creepsAlive = initialCreeps;
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
        for (Creep c : creepsAlive) {
            //TODO update?

            if (c.isDead()) {
                creepsDead.add(c);
            }
        }
    }

    private void pruneCreeps() {
        creepsAlive.removeAll(creepsDead);
    }
}
