
package edu.moravian.tower;

import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import java.awt.Color;
import java.awt.Shape;

/**
 *
 * @author moore
 */
public class BasicTower implements Tower{
    
    //TODO get this from settings 
    
    private Point2D pos;

    public BasicTower(Point2D pos) {
        this.pos = pos;
    }
  
 

    @Override
    public Point2D getPosition() {
       return pos;
    }

    @Override
    public Shape getRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

public void draw(WorldGraphics2D g2d){
    Color old = g2d.getColor();
    g2d.fillCircle(pos, 100, old.brighter().brighter());
}
    
    
}
