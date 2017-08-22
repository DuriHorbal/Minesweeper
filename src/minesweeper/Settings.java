package minesweeper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/** Settings of game.
 *
 * @author Ďuri
 */
public class Settings implements Serializable {

    /**
     * Beginner setting
     */
    public static final Settings BEGINNER = new Settings(9, 9, 10);
    /**
     * Intermediate setting
     */
    public static final Settings INTERMEDIATE = new Settings(16, 16, 40);
    /**
     * Expert setting
     */
    public static final Settings EXPERT = new Settings(16, 30, 99);
    private static final String SETTING_FILE = System.getProperty("user.home") + System.getProperty("file.separator") + "minesweeper.settings";
    private final int rowCount;
    private final int columnCount;
    private final int mineCount;

    /**
     *
     * @param rowCount
     * @param columnCount
     * @param mineCount
     */
    public Settings(int rowCount, int columnCount, int mineCount) {
        this.columnCount = columnCount;
        this.mineCount = mineCount;
        this.rowCount = rowCount;
    }

    /**
     * @return the rowCount
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * @return the columnCount
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * @return the mineCount
     */
    public int getMineCount() {
        return mineCount;
    }

    /**
     * @return int hasCode=columnCount*mineCount*rowCount
     */
    @Override
    public int hashCode() {
        return this.columnCount * this.mineCount * this.rowCount;
    }

    /**
     * @return the equals
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Settings other = (Settings) obj;
        if (this.rowCount != other.rowCount) {
            return false;
        }
        if (this.columnCount != other.columnCount) {
            return false;
        }
        if (this.mineCount != other.mineCount) {
            return false;
        }
        return true;
    }

    /**
     * save method. save Settings
     */
    public void save() {
        try {
            FileOutputStream saveFile = new FileOutputStream(SETTING_FILE);
            ObjectOutputStream object = new ObjectOutputStream(saveFile);
            object.writeObject(this);
            saveFile.close();
        } catch (Exception exc) {
            // exc.printStackTrace(); 
        }
    }

    /**
     * load method. load Settings
     *
     * @return game Setting
     */
    public static Settings load() {
        Settings hra = BEGINNER;
        try {
            FileInputStream loadFile = new FileInputStream(SETTING_FILE);
            ObjectInputStream load = new ObjectInputStream(loadFile);
            hra = (Settings) load.readObject();
        } catch (Exception exc) {
            // exc.printStackTrace();
        }
        return hra;
    }
}
