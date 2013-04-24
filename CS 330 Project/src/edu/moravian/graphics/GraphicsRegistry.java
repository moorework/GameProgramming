package edu.moravian.graphics;

/**
 *
 * @author myles
 */
public class GraphicsRegistry {
    private static GraphicsManager graphicsManager;
    
    public static void setGraphicsManager(GraphicsManager gM) {
        graphicsManager = gM;
    }
    
    public static void registerDrawable(Drawable newDrawable) {
        graphicsManager.addDrawable(newDrawable);
    }
    
    public static void unRegisterDrawable(Drawable toRemove) {
        graphicsManager.removeDrawable(toRemove);
    }
    
    public static void clearRegistry() {
        graphicsManager.clearManager();
    }
}
