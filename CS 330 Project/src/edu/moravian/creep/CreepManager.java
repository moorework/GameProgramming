package edu.moravian.creep;

import edu.moravian.graphics.WorldGraphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *  This class manages the living and dead creeps.  It automatically trims the creeps on their deaths 
 * @author myles
 */
public class CreepManager
  {

    ArrayList<Creep> creepsAlive;
    ArrayList<Creep> creepsDead;

    public CreepManager()
      {
        creepsAlive = new ArrayList<Creep>();
        creepsDead = new ArrayList<Creep>();
      }

    public CreepManager(ArrayList<Creep> initialCreeps)
      {
        if (initialCreeps != null)
          {
            this.creepsAlive = initialCreeps;
          }
        else
          {
            creepsAlive = new ArrayList<Creep>();
          }
        this.creepsDead = new ArrayList<Creep>();
      }

    public void addCreep(Creep newCreep)
      {
        this.creepsAlive.add(newCreep);
      }

    public int getNumCreeps()
      {
        return creepsAlive.size();
      }

    public Iterator<Creep> getCreeps()
      {
        return creepsAlive.iterator();
      }

    public void update(double delta)
      {

        pruneCreeps();

        for (Creep c : creepsAlive)
          {
            //TODO update?
            c.update(delta);
            if (c.isDead())
              {
                creepsDead.add(c);
              }
          }
        
        pruneCreeps();
      }

    private void pruneCreeps() {
        //TODO change this to a state change when appropriate 
        creepsAlive.removeAll(creepsDead);
      }

    //TODO Remove this, this is only testing 
    public void draw(WorldGraphics2D w2d)
      {
        for (Creep cr : creepsAlive)
          {
            ((BasicCreep) cr).draw(w2d);
          }
      }
  }
