package edu.moravian.WorldMap;

import edu.moravian.graphics.DrawLocation;
import edu.moravian.graphics.Drawable;
import edu.moravian.graphics.GraphicsIDHolder;
import edu.moravian.graphics.Sprite;
import edu.moravian.math.Point2D;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.jgrapht.Graph;

/**
 *
 * @author barros
 */
public class WorldMap implements Drawable {
    
    public WorldMap(ArrayList<WorldCell> topography, BufferedImage appearance, Dimension size) {
        // TODO implement me
    }
    
    public boolean isOccupied(Point2D pointToCheck) {
        // TODO implement me
        return true;
    }
    
    public void setOccupied(Point2D pointToOccupy) {
        // TODO implement me
    }
    
    public void setUnnocupied(Point2D pointToUnOccupy) {
        // TODO implement me
    }
    
    public Point2D getCornerPoint(Point2D pointLocation) {
        // TODO implement me
        return null;
    }
    
    public Graph<PathCell, Integer> getNavPath() {
        // TODO implement me
        return null;
    }

    @Override
    public Point2D getPos()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DrawLocation getDrawLocation()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GraphicsIDHolder getGraphicsID()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Point2D getStartingPoint() {
        // TODO implement me
        return null;
    }
    
    public Point2D getEndPoint()
    {
        // TODO implement me
        return null;
    }

    @Override
    public Sprite getCurrFrame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
