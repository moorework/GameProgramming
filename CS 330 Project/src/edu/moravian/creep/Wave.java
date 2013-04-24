
package edu.moravian.creep;

import edu.moravian.WorldMap.NavGraph;
import edu.moravian.WorldMap.WorldMap;
import edu.moravian.math.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author myles
 */
public class Wave {
    private final double GAP_BETWEEN_SPAWN = 15;
    
    private int numCreeps;
    private int creepHealth;
    private double creepSpeed;
    
    private int imageID;
    
    public Wave(int numCreeps, int creepHealth, double creepSpeed, int imageID) {
        this.numCreeps = numCreeps;
        this.creepHealth = creepHealth;
        this.creepSpeed = creepSpeed;
        
        this.imageID = imageID;
    }
    
    public int getNumCreeps() {
        return numCreeps;
    }
    
    public int getCreepHealth() {
        return creepHealth;
    }
    
    public double getCreepSpeed() {
        return creepSpeed;
    }
    
    public CreepManager getCreepManager(WorldMap worldMap) {
        CreepManager ret;
        ArrayList<BasicCreep> creeps = new ArrayList<BasicCreep>(numCreeps);
        
        NavGraph navGraph = worldMap.getNavPath();
        
        LinkedList<Point2D> startPoints = worldMap.getStartingPoints();
        LinkedList<Point2D> endPoints = worldMap.getEndingPoints();
        
        int numRows = startPoints.size();
        int numCreepsPerRow = (int) Math.ceil(numCreeps / (double) numRows);
        
        BasicCreep newCreep;
        Point2D currPoint;
        Point2D startPoint;
        Point2D endPoint;
        for (int i = 0; i < numRows; i++) {
            currPoint = startPoints.get(i);
            currPoint.setX(currPoint.getX() - GAP_BETWEEN_SPAWN);
            startPoint = currPoint;
            
            endPoint = endPoints.get(i);
            
            for (int j = 0; j < numCreepsPerRow; j++) {
                newCreep = new BasicCreep(navGraph, startPoint, endPoint, creepSpeed, creepHealth, imageID);
                
                creeps.add(newCreep);
            }
        }
        
        ret = new CreepManager(creeps);
        
        return ret;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Wave == false) {
            return false;
        }
        
        Wave otherWave = (Wave) o;
        
        int theirNumCreeps = otherWave.getNumCreeps();
        int theirCreepHealth = otherWave.getCreepHealth();
        double theirCreepSpeed = otherWave.getCreepSpeed();
        
        if ((theirNumCreeps == numCreeps) == false) {
            return false;
        }
        
        if ((theirCreepHealth == creepHealth) == false) {
            return false;
        }
        
        if (Math.abs(creepSpeed - theirCreepSpeed) > 0.1) {
            return false;
        }
        
        return true;
    }
}
