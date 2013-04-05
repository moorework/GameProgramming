package edu.moravian.agentManager;

import edu.moravian.Settings;
import edu.moravian.Timer;
import edu.moravian.agent.ChaseAgent;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class ChaseStateMachine
  {

    private int hungerTime;
    private int sleepTime;
    private AgentState current_state;
    private AgentState previous_state;
    private ChaseAgent agent;
    //Fatigue numbers are in seconds 
    TimeBasedMeter fatigue;
    TimeBasedMeter hunger;

    public ChaseStateMachine(ChaseAgent agent)
      {
        hungerTime = (int) Settings.getInstance().getDefaultFoodReserves();
        sleepTime = (int) Settings.getInstance().getDefaultChaseEnergyLevel();
        this.agent = agent;
        hunger = new TimeBasedMeter(hungerTime);
        fatigue = new TimeBasedMeter(sleepTime);
        current_state = NullState.Instance();
      }

    public void setCurrent_state(AgentState new_state)
      {
        current_state.Leave(agent);
        new_state.Enter(agent);
        this.current_state = new_state;
      }

    public void update()
      {
        /*
         * The problem we face here is that changing states is gonna get really 
         * messy if we keep it here 
         */
        current_state.Execute(agent);
      }

    public void RevertToPreviousState()
      {
        current_state = previous_state;
        //TODO may queue of previous states 
      }

    public boolean isTired()
      {
        return fatigue.isDueForReset();

      }

    public void pauseHunger()
      {
        hunger.pause();
      }

    public void unPauseHunger()
      {
        hunger.unPause();
      }

    public boolean isHungry()
      {
      
        return hunger.isDueForReset();
      }

    /**
     * Restore the maximum multiplied by a scale
     *
     * @param Scale The amount by which to scale the food restoration. 1 is
     * default
     */
    public void restoreFood(double Scale)
      {
     
       hunger.reset();
          
          System.out.println(hunger.time.getDelta() + Scale);

      }

    public AgentState getCurrent_state() {
        return current_state;
    }
    
    

    /**
     * Restore fatigue by default amount scaled by input.
     *
     * @param Scale The amount by which to scale the sleep restoration. 1 is
     * default
     */
    public void restoreFatigue(double Scale)
      {
        fatigue.reset();

      }

    /**
     * This allows the easy creation of managers for time based game metrics.  
     * 
     */
    private class TimeBasedMeter
      {

        Timer time;
        private double offset;
         private double cutoff;

        public TimeBasedMeter(double cutoff)
          {
            this.cutoff = cutoff;
            offset = 0;
            time = new Timer();
          }

        public void pause()
          {
            offset += time.getDelta();
          }

        public void unPause()
          {
            time.tick();
          }

        public boolean isDueForReset()
          {
            return (time.getDelta() + offset) > cutoff;
          }

        public void reset()
          {
            time.tick();
            offset = 0;
          }

        public void reset(int scale)
          {
            time.tick();
            offset = 0;
            offset += scale;
            
          }

        public double getOffset() {
            return offset;
        }
        
        
      }
  }
