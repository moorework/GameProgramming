<<<<<<< HEAD
package edu.moravian;

import edu.moravian.graphics.VideoConfigurationException;
import edu.moravian.util.CoordinateTranslator;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;

/**
 * The start-up for our game.
 *
 * @author mebjc01
 */
public class Main
  {

    private static int DWIDTH = 1600;
    private static int DHEIGHT = 1200;
    private static int DBITDEPTH = 32;

    public static void main(String[] args) throws FileNotFoundException
      {

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension resolution = tk.getScreenSize();

        Settings set = Settings.getInstance();
        set.setResolution(resolution);
        set.setWorldSize(new Dimension(1200, 800));

        DWIDTH = resolution.width;
        DHEIGHT = resolution.height;

        // Create our game with a world size equal to
        // the screen size
      
        TowerDefenseGame g = new TowerDefenseGame((int)set.getWorldSize().getWidth(),(int) set.getWorldSize().getHeight());
        
        Controller controller;
        CoordinateTranslator coordTrans = new CoordinateTranslator(set.getResolution().width, set.getResolution().height,
                        set.getWorldSize().width, set.getWorldSize().height);
        
        try
          {
            // Create the video controller.  This will throw if something
            // goes wrong
            UserInterfaceController video = new UserInterfaceController(DWIDTH, DHEIGHT, DBITDEPTH);
            
            controller = new Controller(video, g, coordTrans);
            
            video.setController(controller);

            // And run the game
            new Thread(video).start();
          }
        catch (VideoConfigurationException ex)
          {
            System.out.println("Unable to display " + DWIDTH + "x" + DHEIGHT
                    + "x" + DBITDEPTH + " in full screen mode");
          }
      }
  }
=======
package edu.moravian;

import edu.moravian.graphics.VideoConfigurationException;
import edu.moravian.util.CoordinateTranslator;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;

/**
 * The start-up for our game.
 *
 * @author mebjc01
 */
public class Main
  {

    private static int DWIDTH = 1600;
    private static int DHEIGHT = 1200;
    private static int DBITDEPTH = 32;

    public static void main(String[] args) throws FileNotFoundException
      {

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension resolution = tk.getScreenSize();

        Settings set = Settings.getInstance();
        set.setResolution(resolution);
        set.setWorldSize(new Dimension(1200,800));

        DWIDTH = resolution.width;
        DHEIGHT = resolution.height;

        // Create our game with a world size equal to
        // the screen size
      
        TowerDefenseGame g = new TowerDefenseGame((int)set.getWorldSize().getWidth(),(int) set.getWorldSize().getHeight());
        
        Controller controller;
        CoordinateTranslator coordTrans = new CoordinateTranslator(set.getResolution().width, set.getResolution().height,
                        set.getWorldSize().width, set.getWorldSize().height);
        
        try
          {
            // Create the video controller.  This will throw if something
            // goes wrong
            UserInterfaceController video = new UserInterfaceController(DWIDTH, DHEIGHT, DBITDEPTH);
            
            controller = new Controller(video, g, coordTrans);
            
            video.setController(controller);

            // And run the game
            new Thread(video).start();
          }
        catch (VideoConfigurationException ex)
          {
            System.out.println("Unable to display " + DWIDTH + "x" + DHEIGHT
                    + "x" + DBITDEPTH + " in full screen mode");
          }
      }
  }
>>>>>>> e95bf10e4d8e648f063bddc74ce56a4581d58b10
