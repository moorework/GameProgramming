package edu.moravian;

import edu.moravian.graphics.VideoConfigurationException;
import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.util.Timer;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 * @author mebjc01
 *
 * This class encapsulates a graphics loop for a full-screen game.
 *
 * The class implements Runnable, and is intended to be used as a thread.
 *
 */
class UserInterfaceController extends JFrame implements Runnable
{
    // Convience references to the Graphics system

    private GraphicsEnvironment genv;
    private GraphicsDevice gdev;
    // The parameters specified by the user
    private int width;
    private int height;
    private int depth;
    private Game game;
    private Timer time;

    /**
     * Create an instance of the class with the specified screen configuration
     * using the given game.
     *
     * @param width the width of the desired screen resolution
     * @param height the height of the desired screen resolution
     * @param depth the color depth of the desired screen resolution
     * @param thugAim the game to use in the update loop
     * @throws VideoConfigurationException if the desired video mode is not
     * available or if fullscreen mode is not allowed
     */
    public UserInterfaceController(int width, int height, int depth, Game thugAim)
            throws VideoConfigurationException
    {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.game = thugAim;

        // Save references to the graphics environment and device
        // for future reference
        genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gdev = genv.getDefaultScreenDevice();

        if (!hasResolution(width, height, depth))
        {
            throw new VideoConfigurationException("unsupported resolution:");
        }

        if (!hasFullScreen())
        {
            throw new VideoConfigurationException("fullscreen unsupported");
        }

        // Full-screen applications should not be resizable, they
        // should not have decorations (title bar, etc.)
        setResizable(false);
        setUndecorated(true);

        // Because we are going to use active rendering (control the video
        // directly), our program should not respond to the normal
        // repaint requests.
        setIgnoreRepaint(true);

        inputHandler input = new inputHandler();

        this.addKeyListener(input);

        time = new Timer();
    }

    /**
     * Determines whether fullscreen mode is available
     *
     * @return true if fullscreen is available
     */
    private boolean hasFullScreen()
    {
        return gdev.isFullScreenSupported();
    }

    /**
     * Determines whether the specified resolution is available
     *
     * @param width the desired width
     * @param height the desired height
     * @param bitDepth the desired color depth
     * @return true if the specifed resolution is available
     */
    private boolean hasResolution(int width, int height, int bitDepth)
    {
        DisplayMode[] modes = gdev.getDisplayModes();

        for (int i = 0; i < modes.length; i++)
        {
            int w = modes[i].getWidth();
            int h = modes[i].getHeight();
            int b = modes[i].getBitDepth();

            if (width == w && height == h && bitDepth == b)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * This method is the process of the loop. After changing to the specified
     * resolution, the method executes the game as an update loop. When the game
     * indicates it is done, the loop terminates.
     */
    @Override
    public void run()
    {

        // Save the old resolution so we can go back when we are done.
        DisplayMode oldMode = gdev.getDisplayMode();

        // Many of the following method calls can cause exceptions
        // to be thrown.  If unhandled, and exception will cause the program
        // to terminate, and depending on the system, the video system may
        // be left in a "bad" state.  Thus, we enclose all our code in a huge
        // try block with a "finally" clause.  A "finally" clause will *always*
        // execute even if an unhandled exception causes the program to
        // terminate.  We use this to make sure we "clean up" the video

        try
        {
            // Go full screen!  "this" is the object, which is a window
            gdev.setFullScreenWindow(this);
            // Change to our desired resolution
            gdev.setDisplayMode(new DisplayMode(width, height, depth,
                    DisplayMode.REFRESH_RATE_UNKNOWN));

            // We want our program to use two buffers for graphics - one
            // is the display on the screen, and the other is the one
            // we write to.  More than two buffers is possible, but not
            // needed for what we need.
            // NOTE:  This cannot be done until *after* we are fullscreen
            this.createBufferStrategy(2);

            // Now that the strategy is created, we safe a reference to it
            // so that we can fillRect (below)
            BufferStrategy bufStrat = this.getBufferStrategy();

            // prev will be the time of the last frame.
            long prev = 0;

            // Keep going until the game says it is done
            while (!game.done())
            {
                //Tick the timer 
                time.tick();

                // By getting the current time each frame, we can compute
                // the program's frames per second.
                long currTime = System.currentTimeMillis();
                long diff = (currTime - prev);
                prev = currTime;

                // Tell the game object to update itself - i.e. to make itself
                // ready for the next fillRect.
                game.update();

                // Drawing is done through a Graphics object.  You can think
                // of this as the object representing the screen.
                Graphics g = bufStrat.getDrawGraphics();

                // THe first thing we have to do is "clear" the screen.
                // Whatever was drawn perviously is still on the screen.
                // We'll use a solid white background here.
                g.setColor(Settings.getInstance().getBackgroundColor());
                // Note that drawRect will only fillRect the rectangle outline
                g.fillRect(0, 0, width, height);


                // Tell the game to fillRect itself using the graphics context
                game.draw(new WorldGraphics2D(g));


                // Write the FPS in the upper-left corner.  The coordinates
                // designate the lower left of the text, and so anything
                // written at (0,0) will end up being written above the
                // viewable region.
                g.setColor(Color.blue);
                g.drawString("" + 1.0 / (diff / 1000.0), 0, 10);


                // Free up any resources being used.
                g.dispose();

                // This call *may* make the system wait for the monitor
                // to perform a vsync before continuing.  This could help
                // reduce flicker.  It doesn't seem to do anything on my Mac.
                //Toolkit.getDefaultToolkit().sync();

                // Now that we have drawn everything we want to see,
                // we "show" it, meaning tha the data in the 2nd buffer becomes
                // what is on the screen.
                bufStrat.show();

                // A monitor can only fillRect frames so fast.  If our frame rate
                // is faster than the monitor's refresh rate, frames will
                // either be dropped by the video system or they will be
                // drawn and cause video flicker.  To avoid this, we make
                // the program sleep.
                // If the OS forces the thread to wake up before the time,
                // then an exception will be thrown.  We can simply ignore
                // this exception and continue generating frames.

                
                
//                double sleeptime = time.getFPS() - time.getDelta();
//
//                if (sleeptime <= 0)
//                {
//                    sleeptime = 5;
//                }
//
//                try
//                {
//                    Thread.sleep((long) sleeptime);
//                }
//                catch (InterruptedException e)
//                {
//                }

            }
        }
// A "finally" clause will always execute even if an unhandled
// exception causes the program to terminate.  We use this to
// make sure we "clean up" the video
        finally
        {
            // Before we exit, we want to change back to the original
            // video mode.
            gdev.setDisplayMode(oldMode);
            // This command returns the screen to non-full-screen.
            gdev.setFullScreenWindow(null);

            System.exit(0);


        }
    }

    private class inputHandler implements KeyListener
    {

        @Override
        public void keyTyped(KeyEvent e)
        {
            ((TowerDefenseGame) game).keyTyped(e);
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            ((TowerDefenseGame) game).keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            ((TowerDefenseGame) game).keyReleased(e);
        }
    }
}
