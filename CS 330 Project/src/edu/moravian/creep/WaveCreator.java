package edu.moravian.creep;

import edu.moravian.util.Timer;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class WaveCreator
{

    LinkedList<Wave> waves;
    private static final int NUM_ATTRIBUTES = 7;
//TODO make sure this breaks correctly 
    
    /**
     * Creates a series of wave objects that hold waves of creeps.  
     * @param fileLoc
     * @throws FileNotFoundException 
     */
    public WaveCreator(String fileLoc) throws FileNotFoundException
    {
        /*
         * Ok this is the documentation of the creep file system
         */
        //TODO create ini files with documentaiton inside of them 

        waves = new LinkedList<Wave>();
        //read me some creep data

        File f = new File(fileLoc);
        Scanner s = new Scanner(f);
        String creepParse = "";
        //TODO make this not tripe
        while (s.hasNextLine())
        {
            creepParse += s.nextLine();
        }
        //Split them into indiviual waves
        String[] pieces = creepParse.split("$");

        for (int i = 0; i < pieces.length; i++)
        {
            waves.add(parseWave(pieces[i]));
        }

    }

    /**
     * Makes sure there is another wave availible.
     * @return 
     */
    public boolean hasNextWave()
    { 
        return waves.size() > 1;
    }

    /**
     * Pops a wave off the wave list.  Beware of not having a next wave
     * @return 
     */
    public Wave getNextWave()
    {
        return waves.getFirst();
    }

    /**
     * A string that represents one wave.  A series of N atributes deliniated by
     * commas.  
     * @param oneWave
     * @return 
     */
    private Wave parseWave(String oneWave)
    {
        //Got that polymorphism, hater
        String[] creeps = oneWave.split(System.getProperty("line.separator"));
        Wave ret = new Wave();

        for (String individualCreep : creeps)
        {
            String[] parts = individualCreep.split(",");
            int numCreeps = Integer.parseInt(parts[0]);
            int creepType = Integer.parseInt(parts[1]);
            double strengthMult = Double.parseDouble(parts[2]);
            double speedMult = Double.parseDouble(parts[3]);
            int numPerRow = Integer.parseInt(parts[4]);
            double release_frequency = Double.parseDouble(parts[5]);

            ret.addWave(numCreeps, creepType, strengthMult, speedMult, numPerRow, release_frequency);

        }

        return ret;

    }

    //TODO make this more extendable
    //TODO do we want waves to control when they are released?
    private class Wave
    {

    private    LinkedList<Creep> release_order;
     private   LinkedList<Integer> release_quantity;
     private   Timer time;

        public Wave()
        {

            release_order = new LinkedList<Creep>();
            release_quantity = new LinkedList<Integer>();
            time = new Timer();
        }

        private void addWave(int numCreeps, int creepType, double strengthMult, double speedMult, int numPerRow, double release_frequency)
        {
            //TODO we need some way to index into creep type
        }
    }
}
