package edu.moravian.creep.AI;

import edu.moravian.creep.Creep;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public interface CreepState
{

    public void Enter(Creep ag);

    public void Execute(Creep ag);

    public void Leave(Creep ag);
}
