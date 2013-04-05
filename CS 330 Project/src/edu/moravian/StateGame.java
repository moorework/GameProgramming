package edu.moravian;

import edu.moravian.agent.ChaseAgent;
import edu.moravian.agent.FoodEntity;
import edu.moravian.agent.PlayerAgent;
import edu.moravian.agentManager.ChasePointState;
import edu.moravian.math.Point2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

/**
 * This class encapsulates and holds all of the rules for the game
 *
 * @author moore
 */
public class StateGame implements KeyListener, Drawable, Game
{

    private int worldWidth;
    private int worldHeight;
    private LinkedList<ChaseAgent> agents;
    private LinkedList<FoodEntity> foods;
    private boolean endgame_met;
    private int lives;
    private final PlayerAgent player;
    private final Point2D center_point;
    private Color background;
    private Settings set;
    private boolean debug;

    public StateGame(int worldWidth, int worldHeight)
    {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        center_point = new Point2D(worldWidth / 2, worldHeight / 2);

        agents = new LinkedList<ChaseAgent>();
        // agents.add(new ChaseAgent(center_point));
        agents.add(new ChaseAgent(Point2D.zero));
        agents.add(new ChaseAgent(Settings.getInstance().getSafePoint()));

        agents.get(0).getBehaviorManager().setCurrent_state(new ChasePointState());

        foods = new LinkedList<FoodEntity>();

        foods.add(new FoodEntity(Settings.getInstance().getSafePoint(), 0));
        foods.add(new FoodEntity(center_point, (int) Settings.getInstance().getDefaultFoodReserves() + 1));

        debug = Settings.getInstance().getDebug();

        set = Settings.getInstance();
        set.setWorldSize(new Dimension(1000, 1500));

        endgame_met = false;
        lives = set.getLives();

        player = PlayerAgent.instance();

        background = set.getBackgroundColor();


    }

    @Override
    public void update()
    {
        /*
         * In general we are trying to delegate logic to others 
         * and keep rules to ourself
         */

        for (ChaseAgent a : agents)
        {
            if (a.touching(player.get_dims()) == false)
            {
                //Give the chase agents the scent of the player                 
                a.update();

            }
            else
            {
                //Change the color when the entities touch 


                background = new Color(background.getRGB() + 40);
                a.reset();

                lives -= 1;
            }
            if (lives <= 0)
            {
                this.endgame_met = true;
            }
        }
    }

    @Override
    public void draw(WorldGraphics2D Wg2D)
    {
        Wg2D.setColor(background);
        Wg2D.fillRect(new Point2D(0, 0), worldWidth, worldHeight);

        player.draw(Wg2D);

        Wg2D.setColor(Color.WHITE);
        Wg2D.drawString("" + lives, new Point2D(worldWidth - 10, worldHeight - 10), Color.WHITE);

        for (FoodEntity food : foods)
        {
            food.draw(Wg2D);
        }

        for (ChaseAgent a : agents)
        {
            a.draw(Wg2D);
            if (debug)
            {
                Color old = Wg2D.getColor();
                Wg2D.setColor(Color.pink);
                Point2D temp = a.getCenter().scalePlus(25 + a.getDirection().magnitudeSq(), a.getDirection());
                Wg2D.drawLine(a.getCenter(), temp);
                Wg2D.setColor(old);
            }
        }

    }

    @Override
    public boolean done()
    {
        return endgame_met;
    }

    @Override
    public void keyTyped(KeyEvent ke)
    {
    }

    /**
     * This is where we handle input. If any future keyboard input needs to be
     * added, here is the place.
     *
     * @param ke
     */
    @Override
    public void keyPressed(KeyEvent ke)
    {
        switch (ke.getKeyCode())
        {
            case KeyEvent.VK_D:
                player.moveRight();
                break;
            case KeyEvent.VK_A:
                player.moveLeft();
                break;
            case KeyEvent.VK_W:
                player.moveUp();
                break;
            case KeyEvent.VK_S:
                player.moveDown();
                break;
            case KeyEvent.VK_Z:
                debug = !debug;
            default:

        }
    }

    @Override
    public void keyReleased(KeyEvent ke)
    {
    }
}
