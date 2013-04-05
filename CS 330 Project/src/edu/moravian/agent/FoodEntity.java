package edu.moravian.agent;

import edu.moravian.EntityManager;
import edu.moravian.WorldGraphics2D;
import edu.moravian.math.Point2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class FoodEntity implements Entity {

    private Point2D position;
    private int foodvalue;
    private BufferedImage apearance;

    /**
     *
     * @param position The position at which we want to place the food (upper
     * left corner of appearance will be at this point)
     * @param foodvalue The value of food that this will provide feeders. (1 has
     * no special effects)
     */
    public FoodEntity(Point2D position, int foodvalue) {
        try {
            File file;
            if (foodvalue > 0) {
                file = new File("Images/BadFood.jpeg");
            } else {
                 file = new File("Images/Food.png");
            }
            apearance = ImageIO.read(file);

        } catch (IOException e) {
            System.out.println("Couldn't parse image");
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Couldn't parse image");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        this.foodvalue = foodvalue;
        this.position = position;

        EntityManager.instance.addFood(this);
    }

 
    @Override
    public void draw(WorldGraphics2D g2d) {

        Color old = g2d.getColor();
        g2d.drawImage(apearance, position, null);
        g2d.setColor(old);

    }

    @Override
    public Shape get_dims() {
        return new Rectangle(apearance.getWidth(), apearance.getHeight());
    }

    @Override
    public boolean touching(Shape shp) {
        if (shp.intersects(position.getX(), position.getY(), apearance.getWidth(), apearance.getHeight())) {
            return true;
        }
        return false;
    }

    public Point2D getPosition() {
        return position;
    }

    public int getFoodvalue() {
        return foodvalue;
    }
}
