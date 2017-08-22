/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;
/*
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;*/
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ďuri
 */
public class SettingsTest {

    public SettingsTest() {
    }

    /**
     * Test of load, save method, of class Settings.
     */
    @Test
    public void testLoadSave() {
        Settings expResult = Settings.BEGINNER;
        expResult.save();
        Settings result = Settings.load();
        assertEquals(expResult, result);

        expResult = Settings.EXPERT;
        expResult.save();
        result = Settings.load();
        assertEquals(expResult, result);

        expResult = Settings.INTERMEDIATE;
        expResult.save();
        result = Settings.load();
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class Settings.
     */
    @Test
    public void testHash() {
        Settings expResult = Settings.EXPERT;
        int kod = 16 * 30 * 99;
        assertEquals(expResult.hashCode(), kod);
        
        expResult = Settings.INTERMEDIATE;
        kod = 16 * 16 * 40;
        assertEquals(expResult.hashCode(), kod);
        
        expResult = Settings.BEGINNER;
        kod = 9 * 9 * 10;
        assertEquals(expResult.hashCode(), kod);
    }
}