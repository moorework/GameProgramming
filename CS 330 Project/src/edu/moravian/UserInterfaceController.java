package edu.moravian;

import edu.moravian.SM.PauseState;
import edu.moravian.WorldMap.MapBuilder;
import edu.moravian.graphics.VideoConfigurationException;
import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import edu.moravian.tower.BasicTower;
import edu.moravian.tower.Tower;
import edu.moravian.util.DeltaTimer;
import edu.moravian.util.GraphicsDataParser;
import edu.moravian.util.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JButton;
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
    private Controller controller;
    private Timer time;
    private int clickableArea;
    private UI_Element reset;
    private UI_Element pause;
    private UI_Element towerData;
    private SimpleButton towerButton;
    private BufferedImage mapRep;

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
<<<<<<< HEAD
    public UserInterfaceController(int width, int height, int depth)
=======
    public UserInterfaceController(int width, int height, int depth) 
>>>>>>> e95bf10e4d8e648f063bddc74ce56a4581d58b10
            throws VideoConfigurationException, FileNotFoundException
    {
        this.width = width;
        this.height = height;
        this.depth = depth;

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

        clickableArea = (int) (height * Settings.getInstance().getDrawablePercentage());



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
        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        time = new Timer();
        
        reset = new UI_Element(new Point2D(20, 20), "Reset", Color.blue, Color.black, new Dimension(50, 50), false);
        pause = new UI_Element(new Point2D(100, 20), "Pause", Color.blue, Color.black, new Dimension(50, 50), false);
        
        towerData = new UI_Element(new Point2D(1250, 0), "", Color.LIGHT_GRAY, Color.black, new Dimension(640, 120), true);
        towerButton = new SimpleButton(new Point2D(1160, 30), new Dimension(50, 50), GraphicsDataParser.readInSprite("buttonTower.png").getBackingImage());

        String path = Settings.getInstance().getMapPath();

        MapBuilder mb = new MapBuilder();
        
        mapRep = mb.createImageReppresentation(MapBuilder.getMapRepresentation(path), new Dimension(width, height - clickableArea));
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
    
    public void setController(Controller c) {
        controller = c;
    }

    /**
     * This method is the process of the loop. After changing to the specified
     * resolution, the method executes the game as an update loop. When the game
     * indicates it is done, the loop terminates.
     */
    @Override
    public void run()
    {
        
        int avgCount = 0;
        double avg = 0;
        String avgFPS = "";
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

            DeltaTimer deltaTimer = new DeltaTimer();

            // prev will be the time of the last frame.
            double delta = 0;
            // time since we last drew the FPS on-screen
            double sinceLastFPSDraw = 0;
            // to reduce the wild drawing of the FPS counter, we'll restrict
            // updating it to once every 70 milliseconds
            final double SPAN_BETWEEN_FPS_DRAWS = 70 / 1000.0;
            // a String representing the FPS counter to be drawn on-screen
            String framesPerSecond = "";

            // prev will be the time of the last frame.
            long prev = 0;
            String towerStr;
            // Keep going until the game says it is done
            while (!controller.done())
            {
         
                //Tick the timer 
                time.tick();

                // By getting the current time each frame, we can compute
                // the program's frames per second.
                long currTime = System.currentTimeMillis();
                long diff = (currTime - prev);
                prev = currTime;
                
                // By getting the current time each frame, we can compute
                // the program's frames per second.
                deltaTimer.tick();

                // retrieve the span of time necessary to process the last frame
                // to use as a basis for computations for this frame
                delta = deltaTimer.getDelta();

                // update the time since last we updated the FPS on-screen
                sinceLastFPSDraw += delta;

                         // Tell the game object to update itself - i.e. to make itself
                // ready for the next fillRect.
                controller.update(delta);

                // Drawing is done through a Graphics object.  You can think
                // of this as the object representing the screen.
         
                Graphics g = bufStrat.getDrawGraphics();
         
                
                // THe first thing we have to do is "clear" the screen.
                // Whatever was drawn perviously is still on the screen.
                // We'll use a solid white background here.
         
                g.setColor(Settings.getInstance().getBackgroundColor());
                // Note that drawRect will only fillRect the rectangle outline
                g.fillRect(0, 0, width, height);

                g.setColor(Color.blue);
                g.drawLine(0, clickableArea, width, clickableArea);

                g.drawImage(mapRep, 0, clickableArea, this);
                
                // Tell the game to fillRect itself using the graphics context
                WorldGraphics2D world  = new WorldGraphics2D(g);

                controller.draw(world);

                reset.draw(g);
                
                pause.draw(g);
<<<<<<< HEAD
=======
                
                BasicTower t;
                towerStr = "";
                if (controller.towerCurrentlySelected()) {
                    towerData.clearText();
                    t = controller.getCurrentlySelectedTower();
                    towerStr = "Damage: " + t.getDamage() + "\n";
                    towerData.addString(towerStr);
                    towerStr = "Targeting Radius: " + t.getRadius() + "\n";
                    towerData.addString(towerStr);
                    towerStr = "\n";
                    towerData.addString(towerStr);
                    towerStr = "Targeting System: " + t.getTargetingType() + "\n";
                    towerData.addString(towerStr);
                }
                else {
                    towerData.clearText();
                    towerStr = "Damage: --- \n";
                    towerData.addString(towerStr);
                    towerStr = "\n";
                    towerData.addString(towerStr);
                    towerStr = "Targeting Radius: --- \n";
                    towerData.addString(towerStr);
                    towerStr = "\n";
                    towerData.addString(towerStr);
                    towerStr = "Targeting System: --- \n";
                    towerData.addString(towerStr);
                }
                
                towerData.draw(g);
                towerButton.draw(g);
>>>>>>> e95bf10e4d8e648f063bddc74ce56a4581d58b10

                // Write the FPS in the upper-left corner.  The coordinates
                // designate the lower left of the text, and so anything
                // written at (0,0) will end up being written above the
                // viewable region.
                g.setColor(Color.BLUE);
                if (sinceLastFPSDraw >= SPAN_BETWEEN_FPS_DRAWS)
                {
                    if (controller.gameIsPaused() == false)
                    {
                        sinceLastFPSDraw = 0;

                        framesPerSecond = "" + (int) (1.0 / (delta));
                    }
                }

                g.drawString(framesPerSecond, 0, (height - 5));

     
                
                //TODO number of waves left

                // FIXME
                //g.drawString("Creeps alive: " + ((TowerDefenseGame) game).getCreepMan().getNumCreeps() + "", 350, 10);

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


            }
        } // A "finally" clause will always execute even if an unhandled
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

    private boolean getPauseStatus(TowerDefenseGame td)
    {
        return td.getStateMac().isPaused();
    }

    private class inputHandler implements KeyListener, MouseListener, MouseMotionListener
    {

        @Override
        public void keyTyped(KeyEvent e)
        {
            controller.keyTyped(e);
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            controller.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            controller.keyReleased(e);
        }

        @Override
        public void mouseClicked(MouseEvent me)
        {
            Point2D click = new Point2D(me.getX(), me.getY());
            //Interrupt the event here, query interactable state from game 
            if (me.getY() < clickableArea)
            {
                

                if (reset.contains(click))
                {
<<<<<<< HEAD
=======
                    
>>>>>>> e95bf10e4d8e648f063bddc74ce56a4581d58b10
                    controller.resetTowerDefense(width, height);
                }
                else if (pause.contains(click))
                {
<<<<<<< HEAD
=======
                    
>>>>>>> e95bf10e4d8e648f063bddc74ce56a4581d58b10
                    controller.pause();
                }
                else if (towerButton.contains(click)) {
                    controller.setAboutToBuildTower();
            }

            }
            
            else
            {
                controller.mouseClicked(me);

            }
        }

        @Override
        public void mousePressed(MouseEvent me)
        {
        }

        @Override
        public void mouseReleased(MouseEvent me)
        {
        }

        @Override
        public void mouseEntered(MouseEvent me)
        {
        }

        @Override
        public void mouseExited(MouseEvent me)
        {
        }

        @Override
        public void mouseDragged(MouseEvent me)
        {
            // okay
        }

        @Override
        public void mouseMoved(MouseEvent me)
        {
            // if the player's mouse is in the UI area...
            if (me.getY() < clickableArea) {
                // ignore
                return;
            }
            
            // otherwise it's in the game, so pass it on
            controller.mouseMoved(new Point2D(me.getX(), me.getY()));
        }
    }
    
    private class SimpleButton {
        Point2D loc;
        Dimension size;
        BufferedImage img;
        
        public SimpleButton(Point2D position, Dimension dim, BufferedImage img) {
            loc = position;
            size = dim;
            this.img = img;
        }
        
        public boolean contains(Point2D pt)
        {
            return pt.getX() > loc.getX()
                    && (loc.getX() + size.width) > pt.getX()
                    && pt.getY() > loc.getY()
                    && (loc.getY() + size.height) > pt.getY();
        }
        
        public void draw(Graphics g) {
            g.setColor(Color.BLUE);
            g.fillRect((int) loc.getX() - 5, (int) loc.getY() - 5, size.width + 10, size.height + 10);
            
            g.drawImage(img, (int) loc.getX(), (int) loc.getY(), null);
        }
    }

    private class UI_Element
    {

        Point2D loc;
        String text;
        Color colorMF;
        Color colorBT;
        Dimension boxSize;
        
        int textHeightSpacing;
        ArrayList<String> texts;
        boolean spaceText;

        public UI_Element(Point2D pt, String str, Color colText, Color colBack, Dimension size, boolean spaceText)
        {
            loc = pt;
            text = str;
            colorMF = colText;
            colorBT = colBack;
            boxSize = size;
            
            texts = new ArrayList<String>();
            texts.add(str);
            
            textHeightSpacing = 0;
            this.spaceText = spaceText;
        }

        public void draw(Graphics g)
        {
            Color old = g.getColor();
            g.setColor(colorMF);
            g.fillRect((int) loc.getX(), (int) loc.getY(), boxSize.width, boxSize.height);
            g.setColor(colorBT);
            
            if (spaceText == true) {
                drawSpacedText(g);
            }
            else {
                g.drawString(text, (int) loc.getX() + 6, (int) loc.getY() + boxSize.height / 2);
            }
            g.setColor(old);
        }
        
        private void drawSpacedText(Graphics g) {
            String toDraw;
            int spaceTextAmount = 20;
           for (int i = 0; i < texts.size(); i++) {
               toDraw = texts.get(i);
               g.drawString(toDraw, (int) loc.getX() + 6, (int) loc.getY() + 25 + spaceTextAmount * i);
           }
        }

        public boolean contains(Point2D pt)
        {
            return pt.getX() > loc.getX()
                    && (loc.getX() + boxSize.width) > pt.getX()
                    && pt.getY() > loc.getY()
                    && (loc.getY() + boxSize.height) > pt.getY();
        }
        
        public void addString(String newText) {
            texts.add(newText);
        }
        
        public void setString(String newText) {
            texts.clear();
            texts.add(newText);
        }
        
        public void clearText() {
            texts.clear();
        }
    }
}
