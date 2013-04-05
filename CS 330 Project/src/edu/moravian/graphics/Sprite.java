package edu.moravian.graphics;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.WritableRaster;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A Sprite is a two-dimensional pre-rendered graphic that IS-A(n) Image.
 *
 * @author Myles
 */
public class Sprite extends Image {
    private BufferedImage sprite;

    /**
     * Create a new Sprite using the graphic located at the provided directory
     * path.
     *
     * @param localFilePath a file path to the image to create the Sprite with
     */
    public Sprite(String localFilePath)
    {
        try
        {
            // read in the image located at the provi file path; this will be
            // the image data for the Sprite
            sprite = ImageIO.read(getClass().getResource(localFilePath));
        } catch (IOException ex)
        {
            System.out.println("Error reading-in Sprite file: " + localFilePath);

            System.exit(1);
        }
    }

    /**
     * Create a Sprite from a pre-existing Image object. Note that two will be
     * distinct from each other. They will be equal to each other but seperate
     * objects.
     *
     * @param img the image to use as the model for this Sprite
     */
    public Sprite(Image img)
    {
        // retrieve a scaled instance of the image that has not, in fact, been scaled
        Image imgData = img.getScaledInstance(img.getWidth(null), -1, Image.SCALE_SMOOTH);

        // create a new, blank BufferedImage using the image data as its source
        sprite = new BufferedImage(imgData.getWidth(null),
                imgData.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);

        // draw the original image onto the new BufferedImage
        sprite.getGraphics().drawImage(imgData, 0, 0, null);

        // sprite is now equal to the original image but totally distinct from it
    }

    /**
     * Retrieve the width of the Sprite.
     *
     * @return the width of the Sprite
     */
    public int getWidth()
    {
        return sprite.getWidth(null);
    }

    /**
     * Retrieve the height of the Sprite.
     *
     * @return the height of the Sprite
     */
    public int getHeight()
    {
        return sprite.getHeight(null);
    }

    public Sprite getFlippedHoriz()
    {
        BufferedImage toFlip = sprite;
        // use an affine transformation to flip the image horziontally

        // we'll scale the image without altering its size
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        // translate the image in which we'll flip the image by concatenating
        // the negation of the image width, effectively swapping the x-axis
        tx.translate(-this.getWidth(), 0);
        // the AffineTransformOp is what will actually perform the flip
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        // flip the image
        BufferedImage toReturn = new BufferedImage(toFlip.getWidth(), toFlip.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        
        toReturn = op.filter(toFlip, toReturn);
        
        return new Sprite(toReturn);
    }

    public void flipHoriz()
    {
        Sprite image = getFlippedHoriz();
        
        Image imgData = image.getScaledInstance(image.getWidth(), -1, Image.SCALE_SMOOTH);

        BufferedImage bufferedImage = new BufferedImage(imgData.getWidth(null), imgData.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);

        bufferedImage.getGraphics().drawImage(imgData, 0, 0, null);

        sprite = bufferedImage;
    }

    /**
     * Determines the width of the image. If the width is not yet known, this
     * method returns -1 and the specified ImageObserver object is notified
     * later.
     *
     * @param observer an object waiting for the image to be loaded.
     * @return the width of this image, or -1 if the width is not yet known.
     */
    @Override
    public int getWidth(ImageObserver observer)
    {
        return sprite.getWidth(observer);
    }

    /**
     * Determines the height of the image. If the height is not yet known, this
     * method returns -1 and the specified ImageObserver object is notified
     * later.
     *
     * @param observer an object waiting for the image to be loaded.
     * @return the height of this image, or -1 if the height is not yet known.
     */
    @Override
    public int getHeight(ImageObserver observer)
    {
        return sprite.getWidth(observer);
    }

    /**
     * Gets the object that produces the pixels for the image. This method is
     * called by the image filtering classes and by methods that perform image
     * conversion and scaling.
     *
     * @return the image producer that produces the pixels for this image.
     */
    @Override
    public ImageProducer getSource()
    {
        return sprite.getSource();
    }

    /**
     * Creates a graphics context for drawing to an off-screen image. This
     * method can only be called for off-screen images.
     *
     * @return a graphics context to draw to the off-screen image.
     */
    @Override
    public Graphics getGraphics()
    {
        return sprite.getGraphics();
    }

    /**
     * Gets a property of this image by name. Individual property names are
     * defined by the various image formats. If a property is not defined for a
     * particular image, this method returns the UndefinedProperty object.
     *
     * If the properties for this image are not yet known, this method returns
     * null, and the ImageObserver object is notified later.
     *
     * The property name "comment" should be used to store an optional comment
     * which can be presented to the application as a description of the image,
     * its source, or its author.
     *
     * @param name a property name.
     * @param observer an object waiting for this image to be loaded.
     * @return the value of the named property.
     */
    @Override
    public Object getProperty(String name, ImageObserver observer)
    {
        return sprite.getProperty(name, observer);
    }
    
    public BufferedImage getBackingImage() {
        return sprite;
    }
}
