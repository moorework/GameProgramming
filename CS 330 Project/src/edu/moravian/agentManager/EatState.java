package edu.moravian.agentManager;

import edu.moravian.EntityManager;
import edu.moravian.Settings;
import edu.moravian.Timer;
import edu.moravian.agent.ChaseAgent;
import edu.moravian.agent.FoodEntity;
import edu.moravian.math.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class EatState implements AgentState
  {

    Timer time;
    boolean eating;
    double eatTime;
    private BufferedImage apearance;
    FoodEntity closest_food;

    public EatState()
      {
        time = new Timer();
        
        eatTime = Settings.getInstance().getDefaultEatTime();
        init_appearance();
      }

    public EatState(double eattime_i)
      {
        time = new Timer();
        eatTime = eattime_i;
        init_appearance();
      }

    @Override
    public void Enter(ChaseAgent ag)
      {
        double food_dis = Double.MAX_VALUE;

        for (FoodEntity food : EntityManager.instance.getFoods())
          {
            double dist_tmp = Point2D.distanceSQ(food.getPosition(), ag.getPosition());
            if (dist_tmp < food_dis)
              {
                food_dis = dist_tmp;
                closest_food = food;
              }
          }
        time.tick();
        ag.setApearance(apearance);
        eating = false;


      }

    @Override
    public void Execute(ChaseAgent ag)
      {
        
        //Remember, sleeping is more important than eating 
        if(ag.getBehaviorManager().isTired()){
            ag.getBehaviorManager().setCurrent_state(new ChaseAgentSleep());
        }
                
        if (Point2D.distanceSQ(ag.getPosition(), closest_food.getPosition()) < 10)
          {
           
            if (eating == true)
              {
                 
                   
                if (time.getDelta() > eatTime)
                  {
                    ag.getBehaviorManager().restoreFood(closest_food.getFoodvalue());
                    ag.getBehaviorManager().setCurrent_state(new ChasePointState());

                  }
              }
            else
              {
                eating = true;
                time.tick();
              }
          }
        else
          {

            ag.chase(closest_food.getPosition());
          }
      }

    @Override
    public void Leave(ChaseAgent ag)
      {
        ag.resetAppearance();
      }

    private void init_appearance()
      {
        try
          {
            File file = new File("Images/EnemyEat.png");
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
  }
