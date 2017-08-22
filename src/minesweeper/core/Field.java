package minesweeper.core;

import java.util.Random;
import minesweeper.Minesweeper;

/**
 * Field represents playing field and game logic.
 */
public class Field {

    /**
     * Playing field tiles.
     */
    private final Tile[][] tiles;
    /**
     * Field row count. Rows are indexed from 0 to (rowCount - 1).
     */
    private final int rowCount;
    /**
     * Column count. Columns are indexed from 0 to (columnCount - 1).
     */
    private final int columnCount;
    /**
     * Mine count.
     */
    private final int mineCount;
    /**
     * Start game.
     */
    private boolean start;
    /**
     * Game state.
     */
    private GameState state = GameState.PLAYING;

    /**
     * Constructor.
     *
     * @param rowCount row count
     * @param columnCount column count
     * @param mineCount mine count
     */
    public Field(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
        this.start = true;
        tiles = new Tile[rowCount][columnCount];
        generate();
    }

    /**
     * Opens tile at specified indeces.
     *
     * @param row row number
     * @param column column number
     */
    public void openTile(int row, int column) {
        final Tile tile = tiles[row][column];
        if (this.start) {
            this.start = false;
            Minesweeper.getInstance().startTimer();
        }
        if (tile.getState() == Tile.State.CLOSED || tile.getState() == Tile.State.QUESTION) {
            tile.setState(Tile.State.OPEN);
            if (tile instanceof Mine) {
                openMines();
                state = GameState.FAILED;
                Minesweeper.getInstance().stopTimer();
                return;
            }
            if (tile instanceof Clue) {
                Clue actual = (Clue) tile;
                if (actual.getValue() == 0) {
                    openAdjacentTiles(row, column);
                }
            }
            if (isSolved()) {
                state = GameState.SOLVED;
                Minesweeper.getInstance().stopTimer();
            }
        }
    }

    /**
     * Open tiles around Clue.
     *
     * @param row row number
     * @param column column number
     */
    public void rightLeftClick(int row, int column) {
        final Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.OPEN) {
            Clue clue = (Clue) tile;
            if (markedTiles(row, column, clue.getValue())) {
                openNoMarkedTiles(row, column);
            }
        }
    }

    /**
     * Marks tile at specified indeces.
     *
     * @param row row number
     * @param column column number
     */
    public void markTile(int row, int column) {
        final Tile tile = tiles[row][column];
        switch (tile.getState()) {
            case CLOSED:
                tile.setState(Tile.State.MARKED);
                break;
            case MARKED:
                tile.setState(Tile.State.QUESTION);
                break;
            case QUESTION:
                tile.setState(Tile.State.CLOSED);
                break;
        }
    }

    /**
     * Generates playing field.
     */
    private void generate() {
        generateMines();
        fillWithClues();
    }

    /**
     * Generate mines
     */
    private void generateMines() {
        int row, column;
        Random cislo = new Random();
        int trebaPolozit = this.mineCount;
        while (trebaPolozit > 0) {
            row = cislo.nextInt(this.rowCount);
            column = cislo.nextInt(this.columnCount);
            if (tiles[row][column] == null) {
                tiles[row][column] = new Mine();
                trebaPolozit--;
            }
        }
    }

    /**
     * Open all mines
     */
    private void openMines() {
        int i, j;
        for (i = 0; i < this.rowCount; i++) {
            for (j = 0; j < this.columnCount; j++) {
                if (tiles[i][j] instanceof Mine) {
                    tiles[i][j].setState(Tile.State.OPEN);
                }
            }
        }
    }

    /**
     * Make number on all Clues
     */
    private void fillWithClues() {
        int i, j;
        for (i = 0; i < this.rowCount; i++) {
            for (j = 0; j < this.columnCount; j++) {
                if (tiles[i][j] == null) {
                    tiles[i][j] = new Clue(countAdjacentMines(i, j));
                }
            }
        }
    }

    /**
     * Returns true if game is solved, false otherwise.
     *
     * @return true if game is solved, false otherwise
     */
    private boolean isSolved() {
        int pocetDlazdic = (columnCount) * (rowCount);
        int pocetOdrkytych = getNumberOf(Tile.State.OPEN);
        return (mineCount == (pocetDlazdic - pocetOdrkytych));
    }

    /**
     * Returns number of adjacent mines for a tile at specified position in the
     * field.
     *
     * @param row row number.
     * @param column column number.
     * @return number of adjacent mines.
     */
    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < getRowCount()) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < getColumnCount() && tiles[actRow][actColumn] instanceof Mine) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Make number on all Clues
     *
     * @param row row number.
     * @param column column number.
     * @param pocet value of clue
     * @return boolean true if value of clue = number of marked Tiles
     */
    private boolean markedTiles(int row, int column, int pocet) {
        int i, j, markedMines = 0;
        for (i = row - 1; i <= row + 1; i++) {
            for (j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && j >= 0 && i < this.rowCount && j < this.columnCount && tiles[i][j].getState().equals(Tile.State.MARKED)) {
                    markedMines++;
                }
            }
        }
        if (pocet > 0) {
            return (pocet == markedMines);
        }
        return false;
    }

    /**
     * @return the rowCount
     */
    public int getRowCount() {
        return this.rowCount;
    }

    /**
     * @return the columnCount
     */
    public int getColumnCount() {
        return this.columnCount;
    }

    /**
     * @return the mineCount
     */
    public int getMineCount() {
        return this.mineCount;
    }

    /**
     * @return the state
     */
    public GameState getState() {
        return this.state;
    }

    /**
     * get tile from field.
     *
     * @param row of tile
     * @param column of tile
     * @return the tiles[][]
     */
    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }

    /**
     * @param state State of Tile
     * @return number of tiles in State state
     */
    private int getNumberOf(Tile.State state) {
        int i, j, number = 0;
        for (i = 0; i < this.rowCount; i++) {
            for (j = 0; j < this.columnCount; j++) {
                if (tiles[i][j].getState() == state) {
                    number++;
                }
            }
        }
        return number;
    }

    /**
     * Open ALL 8 Tiles around
     *
     * @param row row number.
     * @param column column number.
     */
    private void openAdjacentTiles(int row, int column) {
        int i, j;
        for (i = row - 1; i <= row + 1; i++) {
            for (j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && j >= 0 && i < this.rowCount && j < this.columnCount) {
                    openTile(i, j);
                }
            }
        }
    }

    /**
     * Open Tiles around, that are not Marked
     *
     * @param row row number.
     * @param column column number.
     */
    private void openNoMarkedTiles(int row, int column) {
        int i, j;
        for (i = row - 1; i <= row + 1; i++) {
            for (j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && j >= 0 && i < this.rowCount && j < this.columnCount && (tiles[i][j].getState().equals(Tile.State.CLOSED) || tiles[i][j].getState().equals(Tile.State.QUESTION))) {
                    openTile(i, j);
                }
            }
        }
    }

    /**
     * @return number of tiles, that player have to find
     */
    public int getRemainingMineCount() {
        return this.mineCount - getNumberOf(Tile.State.MARKED);
    }
}
