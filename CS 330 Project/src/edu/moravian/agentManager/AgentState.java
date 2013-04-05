/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.agentManager;

import edu.moravian.agent.ChaseAgent;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public interface AgentState
{
    public void Enter(ChaseAgent ag);

    public void Execute(ChaseAgent ag);

    public void Leave(ChaseAgent ag);
}
