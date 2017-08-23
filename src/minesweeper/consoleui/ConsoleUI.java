package minesweeper.consoleui;

import minesweeper.UserInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import minesweeper.Minesweeper;
import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;
import minesweeper.core.Tile;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {

    /**
     * Playing field.
     */
    private Field field;
    /**
     * Input reader.
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads line of text from the reader.
     *
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Starts the game.
     *
     * @param field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();

            if (field.getState().equals(GameState.SOLVED)) {
                update();
                System.out.print("<Vyhral si>\n");
                int timeGame = Minesweeper.getPlayingSeconds();
                Minesweeper.getInstance().getBestTimes().addPlayerTime(System.getProperty("user.name"), timeGame);
                System.out.print(Minesweeper.getInstance().getBestTimes().toString());
                System.exit(0);
            }
            if (field.getState().equals(GameState.FAILED)) {
                update();
                System.out.print("<Prehral si>\n");
                System.exit(0);
            }
        } while (true);
    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        int i, j, k;
        String characters = "ABCDEFGHIJKLMNOPRSTUVXYZ";
        System.out.print("  ");
        for (k = 0; k < field.getColumnCount(); k++) {
            System.out.printf("%2d ", k);
        }
        System.out.print(" Count of mines: " + field.getRemainingMineCount() + " Time: " + Minesweeper.getPlayingSeconds() + " sec\n");
        for (i = 0; i < field.getRowCount(); i++) {
            System.out.print(characters.charAt(i) + " ");
            for (j = 0; j < field.getColumnCount(); j++) {
                if (field.getTile(i, j).getState() == Tile.State.OPEN && field.getTile(i, j) instanceof Mine) {
                    System.out.print(" X ");
                }
                if (field.getTile(i, j).getState() == Tile.State.OPEN && field.getTile(i, j) instanceof Clue) {
                    Clue pomocka = (Clue) field.getTile(i, j);
                    System.out.print(" " + pomocka.getValue() + " ");
                }
                if (field.getTile(i, j).getState() == Tile.State.MARKED) {
                    System.out.print(" M ");
                }
                if (field.getTile(i, j).getState() == Tile.State.CLOSED) {
                    System.out.print(" - ");
                }
                if (field.getTile(i, j).getState() == Tile.State.QUESTION) {
                    System.out.print(" ? ");
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * Processes user input. Reads line from console and does the action on a
     * playing field according to input string.
     */
    private void processInput() {
        String vstup;
        System.out.print("X for Exit | M for Mark | O for Open | Example OA1 ");
        vstup = readLine();
        try {
            handleInput(vstup);
        } catch (WrongFormatException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Processes user input. controler input string
     */
    private void handleInput(String vstup) throws WrongFormatException {
        String pravyVstup = vstup;
        vstup = vstup.toUpperCase();
        Pattern vzor = Pattern.compile("([OM])([A-I])([0-8])");
        Matcher matcher = vzor.matcher(vstup);
        if ("X".matches(vstup)) {
            System.out.print("You gave it up\n");
            System.exit(0);
        } else if (matcher.matches()) {
            if ("O".equals(matcher.group(1))) {
                int column = Integer.parseInt(matcher.group(3));
                int row = Integer.parseInt(matcher.group(2), 32) - 10;
                field.openTile(row, column);
            }
            if ("M".equals(matcher.group(1))) {
                int column = Integer.parseInt(matcher.group(3));
                int row = Integer.parseInt(matcher.group(2), 32) - 10;
                field.markTile(row, column);
            }
        } else {
            throw new WrongFormatException("Wrong input: " + pravyVstup);
        }
    }
}
