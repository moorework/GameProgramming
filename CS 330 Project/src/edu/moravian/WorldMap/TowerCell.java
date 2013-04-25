package edu.moravian.WorldMap;

import edu.moravian.tower.Tower;

/**
 *
 * @author myles
 */
public class TowerCell implements WorldCell {
    private final boolean CAN_BE_OCCUPIED;
    private final boolean IS_PATHABLE;
    private Tower t;
    
    private boolean isOccupied;
    
    public TowerCell()
    {
        CAN_BE_OCCUPIED = true;
        IS_PATHABLE = false;
        
        isOccupied = false;
    }
    
    @Override
    public boolean canBeOccupied()
    {
        return CAN_BE_OCCUPIED;
    }
    
    public boolean isOccupied()
    {
        return isOccupied;
    }
    
    public void setOccupied(Tower newTower)
    {
        t = newTower;
        isOccupied = true;
    }
    
    public void setUnOccupied()
    {
        t = null;
        isOccupied = false;
    }
    
    public Tower getTower() {
        return t;
    }

    @Override
    public boolean isPathable()
    {
        return IS_PATHABLE;
    }
}
