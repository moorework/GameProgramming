package edu.moravian.WorldMap;

import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import edu.moravian.Settings;
import edu.moravian.math.Point2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

/**
 *
 * @author myles
 */
public class MapBuilder {

    private static final char PATH_CHAR = 'P';
    private static final char TOWER_CHAR = 'T';
    private static final char START_CHAR = 'S';
    private static final char END_CHAR = 'E';
    private static int cachedAppearenceID;
    private static LinkedList<PathCell> cachedStartingPoints;
    private static LinkedList<PathCell> cachedEndingPoints;

    public static void main(String[] args) throws FileNotFoundException, IOException {


        BufferedImage buf = createImageReppresentation(getMapRepresentation("maps/basicMap"), new Dimension(800, 600));

        File outputFile = new File("image.png");
        ImageIO.write(buf, "PNG", outputFile);
    }

    protected static ArrayList<ArrayList<WorldCell>> getMapRepresentation(String mapDirLocation) throws FileNotFoundException {

        //TODO test if there is no file

        //TODO double arraylist?
        File f = new File(mapDirLocation);

        Scanner s = new Scanner(f);


        ArrayList<ArrayList<WorldCell>> ret = new ArrayList<ArrayList<WorldCell>>();

        String first = s.nextLine().toUpperCase().trim();

        ret.add(translate(first));

        while (s.hasNextLine()) {
            String temp = s.nextLine().toUpperCase().trim();
            if (temp.length() != first.length()) {
                throw new IllegalArgumentException("Lines must all contain same number of charactars");
            }

            ret.add(translate(temp));

        }

        //TODO pull out appearenceID at some point; last line of file?
//TODO move the start off screen?

        return ret;
    }

    protected static int getAppearenceID()
      {

        return cachedAppearenceID;
    }

    protected static LinkedList<PathCell> getStartingPoints()
      {

        return cachedStartingPoints;
      }

    protected static LinkedList<PathCell> getEndingPoints()
      {

        return cachedEndingPoints;
      }

    //TODO test round one 
    private static ArrayList<WorldCell> translate(String first)
      {
          
         TowerCell tCell;
         PathCell pCell;
        //TODO store translation values elsewhere?
        ArrayList<WorldCell> ret = new ArrayList<WorldCell>();
        for (char c : first.toCharArray()) {
            switch (c) {
                case PATH_CHAR:
                    pCell = new PathCell();
                    ret.add(pCell);
                    break;
                case TOWER_CHAR:
                    tCell = new TowerCell();
                    ret.add(tCell);
                    break;
                case START_CHAR:
                    pCell = new StartCell();
                    cachedStartingPoints.add(pCell);
                    ret.add(pCell);
                    break;
                case END_CHAR:
                    pCell = new EndCell();
                    cachedEndingPoints.add(pCell);
                    ret.add(pCell);
                    break;
                default:
                    throw new IllegalArgumentException("Data file must be composed of legal cells");
            }
        }
        return ret;
    }

    //TODO draw grid lines?
    
    /**
     * Creates an image representation of the map based on the map given
     * @param ret
     * @return 
     */
    private static BufferedImage createImageReppresentation(ArrayList<ArrayList<WorldCell>> ret, Dimension res) {
      
        int blockWidth = res.width / ret.get(0).size();
        int blockHeight = res.height / ret.size();


        BufferedImage buf = new BufferedImage(res.width, res.height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < ret.size(); i++) {
            for (int j = 0; j < ret.get(i).size(); j++) {


                for (int k = 0; k < blockWidth; k++) {
                    for (int l = 0; l < blockHeight; l++) {
                        buf.setRGB(k + j * blockWidth, l + i * blockHeight, translateColor(ret.get(i).get(j)));
                    }
                }


            }
        }

        return buf;
    }

    private static int translateColor(WorldCell t) {
        //FUTURE make this data driven 
        if (t instanceof PathCell) {
            return Color.red.getRed();
        } else if (t instanceof StartCell) {
            return Color.black.getRGB();
        } else if (t instanceof EndCell) {

            return Color.ORANGE.getRGB();
        } else if (t instanceof TowerCell) {

            return Color.white.getRGB();
        } else {
            return -1;

        }
    }
}
//TODO make a map verifier 
//FUTURE add comments inside the data file 
