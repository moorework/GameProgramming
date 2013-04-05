package edu.moravian.agentManager;

import edu.moravian.Settings;
import edu.moravian.agent.ChaseAgent;
import edu.moravian.util.Timer;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class ChaseStateMachine
  {


    private AgentState current_state;
    private AgentState previous_state;
    private ChaseAgent agent;
    //Fatigue numbers are in seconds 
    TimeBasedMeter fatigue;
    TimeBasedMeter hunger;

    public ChaseStateMachine(ChaseAgent agent)
      {

        this.agent = agent;

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



    public AgentState getCurrent_state() {
        return current_state;
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
