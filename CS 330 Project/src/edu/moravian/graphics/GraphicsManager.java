package edu.moravian.graphics;

import edu.moravian.graphics.filters.SpriteFilter;
import edu.moravian.math.Point2D;
import java.util.ArrayList;

/**
 *
 * @author Myles
 */
public class GraphicsManager {
    private final String EXTERNAL_GRAPHICS_DATA_LOC = "graphicsData.properties";
    
    // the objects that we're to fillRect
    private ArrayList<Drawable> graphics;
    private GraphicsDatabase graphicsDatabase;
    // the filters that will be applied to everything we have
    private ArrayList<SpriteFilter> globalFilters;
    
    /**
     * Create a new GraphicsManager.
     */
    public GraphicsManager() {
        // instantiate the collections holding the things we are to fillRect
        graphics = new ArrayList<Drawable>();
        // instantiate the collection holding the global filters to be used on all images
        globalFilters = new ArrayList<SpriteFilter>();
        
        graphicsDatabase = new GraphicsDatabase(EXTERNAL_GRAPHICS_DATA_LOC);
    }
    
    /**
     * Add a new Drawable object to the Manager. This object will be included in
     * all future fillRect cycles.
     * 
     * @param newDrawObject the new Drawable object to be managed
     */
    public void addDrawable(Drawable newDrawObject) {
        // add the new Drawable object to our collection of them
        graphics.add(newDrawObject);
    }
    
    public void clearManager() {
        graphics.clear();
        globalFilters.clear();
    }
    
    /**
     * Remove a Drawable object from the Manager (if it is present). If the object
     * is not present in the manager nothing is done.
     * 
     * @param toRemove the Drawable object to remove
     */
    public void removeDrawable(Drawable toRemove) {
        // the ArrayList will remove the object if present and ignore the request
        // otherwise
        int removalIndex = -1;
        int toRemoveID = toRemove.getGraphicsID();
        Point2D toRemovePos = toRemove.getPos();
        Drawable currDrawable;
        int currDrawID;
        Point2D currDrawPos;
        for (int i = 0; i < graphics.size(); i++) {
            currDrawable = graphics.get(i);
            currDrawID = currDrawable.getGraphicsID();
            currDrawPos = currDrawable.getPos();
            
            if (currDrawID != toRemoveID) {
                return;
            }
            
            if (currDrawPos.equals(toRemovePos)) {
                removalIndex = i;
            }
        }
        
        if (removalIndex >= 0) {
            graphics.remove(removalIndex);
        }
    }
    
    /**
     * Add a new Drawable object to the Manager which will be the lowest drawing layer;
     * all other Drawable objects will be drawn on top of this one. This contract
     * is broken if this method is again called later with a new Drawable object.
     * 
     * @param newDrawObject the new Object to be drawn at the lowest layer
     */
    public void addDrawableToLowestLayer(Drawable newDrawObject) {
        // add the new Drawable object to the front of the array so that it will
        // always be the first Drawable object pulled from the collection in order
        // to be drawn
        graphics.add(0, newDrawObject);
    }
    
    /**
     * Add a new filter to be applied to all of the visual elements being managed
     * by the Graphics Manager.
     * 
     * @param newFilter the new GraphicsManager-wide filter
     */
    public void addFilter(SpriteFilter newFilter) {
        // add the new filter to our Collection
        globalFilters.add(newFilter);
    }
    
    /**
     * Remove a SpriteFilter from the GraphicsManager. If the SpriteFilter is not
     * present in the GraphicsManager, nothing is done.
     * 
     * @param toRemove the SpriteFilter to remove from the GM
     */
    public void removeFilter(SpriteFilter toRemove) {
        // remove the SpriteFilter; ArrayList will take care of the details,
        // including ignoring the method call if we do not, in fact, have that
        // SpriteFilter
        globalFilters.remove(toRemove);
    }
    
    /**
     * Clear the GraphicsManager of its SpriteFilters such that no filters will
     * be applied during future drawing cycles.
     */
    public void clearFilters() {
        // replace our Collection with an empty one
        globalFilters = new ArrayList<SpriteFilter>();
    }
    
    /**
     * Draw the resources being managed by the GraphicsManager to a provided
     * World2D object. This represents a single fillRect cycle.
     * 
     * @param w2d the World2D object to fillRect to
     */
    public void draw(WorldGraphics2D w2d) {
        /*
         * We will iterate through all of our graphical resources in-order (as they
         * may be deliberately layered) and, for each of those objects, we will
         * pass it through each of our filters and then fillRect it to the World2D
         * object. How we fillRect it to the World2D object will be defined by the
         * Drawable object using an enum.
         */
        
        
        
        Sprite sprite; // the Drawable graphic that will be drawn to the World2D object
        
        int imageID;
        // for all of the Drawable objects...
        for (Drawable toDraw : graphics) {
            imageID = toDraw.getGraphicsID();
            // retrieve the frame to be drawn to the World2D object
            sprite = graphicsDatabase.getImage(imageID);
            
        
            
            // for each of our filters...
            for (SpriteFilter filter : globalFilters) {
                // apply the filter to the Sprite
                sprite = filter.filterSprite(sprite);
            }
            
            // retrieve the Drawable's DrawLocation so that we know how it would
            // like to be drawn
            DrawLocation drawLoc = toDraw.getDrawLocation();
            // retrieve the Drawable's position so that we know where to fillRect it
            Point2D drawPoint = toDraw.getPos();
            
            // based on the DrawLocation that we pulled from the 
            switch (drawLoc) {
                case AT_POINT: // fillRect the Sprite normally at the Drawable's position
                    w2d.drawImage(sprite, drawPoint);
                    break; // prevent fall-through
                case CENTER: // fillRect the Sprite such that the Position describes
                             // its center-point
                    
                    w2d.drawImageWithPointAtCenter(sprite, drawPoint);
                    
                    break; // prevent fall-through
                case CENTER_X:
                    w2d.drawImageCenterXAxis(sprite, drawPoint);
                    break;
                case CENTER_Y:
                    w2d.drawImageCenterYAxis(sprite, drawPoint);
                default:
            }
        }
    }
}
