package edu.moravian.util;

import edu.moravian.math.Point2D;
import java.awt.Dimension;

/**
 * This class translates between screen coordinates and world coordinates
 *
 * @author James Moore (moore.work@live.com)
 */
public class CoordinateTranslator
{

    private int screenWidth;
    private int screenHeight;
    private double worldWidth;
    private double worldHeight;

    public CoordinateTranslator(int screenWidth, int screenHeight, double worldWidth, double worldHeight)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    /**
     * Translate a screen coordinate to a world coordinate
     *
     * @param screenPoint
     * @return
     */
    public Point2D screenToWorld(Point2D screenPoint)
    {
        return new Point2D((screenPoint.getX() / screenWidth) * worldWidth,
                (1 - screenPoint.getY() / screenHeight) * worldHeight);
    }

    /**
     * Translate a world coordinate to a screen coordinate
     *
     * @param worldPoint
     * @return
     */
    public Point2D worldtoScreen(Point2D worldPoint)
    {
        return new Point2D(worldPoint.getX() / worldWidth * screenWidth,
                (1 - worldPoint.getY() / worldHeight) * screenHeight);
    }

    public int getScreenWidth()
    {
        return screenWidth;
    }

    public int getScreenHeight()
    {
        return screenHeight;
    }

    public double getWorldWidth()
    {
        return worldWidth;
    }

    public double getWorldHeight()
    {
        return worldHeight;
    }

    public Dimension getWorldDims()
    {
        return new Dimension((int) worldWidth, (int) worldHeight);
    }
}
