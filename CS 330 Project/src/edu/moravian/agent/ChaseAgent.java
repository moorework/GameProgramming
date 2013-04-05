package edu.moravian.agent;

import edu.moravian.Drawable;
import edu.moravian.Settings;
import edu.moravian.Timer;
import edu.moravian.WorldGraphics2D;
import edu.moravian.agentManager.ChaseStateMachine;
import edu.moravian.agentManager.ChasePointState;
import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This is an agent that chases the player agent.  
 * @author James Moore (moore.work@live.com)
 */
public class ChaseAgent implements Drawable, Entity
  {

    BufferedImage apearance;
    BufferedImage apearance_orig;
    Vector2D direction;
    Point2D original_position;
    Point2D position;
    int units_food;
    ChaseStateMachine behaviorManager;
    Timer time;
    private final static Point2D default_position =
            new Point2D(Settings.getInstance().getSafePoint());
    
    /**
     * This will create a chase agent in a default position
     */
    public ChaseAgent()
      {
        position = default_position;
        original_position = default_position;
        direction = new Vector2D();
        init_common();
      }

    /**
     * This allows you to specify a position for the chase agent
     * @param position 
     */
    public ChaseAgent(Point2D position)
      {
        this.position = position;
        this.original_position = new Point2D(position);
        direction = new Vector2D();
        init_common();
      }

    private void init_common()
      {
        try
          {
            File file = new File("Images/EnemyIdle.png");
            apearance = ImageIO.read(file);
            apearance_orig = ImageIO.read(file);
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

        behaviorManager = new ChaseStateMachine(this);
        behaviorManager.setCurrent_state(new ChasePointState());

        time = new Timer();
        time.tick();

      }

    @Override
    public void draw(WorldGraphics2D g2d)
      {
        Color old = g2d.getColor();
        //g2d.rotate(0.0, PlayerAgent., direction.getY());
        g2d.drawImage(apearance, position, null);
        g2d.setColor(old);
      }

    public void update()
      {

        behaviorManager.update();
          
      }

    @Override
    public Shape get_dims()
      {
        return new Ellipse2D.Double(position.getX(), position.getY(), apearance.getWidth(), apearance.getHeight());
      }

    @Override
    public boolean touching(Shape shape)
      {
        if (shape.intersects(position.getX(), position.getY(), apearance.getWidth(), apearance.getHeight()))
          {
            return true;
          }
        return false;
      }

    public void reset()
      {
        this.position.set(original_position);
        time = new Timer();
      }

    public void chase(Point2D objective)
      {
        direction = new Vector2D(objective.getX() - position.getX(), objective.getY() - position.getY()).getNormalized();
        position = position.plus(direction.scalePlus(Settings.getInstance().getChaseSpeed(), direction));
      }

    public ChaseStateMachine getBehaviorManager()
      {
        return behaviorManager;
      }

    public void setApearance(BufferedImage apearance)
      {
        if (apearance == null)
          {
            System.exit(1);
            System.out.println("Appearance must be a non-null buffered iamge");
          }
        else
          {
            this.apearance = apearance;
          }
      }

    public void resetAppearance()
      {
        apearance = apearance_orig;
      }

    public Point2D getPosition()
      {
        return position;
      }

    public Vector2D getDirection()
      {
        return direction;
      }
    
        public Point2D getCenter()
    {
        return new Point2D(position.getX() + apearance.getWidth()/2, position.getY() + apearance.getHeight()/2);
    }
    
  }
