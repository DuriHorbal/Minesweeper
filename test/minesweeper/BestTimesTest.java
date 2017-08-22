/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ďuri
 */
public class BestTimesTest {
    
    public BestTimesTest() {
    }

    /**
     * Test of getTime method, of class BestTimes.
     */
    @Test
    public void testInsertSelect() {
        //Minesweeper.getInstance();
        
        BestTimes instance=new BestTimes();
        instance.addPlayerTime("hrac",0);
        String ocakavanyVypis="PORADIE MENO ČAS\n   1.   hrac  0sec";
        String vypis=instance.toString().substring(0, ocakavanyVypis.length());
        assertEquals(vypis, ocakavanyVypis);
        deletePlayer("hrac",0);
    }
    
     /**
     * Test of getTime method, of class BestTimes.
     */
    @Test
    public void testBestTime() {
        BestTimes instance=new BestTimes();
        instance.addPlayerTime("hrac",0);
        int time=instance.getTime(1);
        assertEquals(time, 0);
        deletePlayer("hrac",0);
    }
    
    public void deletePlayer(String name, int time) {
        deleteFromDB(name, time);
    }
    /**
     * deleteFromDB method for delete concrete player.
     *
     * @param name of deleted player
     * @param time of deleted player
     */
    public static void deleteFromDB(String name, int time) {
        try {
            Class.forName(DatabaseSetting.DRIVER_CLASS);
            Connection connection = DriverManager.getConnection(DatabaseSetting.URL, DatabaseSetting.USER, DatabaseSetting.PASSWORD);
            PreparedStatement pstm = connection.prepareStatement(DatabaseSetting.DeletePlayer());
            pstm.setInt(1, time);
            pstm.setString(2, name);
            pstm.execute();
            pstm.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println("Exception occured during DeletePlayers score from database: " + ex.getMessage());
        }
    }
}