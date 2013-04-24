package edu.moravian.creep;

import edu.moravian.graphics.GraphicsRegistry;
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

    ArrayList<BasicCreep> creepsAlive;
    ArrayList<BasicCreep> creepsDead;

    public CreepManager()
      {
        creepsAlive = new ArrayList<BasicCreep>();
        creepsDead = new ArrayList<BasicCreep>();
      }

    public CreepManager(ArrayList<BasicCreep> initialCreeps)
      {
        if (initialCreeps != null)
          {
            this.creepsAlive = initialCreeps;
          }
        else
          {
            creepsAlive = new ArrayList<BasicCreep>();
          }
        this.creepsDead = new ArrayList<BasicCreep>();
        
        for (BasicCreep c : creepsAlive) {
            GraphicsRegistry.registerDrawable(c);
        }
      }

    
    public void addCreep(BasicCreep newCreep)
      {
        this.creepsAlive.add(newCreep);
        GraphicsRegistry.registerDrawable(newCreep);
      }

    public int getNumCreeps()
      {
        return creepsAlive.size();
      }

    public Iterator<BasicCreep> getCreeps()
      {
        return creepsAlive.iterator();
      }

    public void update(double delta)
      {

        pruneCreeps();

        for (BasicCreep c : creepsAlive)
          {
            //TODO update?
            c.update(delta);
            if (c.isDead())
              {
                creepsDead.add(c);
                GraphicsRegistry.unRegisterDrawable(c);
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
