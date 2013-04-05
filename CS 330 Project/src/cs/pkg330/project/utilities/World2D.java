package cs.pkg330.project.utilities;

import cs.pkg330.project.graphics.Sprite;
import cs.pkg330.project.math.Point2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * A World2D is a class that represents a world's visual presence on the screen.
 * 
 * @author myles
 */
public class World2D {
    private Graphics2D screen; // a Graphics2D that describes the screen
    // we use the coordinateTranslator to interface between the world that we
    // represent and the screen that we draw to
    private CoordinateTranslator coordinateTranslator;
    
    /**
     * Create a new World2D. The provided graphics object will represent the screen
     * while the CoordinateTranslator will describe the relationship between the
     * world and the screen.
     * 
     * @param g the graphics object that describes the screen
     * @param ct the CoordinateTranslator that describes the relationship between
     *           the world and the screen
     */
    public World2D(Graphics g, CoordinateTranslator ct) {
        screen = (Graphics2D) g;
        coordinateTranslator = ct;
    }
    
    public void drawImage(Sprite sprite, Point2D position) {
        Point screenPoint = coordinateTranslator.convertWorldToScreen(position);
        
        screen.drawImage(sprite.getBackingImage(), screenPoint.x, screenPoint.y, null);
    }
    
    public void drawImageWithPointAtCenter(Sprite sprite, Point2D position) {
        Point screenPoint = coordinateTranslator.convertWorldToScreen(position);
        
        screen.drawImage(sprite.getBackingImage(), screenPoint.x - (sprite.getWidth() / 2),
                screenPoint.y - (sprite.getHeight() / 2), null);
    }
    
    public void drawImageCenterXAxis(Sprite sprite, Point2D position) {
        Point screenPoint = coordinateTranslator.convertWorldToScreen(position);
        
        screen.drawImage(sprite.getBackingImage(), screenPoint.x - (sprite.getWidth() / 2),
                screenPoint.y, null);
    }
    
    public void drawImageCenterYAxis(Sprite sprite, Point2D position) {
        Point screenPoint = coordinateTranslator.convertWorldToScreen(position);
        
        screen.drawImage(sprite.getBackingImage(), screenPoint.x,
                screenPoint.y - (sprite.getHeight() / 2), null);
    }
    
    public void setColor(Color c) {
        screen.setColor(c);
    }
    
    public void drawString(String toDraw, Point2D position) {
        Point screenPoint = coordinateTranslator.convertWorldToScreen(position);
        
        screen.drawString(toDraw, screenPoint.x, screenPoint.y);
    }
    
    public void setFont(Font f) {
        screen.setFont(f);
    }
    
    public void drawLine(Point2D startPos, Point2D endPos) {
        Point startScreenPt = coordinateTranslator.convertWorldToScreen(startPos);
        Point endScreenPt = coordinateTranslator.convertWorldToScreen(endPos);
        
        screen.drawLine(startScreenPt.x, startScreenPt.y, endScreenPt.x, endScreenPt.y);
    }
    
    public void drawOval(Point2D drawPos, int width, int height) {
        Point drawPoint = coordinateTranslator.convertWorldToScreen(drawPos);
        
        screen.drawOval(drawPoint.x, drawPoint.y, width, height);
    }
    
    public void drawRect(Point2D drawPos, int width, int height) {
        Point drawPoint = coordinateTranslator.convertWorldToScreen(drawPos);
        
        screen.drawRect(drawPoint.x, drawPoint.x, width, height);
    }
}
