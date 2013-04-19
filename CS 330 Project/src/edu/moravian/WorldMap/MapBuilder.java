package edu.moravian.WorldMap;

import edu.moravian.math.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
    private static int cachedAppearenceID;
    private static Point2D cachedStartingPoint;
    private static Point2D cachedEndingPoint;

    protected static ArrayList<ArrayList<WorldCell>> getMapRepresentation(String mapDirLocation) throws FileNotFoundException
      {

        //TODO test if there is no file

        //TODO double arraylist?
        File f = new File(mapDirLocation);

        Scanner s = new Scanner(f);


        ArrayList<ArrayList<WorldCell>> ret = new ArrayList<ArrayList<WorldCell>>();

        String first = s.nextLine();

        ret.add(translate(first));

        while (s.hasNextLine())
          {
            String temp = s.nextLine();
            if (temp.length() != first.length())
              {
                throw new IllegalArgumentException("Lines must all contain same number of charactars");
              }

            ret.add(translate(temp));

          }

        //TODO pull out appearenceID at some point; last line of file?
        // TODO pull out starting and ending points of the map

        return ret;
      }

    protected static int getAppearenceID(String mapDirLocation)
      {

        return cachedAppearenceID;
      }

    protected static Point2D getStartingPoint(String mapDirLocation)
      {

        return cachedStartingPoint;
      }

    protected static Point2D getEndingPoint(String mapDirLocation)
      {

        return cachedEndingPoint;
      }

    //TODO test round one 
    private static ArrayList<WorldCell> translate(String first)
      {
        //TODO store translation values elsewhere?
        ArrayList<WorldCell> ret = new ArrayList<WorldCell>();
        for (char c : first.toCharArray())
          {
            switch (c)
              {
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
  }
//TODO make a map verifier 