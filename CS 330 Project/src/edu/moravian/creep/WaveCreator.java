

package edu.moravian.creep;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;

/**
 *
 * @author myles
 */
public class WaveCreator {
    // property key data for macro wave file
    private final String NUM_WAVES = "numWaves";
    private final String NUM_WAVES_DEFAULT = "1";
    private final String INIT_STRENGTH = "initStrength";
    private final String INIT_STRENGTH_DEFAULT = "10";
    private final String INIT_SPEED = "initSpeed";
    private final String INIT_SPEED_DEFAULT = "15";
    private final String STRENGTH_MULT = "strengthMult";
    private final String STRENGTH_MULT_DEFAULT = "3";
    private final String SPEED_MULT = "speedMult";
    private final String SPEED_MULT_DEFAULT = "4";
    private final String NUM_CREEPS = "numCreeps";
    private final String NUM_CREEPS_DEFAULT = "8";
    private final String IMAGE_ID = "creepImageID";
    private final String IMAGE_ID_DEFAULT = "101";
    
    private Properties waveData; // data that describes all waves
    
    // each successive wave should be more difficult than the one that preceeded
    // it; thus each wave's attributes are modified by the multipliers
    private int numWaves;
    private int initHealth;
    private int initSpeed;
    private int strengthMultiplier;
    private double speedMultiplier;
    private ArrayList<Integer> numCreeps;
    private ArrayList<Integer> imageIDs;
    
    LinkedList<Wave> waves;
    
    public WaveCreator(String waveDataDirectory) {
        waveData = new Properties();
        
        try {
            InputStream is = new FileInputStream(waveDataDirectory);
            waveData.load(is);
        }
        catch(Exception ex) {
            System.out.println("WaveCreator construction failed: " + ex);
        }
        
        numCreeps = new ArrayList<Integer>();
        imageIDs = new ArrayList<Integer>();
        readInData();
        
        waves = new LinkedList<Wave>();
        createWaves();
    }
    
    private void readInData() {
        String numWaveVal = waveData.getProperty(NUM_WAVES, NUM_WAVES_DEFAULT);
        numWaves = Integer.parseInt(numWaveVal);
        
        String initHealthVal = waveData.getProperty(INIT_STRENGTH, INIT_STRENGTH_DEFAULT);
        initHealth = Integer.parseInt(initHealthVal);
        
        String initSpeedVal = waveData.getProperty(INIT_SPEED, INIT_SPEED_DEFAULT);
        initSpeed = Integer.parseInt(initSpeedVal);
        
        String strengthMultiplierVal = waveData.getProperty(STRENGTH_MULT, STRENGTH_MULT_DEFAULT);
        strengthMultiplier = Integer.parseInt(strengthMultiplierVal);
        
        String speedMultiplierVal = waveData.getProperty(SPEED_MULT, SPEED_MULT_DEFAULT);
        speedMultiplier = Double.parseDouble(speedMultiplierVal);
        
        int tempNumCreeps;
        for (int i = 1; i <= numWaves; i++) {
            String numCreepsPerRowVal = waveData.getProperty(NUM_CREEPS + i, NUM_CREEPS_DEFAULT);
            tempNumCreeps = Integer.parseInt(numCreepsPerRowVal);
            
            numCreeps.add(tempNumCreeps);
        }
        
        int imageID;
        String imageIDVal;
        for (int i = 1; i <= numWaves; i++) {
            imageIDVal = waveData.getProperty(IMAGE_ID + i, IMAGE_ID_DEFAULT);
            
            imageID = Integer.parseInt(imageIDVal);
            
            imageIDs.add(imageID);
        }
    }
    
    private void createWaves() {
        Wave freshWave;
        int creepHealth;
        double creepSpeed;
        for (int i = 0; i < numWaves; i++) {
            creepHealth = initHealth + (strengthMultiplier * i);
            creepSpeed = initSpeed + (speedMultiplier * i);
            
            freshWave = new Wave(numCreeps.get(i), creepHealth, creepSpeed, imageIDs.get(i));
            waves.add(freshWave);
        }
    }
    
    public boolean hasMoreWaves() {
        return (waves.isEmpty() == false);
    }
    
    public Wave getNextWave() {
        return waves.poll();
    }
}
