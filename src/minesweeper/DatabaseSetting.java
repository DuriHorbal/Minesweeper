package minesweeper;


/**Settings of databases.
 *
 * @author Ďuri
 */
public class DatabaseSetting {

    /**
     * driver class
     */
    public static final String DRIVER_CLASS = "org.apache.derby.jdbc.ClientDriver";
    /**
     * URL adress of database
     */
    public static final String URL = "jdbc:derby://localhost/mines";
    /**
     * name of user
     */
    public static final String USER = "mines";
    /**
     * password of user
     */
    public static final String PASSWORD = "mines";
    private static String OWN_QUERY_CREATE_BEST_TIMES = "CREATE TABLE own_player_time (name VARCHAR(128) NOT NULL, best_time INT NOT NULL)";
    private static String OWN_QUERY_ADD_BEST_TIME = "INSERT INTO own_player_time (name, best_time) VALUES (?, ?)";
    private static String OWN_QUERY_SELECT_BEST_TIMES = "SELECT name, best_time FROM own_player_time";
    private static String OWN_QUERY_DELETE_BEST_TIMES = "DELETE FROM own_player_time WHERE best_time>?";
    private static String OWN_QUERY_DELETE_PLAYER = "DELETE FROM own_player_time WHERE best_time=? AND name=?";
    //public static final String BEGIN_QUERY_CREATE_SEQUENCE = "CREATE SEQUENCE S_begin start with 1 increment by 1";
    private static String BEGIN_QUERY_CREATE_BEST_TIMES = "CREATE TABLE begin_player_time (name VARCHAR(128) NOT NULL, best_time INT NOT NULL)";
    private static String BEGIN_QUERY_ADD_BEST_TIME = "INSERT INTO begin_player_time (name, best_time) VALUES (?, ?)";
    private static String BEGIN_QUERY_SELECT_BEST_TIMES = "SELECT name, best_time FROM begin_player_time";
    private static String BEGIN_QUERY_DELETE_BEST_TIMES = "DELETE FROM begin_player_time WHERE best_time>?";
    private static String BEGIN_QUERY_DELETE_PLAYER = "DELETE FROM begin_player_time WHERE best_time=? AND name=?";
    //public static final String OWN_QUERY_CREATE_SEQUENCE = "CREATE SEQUENCE S_own start with 1 increment by 1";
    private static String INTER_QUERY_CREATE_BEST_TIMES = "CREATE TABLE inter_player_time (name VARCHAR(128) NOT NULL, best_time INT NOT NULL)";
    private static String INTER_QUERY_ADD_BEST_TIME = "INSERT INTO inter_player_time (name, best_time) VALUES (?, ?)";
    private static String INTER_QUERY_SELECT_BEST_TIMES = "SELECT name, best_time FROM inter_player_time";
    private static String INTER_QUERY_DELETE_BEST_TIMES = "DELETE FROM inter_player_time WHERE best_time>?";
    private static String INTER_QUERY_DELETE_PLAYER = "DELETE FROM inter_player_time WHERE best_time=? AND name=?";
    //public static final String OWN_QUERY_CREATE_SEQUENCE = "CREATE SEQUENCE S_own start with 1 increment by 1";
    private static String EXPERT_QUERY_CREATE_BEST_TIMES = "CREATE TABLE expert_player_time (name VARCHAR(128) NOT NULL, best_time INT NOT NULL)";
    private static String EXPERT_QUERY_ADD_BEST_TIME = "INSERT INTO expert_player_time (name, best_time) VALUES (?, ?)";
    private static String EXPERT_QUERY_SELECT_BEST_TIMES = "SELECT name, best_time FROM expert_player_time";
    private static String EXPERT_QUERY_DELETE_BEST_TIMES = "DELETE FROM expert_player_time WHERE best_time>?";
    private static String EXPERT_QUERY_DELETE_PLAYER = "DELETE FROM expert_player_time WHERE best_time=? AND name=?";
    //private static String EXPERT_QUERY_BEST_TIMES = "SELECT best_time FROM expert_player_time WHERE best_time";
    private static Settings setting;

    private DatabaseSetting() {
    }

    /**
     * Creat method.
     *
     * @return String command for create table for specific level
     */
    public static String Creat() {
        setting = Settings.load();
        switch (setting.getColumnCount()) {
            case 9:
                return BEGIN_QUERY_CREATE_BEST_TIMES;
            case 16:
                return INTER_QUERY_CREATE_BEST_TIMES;
            case 30:
                return EXPERT_QUERY_CREATE_BEST_TIMES;
            default:
                return OWN_QUERY_CREATE_BEST_TIMES;
        }
    }

    /**
     * Add method.
     *
     * @return String command for add best time into table for specific level
     */
    public static String Add() {
        setting = Settings.load();
        switch (setting.getColumnCount()) {
            case 9:
                return BEGIN_QUERY_ADD_BEST_TIME;
            case 16:
                return INTER_QUERY_ADD_BEST_TIME;
            case 30:
                return EXPERT_QUERY_ADD_BEST_TIME;
            default:
                return OWN_QUERY_ADD_BEST_TIME;
        }
    }

    /**
     * Select method.
     *
     * @return String command for select from table for specific level
     */
    public static String Select() {
        setting = Settings.load();
        switch (setting.getColumnCount()) {
            case 9:
                return BEGIN_QUERY_SELECT_BEST_TIMES;
            case 16:
                return INTER_QUERY_SELECT_BEST_TIMES;
            case 30:
                return EXPERT_QUERY_SELECT_BEST_TIMES;
            default:
                return OWN_QUERY_SELECT_BEST_TIMES;
        }
    }

    /**
     * Delete method.
     *
     * @return String command for delete from table for specific level
     */
    public static String Delete() {
        setting = Settings.load();
        switch (setting.getColumnCount()) {
            case 9:
                return BEGIN_QUERY_DELETE_BEST_TIMES;
            case 16:
                return INTER_QUERY_DELETE_BEST_TIMES;
            case 30:
                return EXPERT_QUERY_DELETE_BEST_TIMES;
            default:
                return OWN_QUERY_DELETE_BEST_TIMES;
        }
    }

    /**
     * Delete method.
     *
     * @return String command for delete from table for specific level
     */
    public static String DeletePlayer() {
        setting = Settings.load();
        switch (setting.getColumnCount()) {
            case 9:
                return BEGIN_QUERY_DELETE_PLAYER;
            case 16:
                return INTER_QUERY_DELETE_PLAYER;
            case 30:
                return EXPERT_QUERY_DELETE_PLAYER;
            default:
                return OWN_QUERY_DELETE_PLAYER;
        }
    }
}
