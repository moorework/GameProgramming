package edu.moravian.graphics;

import edu.moravian.Settings;
import edu.moravian.math.Point2D;
import edu.moravian.util.CoordinateTranslator;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This class encapsulates the interaction with the screen
 *
 * @author James Moore (moore.work@live.com)
 */
public class WorldGraphics2D
{
    
    Graphics2D g2d;
    CoordinateTranslator trans;
    Settings set = Settings.getInstance();
    
    public WorldGraphics2D(Graphics g)
    {
        this.g2d = (Graphics2D) g;
        trans = new CoordinateTranslator(
                set.getResolution().width,
                set.getResolution().height,
                set.getWorldSize().width,
                set.getWorldSize().height);
    }
    
    public void setColor(Color background)
    {
        g2d.setColor(background);
    }
    
    public void fillRect(Point2D location, int worldWidth, int worldHeight)
    {
        Point2D lower_left = trans.worldtoScreen(location);
        
        g2d.fillRect((int) lower_left.getX(), (int) lower_left.getY(), worldWidth, worldHeight);
    }
    
    public Color getColor()
    {
        return g2d.getColor();
    }
    
    public void drawString(String string, Point2D point, Color c)
    {
        Color old = g2d.getColor();
        g2d.setColor(c);
        
        Point2D pixelPoint = trans.worldtoScreen(point);
        
        g2d.drawString(string, (int) pixelPoint.getX(), (int) pixelPoint.getY());
        g2d.setColor(old);
        
    }
    
    public void drawLine(Point2D start_in, Point2D end_in)
    {
        Point2D start = trans.worldtoScreen(start_in);
        Point2D end = trans.worldtoScreen(end_in);
        g2d.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
    }
    
    public void drawImage(BufferedImage apearance, Point2D location_in, Object object)
    {
        Point2D location = trans.worldtoScreen(location_in);
        g2d.drawImage(apearance, (int) location.getX(), (int) location.getY(), null);
    }
    
    public void drawImage(Sprite sprite, Point2D position)
    {
        Point2D screenPoint = trans.worldtoScreen(position);
        
        g2d.drawImage(sprite.getBackingImage(), (int) screenPoint.getX(), (int) screenPoint.getY(), null);
    }
    
    public void drawImageWithPointAtCenter(Sprite sprite, Point2D position)
    {
        Point2D screenPoint = trans.worldtoScreen(position);
        
        
        BufferedImage img = sprite.getBackingImage();
        
        
        g2d.drawImage(img, (int) screenPoint.getX() - (int) (sprite.getWidth() / 2), (int) screenPoint.getY() - (sprite.getHeight() / 2), null);
    }
    
    public void drawImageCenterXAxis(Sprite sprite, Point2D position)
    {
        Point2D screenPoint = trans.worldtoScreen(position);
        
        g2d.drawImage(sprite.getBackingImage(), (int) screenPoint.getX() - (sprite.getWidth() / 2), (int) screenPoint.getY(), null);
    }
    
    public void drawImageCenterYAxis(Sprite sprite, Point2D position)
    {
        Point2D screenPoint = trans.worldtoScreen(position);
        
        BufferedImage img = sprite.getBackingImage();
        
        g2d.drawImage(img, (int) screenPoint.getX(), (int) screenPoint.getY() - (sprite.getHeight() / 2), null);
    }
    
    public void fillCircle(Point2D pos, int Radius, Color brighter)
    {
        Color old = g2d.getColor();
        g2d.setColor(brighter);
        Point2D temp = trans.worldtoScreen(pos);
        g2d.fillOval((int) temp.getX(), (int) temp.getY(), Radius * 2, Radius * 2);
        
        g2d.setColor(old);
    }
    
    public void fillRect(Point2D pos, Dimension size, Color col)
    {
        
        Color old = g2d.getColor();
        g2d.setColor(col);
        Point2D temp = trans.worldtoScreen(pos);
        g2d.fillRect((int) temp.getX(), (int) temp.getY(), (int) size.width, (int) size.height);
        
        g2d.setColor(old);
    }
    
    public void fillOval(Point2D loc, int i1, int i2, Color col)
    {
        Color old = g2d.getColor();
        Point2D temp = trans.worldtoScreen(loc);
        g2d.setColor(col);
        g2d.fillOval((int) temp.getX(), (int) temp.getY(), i1, i2);
        g2d.setBackground(old);
    }
    
    public void drawCircle(Point2D loc, int diameter, Color col)
    {
        Color old = g2d.getColor();
        g2d.setColor(col);
        Point2D temp = trans.worldtoScreen(loc);
        g2d.drawOval((int) temp.getX(), (int) temp.getY(), diameter, diameter);
        g2d.setColor(old);
    }
    
    public CoordinateTranslator getTrans()
    {
        return trans;
    }
    
    public Composite getAlphaCompositeRule()
    {
        return g2d.getComposite();
    }

    public void setAlphaCompositeRule(int comp)
    {
    
    }
}
