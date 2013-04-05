package edu.moravian.agent;

import edu.moravian.Drawable;
import edu.moravian.Settings;
import edu.moravian.WorldGraphics2D;
import edu.moravian.math.Point2D;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This is the player agent.  It is a singleton to allow one to simply talk 
 * to the player elsewhere with little drama or error proneness
 * 
 * @author James Moore (moore.work@live.com)
 */
public class PlayerAgent implements Drawable, Entity
  {

    BufferedImage apearance;
    private Point2D player_position;
    Settings set = Settings.getInstance();
    private static PlayerAgent instance;

    private PlayerAgent()
      {
        player_position = Settings.getInstance().getPlayerPosInit();

        try
          {
            File file = new File("Images/bubble.png");
            apearance = ImageIO.read(file);
          }
        catch (IOException e)
          {
            System.out.println("Couldn't parse image");
            System.out.println(e.getMessage());
            System.exit(1);
          }
        catch (IllegalArgumentException e)
          {
            System.out.println("Couldn't parse image");
            System.out.println(e.getMessage());
            System.exit(1);
          }

      }

    public static PlayerAgent instance()
      {
        if (instance == null)
          {
            synchronized (PlayerAgent.class)
              {
                if (instance == null)
                  {
                    instance = new PlayerAgent();
                  }
              }
          }
        return instance;
      }

    public Point2D getPlayerPosition()
      {
        return player_position;
      }

    public void setPlayer_position(Point2D player_position)
      {
        this.player_position = player_position;
      }

    public void moveRight()
      {
        player_position.set(player_position.plus(set.getPlayerMovementRight()));
      }

    public void moveDown()
      {
        player_position.set(player_position.plus(set.getPlayerMovementDown()));
      }

    public void moveLeft()
      {
        player_position.set(player_position.plus(set.getPlayerMovementLeft()));
      }

    public void moveUp()
      {

        player_position.set(player_position.plus(set.getPlayerMovementUp()));
      }

    @Override
    public Shape get_dims()
      {
        return new Ellipse2D.Double(player_position.getX(), player_position.getY(),
                apearance.getWidth(), apearance.getHeight());
      }

    @Override
    public boolean touching(Shape shp)
      {
        return shp.intersects((Rectangle2D) this.get_dims());
      }

    public void draw(WorldGraphics2D g2d)
      {
        Color old = g2d.getColor();

        g2d.drawImage(apearance,  player_position, null);
        g2d.setColor(old);
      }
  }
