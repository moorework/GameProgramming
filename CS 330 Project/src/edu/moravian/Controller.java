
package edu.moravian;

import edu.moravian.graphics.GraphicsManager;
import edu.moravian.graphics.GraphicsRegistry;

/**
 *
 * @author myles
 */
public class Controller {
    private GraphicsManager graphicsManager;
    private UserInterfaceController uiController;
    private TowerDefenseGame tdGame;
    
    public Controller(UserInterfaceController uic, TowerDefenseGame tdGame) {
        uiController = uic;
        this.tdGame = tdGame;
        
        // There is an atrocious bi-directional dependancy, here.
        // The TowerDefenseGame assumes the registry has been set up and it is
        // actively adding to it.
        // The GraphicsManager expects the map display to have been created and
        // stored externally before its creation.
        
        // the chicken and the egg
        
        graphicsManager = new GraphicsManager();
        GraphicsRegistry.setGraphicsManager(graphicsManager);
    }
}
