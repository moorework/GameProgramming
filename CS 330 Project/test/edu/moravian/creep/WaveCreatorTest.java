/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.moravian.creep;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author myles
 */
public class WaveCreatorTest {
    private final String FILE = "testWaves/testWaves.properties";
    
    @Test
    public void testWaveCreation() {
        WaveCreator waveCreator = new WaveCreator(FILE);
        
        assertTrue(waveCreator.hasMoreWaves());
        
        ArrayList<Wave> expectedWaves = new ArrayList<Wave>();
        Wave expectedWave = new Wave(6, 5, 15, 101);
        expectedWaves.add(expectedWave);
        expectedWave = new Wave(8, 8, 19, 102);
        expectedWaves.add(expectedWave);
        
        Wave createdWave;
        for (Wave correctWave : expectedWaves) {
            createdWave = waveCreator.getNextWave();
            
            assertEquals(correctWave, createdWave);
        }
    }
}
