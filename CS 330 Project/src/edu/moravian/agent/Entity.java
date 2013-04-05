package edu.moravian.agent;

import edu.moravian.graphics.WorldGraphics2D;
import java.awt.Graphics2D;
import java.awt.Shape;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public interface Entity
  {
    public void draw(WorldGraphics2D g2d);

    public Shape get_dims();

    public boolean touching(Shape shp);
    
   
  }
