package minesweeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;
import minesweeper.swingui.SwingUI;

/**
 * Main application class.
 */
public class Minesweeper {

    /**
     * User interface.
     */
    private UserInterface userInterface;
    private static long startMillis;
    private BestTimes bestTimes;
    private static Minesweeper instance;
    private Settings settings;
    private static String DEFAULT_UI;// ="swing";//"console";// 

    /**
     * Constructor.
     */
    private Minesweeper() {
        this.instance = this;
        this.settings = Settings.load();
        DEFAULT_UI = defaultUI();
        try {
            this.userInterface = create(DEFAULT_UI);
        } catch (RuntimeException e) {
            System.err.println("Bad UI format (s or c)");
            return;
        }
        this.bestTimes = new BestTimes();
        //Field field = new Field(9, 9, 2);
        Field field = new Field(settings.getRowCount(), settings.getColumnCount(), settings.getMineCount());
        this.userInterface.newGameStarted(field);
    }

    private String defaultUI() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Start swing(s) or console(c) and confirm by enter");
        try {
            return input.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Minesweeper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * startTimer method. odstartuje cas
     */
    public void startTimer() {
        this.startMillis = System.currentTimeMillis();
    }

    /**
     * stopTimer method. vynuluje cas
     */
    public void stopTimer() {
        this.startMillis = 0;
    }

    /**
     * GetInstance method.
     *
     * @return instance of Minesweeper
     */
    public static Minesweeper getInstance() {
        if (instance == null) {
            return new Minesweeper();
        }
        return instance;
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        getInstance();
    }

    /**
     * getPlayingSeconds method.
     *
     * @return int representation of time
     */
    public static int getPlayingSeconds() {
        if (startMillis == 0) {
            return 0;
        }
        long actualTime = System.currentTimeMillis();
        return (int) ((actualTime - startMillis) / 1000);
    }

    /**
     * getBestTimes method.
     *
     * @return the getTime
     */
    public BestTimes getBestTimes() {
        return bestTimes;
    }

    /**
     * getSetting method.
     *
     * @return the setting
     */
    public Settings getSetting() {
        return settings;
    }

    /**
     * setSetting method.
     *
     * @param setting the setting to set
     */
    public void setSetting(Settings setting) {
        this.settings = setting;
        setting.save();
    }

    /**
     * NewGame method.
     *
     * Vytvorenie hry
     */
    public void newGame() {
        Field field = new Field(settings.getRowCount(), settings.getColumnCount(), settings.getMineCount());
        userInterface.newGameStarted(field);
    }

    /**
     * Create method.
     *
     * @param name the name of UI
     * @return UserInterface
     */
    private UserInterface create(String name) {
        if (name.equals("swing") || name.equals("s")) {
            return new SwingUI();
        }
        if (name.equals("console") || name.equals("c")) {
            return new ConsoleUI();
        }
        throw new RuntimeException("No valid UI specified");
    }
}
