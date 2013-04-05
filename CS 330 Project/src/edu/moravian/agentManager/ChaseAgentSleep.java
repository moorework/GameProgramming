package edu.moravian.agentManager;

import edu.moravian.Settings;
import edu.moravian.Timer;
import edu.moravian.agent.ChaseAgent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class is the state that handles sleeping for the agent
 *
 * @author James Moore (moore.work@live.com)
 */
public class ChaseAgentSleep implements AgentState
  {
    Timer time;
    double sleepTime;
    private BufferedImage apearance;

    /**
     * Creates a sleep instance using default values
     */
    public ChaseAgentSleep()
      {
        time = new Timer();
        
        sleepTime = Settings.getInstance().getDefaultChaseEnergyLevel();
        init_appearance();
      }

    /**
     * Creates a sleep state with a given time
     *
     * @param sleeptime_in The time we want to sleep when the agent is tired in
     * seconds
     */
    public ChaseAgentSleep(double sleeptime_in)
      {
        time = new Timer();
        sleepTime = sleeptime_in;
        init_appearance();
      }

    @Override
    public void Enter(ChaseAgent ag)
      {
        time.tick();
        ag.setApearance(apearance);
        ag.getBehaviorManager().pauseHunger();
      }

    @Override
    public void Execute(ChaseAgent ag)
      {
        if (time.getDelta() > sleepTime)
          {
            ag.getBehaviorManager().restoreFatigue(Settings.getInstance().getFullStateRestore());
            ag.getBehaviorManager().setCurrent_state(new ChasePointState());
          }
        else
          {
            //Don't do anything 
          }
      }

    @Override
    public void Leave(ChaseAgent ag)
      {
        ag.resetAppearance();
        ag.getBehaviorManager().unPauseHunger();
      }

    private void init_appearance()
      {
        try
          {
            File file = new File("Images/EnemySleep.png");
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
