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
public class MapBuilder
{

    private static final char PATH_CHAR = 'P';
    private static final char TOWER_CHAR = 'T';
    private static final char START_CHAR = 'S';
    private static final char END_CHAR = 'E';

    public static void initMapData(ArrayList<ArrayList<WorldCell>> mapData, double width, double height) throws Exception
    {
<<<<<<< HEAD


        BufferedImage buf = createImageReppresentation(mapData, new Dimension((int) width, (int) height));
=======
>>>>>>> e95bf10e4d8e648f063bddc74ce56a4581d58b10

        
        BufferedImage buf = createImageReppresentation(mapData, new Dimension((int) width, (int) height));
        
        File outputFile = new File("image.png");
        ImageIO.write(buf, "PNG", outputFile);
    }

    public static ArrayList<ArrayList<WorldCell>> getMapRepresentation(String mapDirLocation) throws FileNotFoundException
    {

        //TODO test if there is no file

        //TODO double arraylist?
        File f = new File(mapDirLocation);

        Scanner s = new Scanner(f);


        ArrayList<ArrayList<WorldCell>> ret = new ArrayList<ArrayList<WorldCell>>();

        String first = s.nextLine().toUpperCase().trim();
        ret.add(translate(first));
        
        while (s.hasNextLine())
        {
            String temp = s.nextLine().toUpperCase().trim();
<<<<<<< HEAD
            System.out.println(temp);
=======
>>>>>>> e95bf10e4d8e648f063bddc74ce56a4581d58b10
            
            if (temp.length() != first.length())
            {
                throw new IllegalArgumentException("Lines must all contain same number of charactars");
            }

            ret.add(translate(temp));
        }

        return ret;
    }

    private static ArrayList<WorldCell> translate(String first)
    {

        TowerCell tCell;
        PathCell pCell;
        //TODO store translation values elsewhere?
        ArrayList<WorldCell> ret = new ArrayList<WorldCell>();
        for (char c : first.toCharArray())
        {
            
            switch (c)
            {
                case PATH_CHAR:
                    pCell = new PathCell(false, false);
                    ret.add(pCell);
                    break;
                case TOWER_CHAR:
                    tCell = new TowerCell();
                    ret.add(tCell);
                    break;
                case START_CHAR:
                    pCell = new PathCell(true, false);
                    ret.add(pCell);
                    break;
                case END_CHAR:
                    pCell = new PathCell(false, true);
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
     *
     * @param ret
     * @return
     */
        public static BufferedImage createImageReppresentation(ArrayList<ArrayList<WorldCell>> ret, Dimension res)
    {
        
        int blockHeight = res.height / ret.size();
        int blockWidth = blockHeight;

        BufferedImage buf = new BufferedImage(res.width, res.height, BufferedImage.TYPE_INT_RGB);
        
        for (int i = 0; i < ret.size(); i++)
        {
            for (int j = 0; j < ret.get(i).size(); j++)
            {


                for (int k = 0; k < blockWidth; k++)
                {
                    for (int l = 0; l < blockHeight; l++)
                    {
                        buf.setRGB(k + j * blockWidth, l + i * blockHeight, translateColor(ret.get(i).get(j)));
                    }
                }


            }
        }

        return buf;
    }

    private static int translateColor(WorldCell t)
    {
        PathCell p;
        
        if (t.isPathable() == true) {
            p = (PathCell) t;
<<<<<<< HEAD
        }
        else {
            return Color.green.getRGB();
        }
        
        //FUTURE make this data driven 
        if (p.isSpawn() == true)
        {
            return Color.black.getRGB();
        }
        else if (p.isEnd() == true) {
            return Color.ORANGE.getRGB();
        }
        else
        {
            return Color.GRAY.getRGB();
=======
        }
        else {
            return new Color(0,123,12).getRGB();
        }
        
        //FUTURE make this data driven 
        if (p.isSpawn() == true)
        {
            return new Color(119,81,17).getRGB();
        }
        else if (p.isEnd() == true) {
            return new Color(119,81,17).getRGB();
        }
        else
        {
            return new Color(119,81,17).getRGB();
>>>>>>> e95bf10e4d8e648f063bddc74ce56a4581d58b10
        }
    }
}
//TODO make a map verifier 
//FUTURE add comments inside the data file 

