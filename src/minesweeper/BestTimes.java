package minesweeper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

/**
 * Player times.
 */
public class BestTimes implements Iterable<BestTimes.PlayerTime> {

    /**
     * List of best player times.
     */
    private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

    /**
     * Constructor.
     */
    public BestTimes() {
        /*addPlayerTime("plad", 35);
         addPlayerTime("plas", 45);
         addPlayerTime("plaa", 80);
         addPlayerTime("plha", 118);
         addPlayerTime("plhha", 70);
         addPlayerTime("plad", 65);
         addPlayerTime("plas", 95);
         addPlayerTime("plaa", 60);
         addPlayerTime("plha", 1018);
         addPlayerTime("plhha", 40);*/
    }

    /**
     * Returns an iterator over a set of best times.
     *
     * @return an iterator
     */
    public Iterator<PlayerTime> iterator() {
        return playerTimes.iterator();
    }

    /**
     * Adds player time to best times.
     *
     * @param name name of the player
     * @param time player time in seconds
     */
    public void addPlayerTime(String name, int time) {
        insertToDB(new PlayerTime(name, time));
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        Formatter f = new Formatter();
        selectFromDB();
        int i = 1;
        f.format("ORDER NAME TIME\n");
        for (PlayerTime pt : playerTimes) {
            f.format("  %2d.  %5s  %dsec\n", i, pt.name, pt.time);
            i++;
        }
        return f.toString();
    }

    /**
     * getTime method.
     *
     * @param position position of player
     * @return int player time in seconds
     */
    public int getTime(int position) {
        selectFromDB();
        try {
            return playerTimes.get(position - 1).time;
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("Position does not exist!");
            return 999;
        }
    }

    /**
     * InsertToDB method.
     *
     * @param playerTime
     */
    private void insertToDB(PlayerTime playerTime) {
        try {
            //System.out.println("tu som");
            Class.forName(DatabaseSetting.DRIVER_CLASS);
            Connection connection = DriverManager.getConnection(DatabaseSetting.URL,
                    DatabaseSetting.USER, DatabaseSetting.PASSWORD);

            Statement stm = connection.createStatement();
            try {
                stm.executeUpdate(DatabaseSetting.Creat());
            } catch (Exception e) {
                //do not propagate exception, table may already exist
            }
            stm.close();
            PreparedStatement pstm = connection.prepareStatement(DatabaseSetting.Add());
            pstm.setString(1, playerTime.getName());
            pstm.setInt(2, playerTime.getTime());
            pstm.execute();
            pstm.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println("Exception occured during saving high score to database: " + ex.getMessage());
        }
    }

    /**
     * SelectFromDB method.
     */
    private void selectFromDB() {
        playerTimes.clear();
        try {
            Class.forName(DatabaseSetting.DRIVER_CLASS);
            Connection connection = DriverManager.getConnection(DatabaseSetting.URL,
                    DatabaseSetting.USER, DatabaseSetting.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(DatabaseSetting.Select());
            while (rs.next()) {
                PlayerTime pt = new PlayerTime(rs.getString(1), rs.getInt(2));
                playerTimes.add(pt);
                Collections.sort(playerTimes);
            }
            stm.close();
        } catch (Exception ex) {
            System.out.println("Exception occured during loading high score from database: " + ex.getMessage());
        }
    }

    /**
     * Player time.
     */
    public static class PlayerTime implements Comparable<PlayerTime> {

        /**
         * Player name.
         */
        private final String name;
        /**
         * Playing time in seconds.
         */
        private final int time;

        /**
         * Constructor.
         *
         * @param name player name
         * @param time playing game time in seconds
         */
        public PlayerTime(String name, int time) {
            this.name = name;
            this.time = time;
        }

        /**
         * getName method.
         *
         * @return String representation of name
         */
        public String getName() {
            return name;
        }

        /**
         * getTime method.
         *
         * @return int representation of time
         */
        public int getTime() {
            return time;
        }

        /**
         * compareTo method. Returns 1(o lepsi čas),0,-1(o horsi cas).
         *
         * @return int representation of the compare
         */
        @Override
        public int compareTo(PlayerTime o) {
            int i = 1;
            if (o.getTime() == this.time) {
                i = 0;
            }
            if (o.getTime() > this.time) {
                i = -1;
            }
            return i;
        }
    }
}
