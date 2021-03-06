package edu.moravian.graphics;

import edu.moravian.util.GraphicsDataParser;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author myles
 */
public class GraphicsDatabase
  {

    private final String NUM_IMAGES = "numImages";
    private final int INIT_IMAGE_KEY = 100;
    private Properties properties;
    private HashMap<Integer, Sprite> graphicsTable;

    public GraphicsDatabase(String graphicsDataDirectory)
      {
        properties = new Properties();

        try
          {
            InputStream is = new FileInputStream(graphicsDataDirectory);
            properties.load(is);
          }
        catch (Exception ex)
          {
            System.out.println("Failed to read in GraphicsDatabase data: " + ex);
          }

        graphicsTable = new HashMap<Integer, Sprite>();
        loadGraphicsData();
      }

    private void loadGraphicsData()
      {
        String numImagesVal = properties.getProperty(NUM_IMAGES);
        int numImages = Integer.parseInt(numImagesVal);

        Integer key = INIT_IMAGE_KEY;
        String imageDirectory;
        ArrayList<Sprite> readInImage;
        Sprite sprite;


        for (int i = 0; i < numImages; i++)
          {

            key = key + i;

            imageDirectory = properties.getProperty(key + "");



            sprite = GraphicsDataParser.readInSprite(imageDirectory);


            graphicsTable.put(key, sprite);

          }
      }

    public Sprite getImage(int imageID)
      {

        
        //return graphicsTable.get(imageID);
        if (graphicsTable.get(imageID) == null)
          {
              System.out.println("ERROR CANNOT FIND IMAGE");
            return GraphicsDataParser.readInSprite("error.png");
          }
        else
          {
            return graphicsTable.get(imageID);
          }
      }
  }
