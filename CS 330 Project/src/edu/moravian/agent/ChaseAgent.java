package edu.moravian.agent;

import edu.moravian.Settings;

import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.agentManager.ChaseStateMachine;

import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import edu.moravian.util.Timer;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This is an agent that chases the player agent.
 *
 * @author James Moore (moore.work@live.com)
 */
public class ChaseAgent implements Entity {

    Rectangle appearance;
    Vector2D direction;
    Point2D original_position;
    Point2D position;
    int units_food;
    ChaseStateMachine behaviorManager;
    private final static Point2D default_position =
            new Point2D(Settings.getInstance().getSafePoint());

    /**
     * This will create a chase agent in a default position
     */
    public ChaseAgent() {
        position = default_position;
        original_position = default_position;
        direction = new Vector2D();
        init_common();
    }

    /**
     * This allows you to specify a position for the chase agent
     *
     * @param position
     */
    public ChaseAgent(Point2D position) {
        this.position = position;
        this.original_position = new Point2D(position);
        direction = new Vector2D();
        init_common();
    }

    private void init_common() {
        appearance = new Rectangle(100, 100);
    }

    @Override
    public void draw(WorldGraphics2D g2d) {
        Color old = g2d.getColor();
        //g2d.rotate(0.0, PlayerAgent., direction.getY());
        g2d.setColor(Color.pink);
        g2d.fillRect(position, 100, 100);

        g2d.setColor(old);
    }

    public void update() {

        behaviorManager.update();

    }

    @Override
    public Shape get_dims() {
        return appearance.getFrame();
    }

    @Override
    public boolean touching(Shape shape) {
        if (shape.intersects(position.getX(), position.getY(),appearance.getWidth(), appearance.getHeight())) {
            return true;
        }
        return false;
    }

    public ChaseStateMachine getBehaviorManager() {
        return behaviorManager;
    }

    public Point2D getPosition() {
        return position;
    }

    public Vector2D getDirection() {
        return direction;
    }
}
