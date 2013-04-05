package edu.moravian.graphics;

/**
 * Describes where a Drawable object's Sprite should be drawn in relation to
 * its position.
 * 
 * AT_POINT - Sprite should be drawn in the default manner (from upper left corner)
 *            at the provided point.
 * CENTER - Sprite should be drawn with the provided point at its center.
 * CENTER_X - Sprite should be drawn with its center drawn at the center of the
 *            position but only on the X-axis. Y-Axis still drawn in default manner.
 * 
 * @author Myles
 */
public enum DrawLocation {
    AT_POINT, CENTER, CENTER_X, CENTER_Y
}
