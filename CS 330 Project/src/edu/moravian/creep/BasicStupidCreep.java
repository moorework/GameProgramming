/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.creep;

import edu.moravian.graphics.WorldGraphics2D;
import edu.moravian.math.Point2D;
import edu.moravian.math.Vector2D;
import edu.moravian.projectile.Projectile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.util.LinkedList;

/**
 *
 * @author moore
 */
public class BasicStupidCreep implements Creep {

    //TODO get this from settings
    private Point2D pos;
    private Vector2D dir;
    private Shape Dims;

    public BasicStupidCreep(Point2D pos, Vector2D dir, Shape Dims) {
        this.pos = pos;
        this.dir = dir;
        this.Dims = Dims;
    }

    @Override
    public Shape get_dims() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Point2D getPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector2D getDirection() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

  

    public void draw(WorldGraphics2D g2d) {
        g2d.fillRect(pos, new Dimension(50, 50), Color.red);
    }

    public void update() {
        pos = pos.scalePlus(1.1, dir);
    }

    @Override
    public void respondToColission(LinkedList<Projectile> projs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
