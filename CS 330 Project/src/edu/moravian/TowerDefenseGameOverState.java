
package edu.moravian;

import edu.moravian.graphics.GraphicsRegistry;
import edu.moravian.graphics.WorldGraphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author myles
 */
public class TowerDefenseGameOverState extends TowerDefenseState {
    private boolean gameConcluded;
    
    public TowerDefenseGameOverState(TowerDefenseGame master) {
        super(master);
        GraphicsRegistry.clearRegistry();
        
        gameConcluded = false;
    }
    
    @Override
    public void update(double delta)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void draw(WorldGraphics2D wG2d)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean done()
    {
        return gameConcluded;
    }

    @Override
    public void keyTyped(KeyEvent ke)
    {
        switch (ke.getKeyCode()) {
        case KeyEvent.VK_Q:
            gameConcluded = true;
            break;
    }
    }

    @Override
    public void keyPressed(KeyEvent ke)
    {
        // like I give a damn
    }

    @Override
    public void keyReleased(KeyEvent ke)
    {
        // like I give a damn
    }
}
