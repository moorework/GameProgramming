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
    private static Point2D cachedStartingPoint;
    private static Point2D cachedEndingPoint;

    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        Settings.getInstance().setResolution(new Dimension(800,600));
        
        BufferedImage buf = createImageReppresentation(getMapRepresentation("foo"));
     
        File outputFile = new File("image.png");
    ImageIO.write(buf, "PNG", outputFile);
    }
            
    
    
    protected static ArrayList<ArrayList<WorldCell>> getMapRepresentation(String mapDirLocation) throws FileNotFoundException {

        //TODO test if there is no file

        //TODO double arraylist?
        File f = new File(mapDirLocation);

        Scanner s = new Scanner(f);


        ArrayList<ArrayList<WorldCell>> ret = new ArrayList<ArrayList<WorldCell>>();

        String first = s.nextLine();

        ret.add(translate(first));

        while (s.hasNextLine()) {
            String temp = s.nextLine();
            if (temp.length() != first.length()) {
                throw new IllegalArgumentException("Lines must all contain same number of charactars");
            }

            ret.add(translate(temp));

        }

        //TODO pull out appearenceID at some point; last line of file?


        return ret;
    }

    protected static int getAppearenceID(String mapDirLocation) {

        return cachedAppearenceID;
    }

    protected static Point2D getStartingPoint(String mapDirLocation) {

        return cachedStartingPoint;
    }

    protected static Point2D getEndingPoint(String mapDirLocation) {

        return cachedEndingPoint;
    }

    //TODO test round one 
    private static ArrayList<WorldCell> translate(String first) {
        //TODO store translation values elsewhere?
        ArrayList<WorldCell> ret = new ArrayList<WorldCell>();
        for (char c : first.toCharArray()) {
            switch (c) {
                case PATH_CHAR:
                    ret.add(new PathCell());
                    break;
                case TOWER_CHAR:
                    ret.add(new TowerCell());
                    break;
                case START_CHAR:
                    ret.add(new StartCell());
                    break;
                case END_CHAR:
                    ret.add(new EndCell());
                    break;
                default:
                    throw new IllegalArgumentException("Data file must be composed of legal cells");
            }
        }
        return ret;
    }

    private static BufferedImage createImageReppresentation(ArrayList<ArrayList<WorldCell>> ret) {
        Dimension res = Settings.getInstance().getResolution();
        int blockWidth = res.width / ret.get(0).size();
        int blockHeight = res.height / ret.get(0).size();


        BufferedImage buf = new BufferedImage(res.width, res.height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < ret.size(); i++) {
            for (int j = 0; j < ret.get(i).size(); j++) {


                for (int k = 0; k < blockWidth; k++) {
                    for (int l = 0; l < blockHeight; l++) {
                        buf.setRGB(k + i * blockWidth, l + j * blockHeight, translateColor(ret.get(i).get(j)));
                    }
                }


            }
        }

        return buf;
    }

    private static int translateColor(WorldCell t) {
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