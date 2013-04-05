package edu.moravian.agentManager;

import edu.moravian.agent.ChaseAgent;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class NullState implements AgentState
  {
    private final static NullState instance = new NullState();
    
    private NullState(){}

    @Override
    public void Enter(ChaseAgent ag)
      {
      }

    @Override
    public void Execute(ChaseAgent ag)
      {
          
      }

    @Override
    public void Leave(ChaseAgent ag)
      {
        
      }
    
      public static AgentState Instance()
      {
       return instance;
      }
  }
