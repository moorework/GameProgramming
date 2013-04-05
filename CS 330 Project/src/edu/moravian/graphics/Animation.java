package edu.moravian.graphics;

import edu.moravian.graphics.filters.SpriteFilter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * 
 * @author Myles
 */
public class Animation {
    private ArrayList<Sprite> frames;
    private ArrayList<SpriteFilter> filters;
    private int currFrame;
    
    // describes how much time must elapse before we transition between frames
    // in the animation. Measured in seconds;
    private double timeBetweenFrameUpdates;
    // describes how long it has been since our last frame update. Measured in seconds;
    private double timeElapsedSinceFrameUpdate;
    
    public Animation(Collection<Sprite> frames, double timeBetweenFrameUpdates) {
        this.frames = (ArrayList) frames;
        currFrame = 0;
        
        filters = new ArrayList<SpriteFilter>();

        this.timeBetweenFrameUpdates = (frames.size() * timeBetweenFrameUpdates) / 100.0;
    }
    
    public Animation(Animation subject) {
        this.frames = (ArrayList) subject.frames;
        this.filters = (ArrayList) subject.filters;
        
        currFrame = subject.currFrame;
        
        timeBetweenFrameUpdates = subject.timeBetweenFrameUpdates;
        timeElapsedSinceFrameUpdate = subject.timeElapsedSinceFrameUpdate;
    }
    
    public void update(double delta) {
        // by default we will not draw the next frame of the animation yet
        boolean nextFrame = false;

        timeElapsedSinceFrameUpdate += delta;
        
        // if the there's been adequate passage of time since the last update
        // of the animation cycle...
        if (timeElapsedSinceFrameUpdate > timeBetweenFrameUpdates) {
            nextFrame = true; // we'll draw the next frame of animation
            // we'll wrap the counter back around
            timeElapsedSinceFrameUpdate -= timeBetweenFrameUpdates;
        }

        // if we've reached the end of the cycle and we're drawing the next
        // frame of animation...
        if (currFrame >= (frames.size() - 1) && nextFrame == true) {
            currFrame = 0; // restart the cycle
        } // else if we're updating the frame in the midst of the cycle...
        else if (nextFrame == true) {
            // move onto the next frame of animation
            currFrame++;
        }
    }
    
    public Sprite getCurrFrame() {
        Sprite output = new Sprite(frames.get(currFrame));
        for (SpriteFilter filter : filters) { // for each filter...
            // filter the Sprite
            output = filter.filterSprite(output);
        }
        
        return output;
    }
    
    public void addFilter(SpriteFilter newFilter) {
        filters.add(newFilter);
    }
    
    public void removeFilter(SpriteFilter toRemove) {
            filters.remove(toRemove);
    }
}
