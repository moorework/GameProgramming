package cs.pkg330.project.graphics.filters;

import cs.pkg330.project.graphics.Sprite;
import edu.moravian.agents.MobileAgent;

/**
 *
 * @author Myles
 */
public class AmarnaFilter implements SpriteFilter {
    // the heading that describes the Sprites being filtered
    private MobileAgent subject;
    
    /**
     * Create a new AmarnaFilter that will filter Sprites with respect to the
     * provided heading.
     * 
     * It is recommended that one rely on pass-by-reference here rather than
     * constantly recreating an AmarnaFilter as needed.
     * 
     * @param heading the heading that we are to flip with respect to
     */
    public AmarnaFilter(MobileAgent agent) {
        subject = agent;
    }

    /**
     * Filter the Sprite by flipping it to align with its heading along the X-axis.
     * 
     * @param toFilter the Sprite to filter
     * @return the filtered Sprite
     */
    @Override
    public Sprite filterSprite(Sprite toFilter) {
        //TODO filter currently assumes all images are naturally right-facing
        
        if (subject.getHeading().getX() < 0) { // if the heading is to the left...
            return toFilter.getFlippedHoriz(); // flip the Sprite to reflect that
        }
        
        // else we need not filter the Sprite, so we can return it unchanged
        return toFilter;
    }
    
}
