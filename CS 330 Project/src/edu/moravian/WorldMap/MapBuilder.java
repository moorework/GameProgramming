package edu.moravian.WorldMap;

import edu.moravian.math.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
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
    private static LinkedList<PathCell> cachedStartingPoints;
    private static LinkedList<PathCell> cachedEndingPoints;

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
            if (temp.length() != first.length()){
                throw new IllegalArgumentException("Lines must all contain same number of characters");
            }
            
            ret.add(translate(temp));

          }

        //TODO pull out appearenceID at some point; last line of file?
        // TODO pull out starting and ending points of the map

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
        for (char c : first.toCharArray())
          {
            switch (c)
              {
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
  }
//TODO make a map verifier 
