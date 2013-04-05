package edu.moravian;

import edu.moravian.graphics.VideoConfigurationException;
import java.awt.Dimension;
import java.awt.Toolkit;

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

    public static void main(String[] args)
      {

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension resolution = tk.getScreenSize();

        Settings set = Settings.getInstance();
        set.setResolution(resolution);

        DWIDTH = resolution.width;
        DHEIGHT = resolution.height;

        // Create our game with a world size equal to
        // the screen size
        TowerDefenseGame g = new TowerDefenseGame(1200, 800);

        try
          {
            // Create the video controller.  This will throw if something
            // goes wrong
            UserInterfaceController video = new UserInterfaceController(DWIDTH, DHEIGHT, DBITDEPTH, g);

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
