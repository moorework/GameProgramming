package edu.moravian.WorldMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author myles
 */
public class MapBuilder {

    protected static ArrayList<ArrayList<WorldCell>> getMapRepresentation(String mapDirLocation) throws FileNotFoundException
    {
        
        //TODO test if there is no file
        
        //TODO double arraylist?
        File f = new File(mapDirLocation);
        
        Scanner s = new Scanner(f);
        
        
        ArrayList<ArrayList<WorldCell>> ret = new ArrayList<ArrayList<WorldCell>>();
        
        String first = s.nextLine();
        
        ret.add(translate(first));
        
        while(s.hasNextLine()){
            String temp = s.nextLine();
            if (temp.length() != first.length()){
                throw new IllegalArgumentException("Lines must all contain same number of charictars");
            }
            
            ret.add(translate(temp));
            
        }
        
        return null;
    }
    
    protected static int getAppearenceID(String mapDirLocation)
    {
        // TODO implement me
        
        return 0;
    }

    private static ArrayList<WorldCell> translate(String first)
    {
        //TODO store translation values elsewhere?
        ArrayList<WorldCell> ret = new ArrayList<WorldCell>();
        for(char c:first.toCharArray()){
            switch(c){
                case 'P':
                    ret.add(new PathCell());
                    break;
                case 'T':
                    ret.add(new TowerCell());
                    break;
                default:
                    throw new IllegalArgumentException("Data file must be composed of legal cells");
            }
        }
        return ret;
    }
}
