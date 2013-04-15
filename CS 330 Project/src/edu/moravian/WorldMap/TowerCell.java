package edu.moravian.WorldMap;

/**
 *
 * @author myles
 */
public class TowerCell implements WorldCell {
    private final boolean CAN_BE_OCCUPIED;
    private final boolean IS_PATHABLE;
    
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
    
    public void setOccupied()
    {
        isOccupied = true;
    }
    
    public void setUnOccupied()
    {
        isOccupied = false;
    }

    @Override
    public boolean isPathable()
    {
        return IS_PATHABLE;
    }
}
