package edu.moravian.util;

import edu.moravian.graphics.Sprite;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.imageio.ImageIO;

/**
 * A utility that statically reads in image files from external directories.
 *
 * @author myles
 */
public class GraphicsDataParser {
    public final static String[] acceptedImageExtensions = {"gif", "png", "bmp", "jpeg", "jpg"};

    /**
     * Reads in all of the image files located in a certain directory and
     * returns them as a Collection of Sprite objects.
     *
     * @param filePath the directory to read the Sprites from
     * @return a Collection of the Sprites read in from the provided filePath
     */
    public static Collection<Sprite> readInSprites(String filePath) {
        // the files that we will need to read in from the provided directory
        ArrayList<File> filesToReadIn = new ArrayList<File>();
        // the target directory that we will be reading from
        File targetFile = new File(filePath);

        // It's just possible that our client will pass in a file path for a
        // single image, rather than a directory, and we should support that.
        // So we will double check that the file we're reading from is a
        // directory or not and, based on that, act accordingly.
        if (targetFile.isDirectory()) { // if the file is a directory...
            // then for every file in the directory...
            for (File fileToAdd : Arrays.asList(targetFile.listFiles())) {
                // pull out a String representation of the File
                String currFileStr = fileToAdd.getName();
                
                // for every image extension that we might feasible expect we'll
                // check it against our current file to determine if it's a
                // feasible image file to read-in; this will clear out any OS
                // cruft (such as OS X's DS_STORE files) and avoid errors
                for (String s : acceptedImageExtensions) {
                    if (currFileStr.endsWith(s)) { // if the file is a recognized image
                        // record it to be read in later
                        filesToReadIn.add(fileToAdd);
                    }
                } // end for

            } // end for
        } else { // else the file is a specific, individual image
            // in which case we can simply add it to our list of files to read in
            filesToReadIn.add(targetFile);
        }
        
        // a collection of the Sprites that we'll be returning to the client
        ArrayList<Sprite> output = new ArrayList<Sprite>();

        Sprite toAdd; // the sprite to add to our output
        for (File currFile : filesToReadIn) { // for each of the files we need to read in...
            
            try { // cross your fingers!
                // wrap the file is a FileInputStream - provides more control in
                // the event of screw ups
                FileInputStream fis = new FileInputStream(currFile);
                // read in the image and store it as a Sprite
                toAdd = new Sprite(ImageIO.read(fis));
                output.add(toAdd); // add it to our output array to be returned
            }
            catch (IOException ex) {
                System.out.println("GraphicsDataParser failed to read-in data: " + currFile);
                ex.printStackTrace();
                //FIXME
                System.exit(1);
            }
        }

        return output;
    }
}
