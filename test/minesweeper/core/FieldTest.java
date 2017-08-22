/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.core;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ďuri
 */
public class FieldTest {
    static final int ROWS = 9;
    static final int COLUMNS = 9;
    static final int MINES = 10;
    
    public FieldTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    @Test                
    public void isSolved() {
        Field field = new Field(ROWS, COLUMNS, MINES);
        
        assertEquals(GameState.PLAYING, field.getState());
        
        int open = 0;
        for(int row = 0; row < field.getRowCount(); row++) {
            for(int column = 0; column < field.getColumnCount(); column++) {
                if(field.getTile(row, column) instanceof Clue) {
                    field.openTile(row, column);
                    open++;
                }
                if(field.getRowCount() * field.getColumnCount() - open == field.getMineCount()) {
                    assertEquals(GameState.SOLVED, field.getState());
                } else {
                    assertNotSame(GameState.FAILED, field.getState());
                }
            }
        }
        
        assertEquals(GameState.SOLVED, field.getState());
    } 
    
    private void generate() {
        pocRiadkov();
        notNull();
        pocMin();
        pocClue();
    }
    
    @Test                
    public void pocRiadkov() {
        Field field = new Field(ROWS, COLUMNS, MINES);
        assertEquals(ROWS, field.getRowCount());
        assertEquals(COLUMNS, field.getColumnCount());
        assertEquals(MINES, field.getMineCount());
    }
    
    @Test                
    public void notNull() {
    int row,column;
    Field field = new Field(ROWS, COLUMNS, MINES);
    for (row=0; row<ROWS; row++){
        for (column=0; column<COLUMNS; column++){
            assertNotNull(field.getTile(row, column));
        }
    }
    }
    
    @Test                
    public void pocMin() {
    int row,column,mineCount=0;
    Field field = new Field(ROWS, COLUMNS, MINES);
    for (row=0; row<ROWS; row++){
        for (column=0; column<COLUMNS; column++){
            if (field.getTile(row, column) instanceof Mine)
                mineCount++;
        }
    }
        assertEquals(MINES, mineCount);
    }
    
    
    @Test                
    public void pocClue() {
    int row,column,clueCount=0;
    Field field = new Field(ROWS, COLUMNS, MINES);
    for (row=0; row<ROWS; row++){
        for (column=0; column<COLUMNS; column++){
            if (field.getTile(row, column) instanceof Clue)
                clueCount++;
        }
    }
        assertEquals(ROWS * COLUMNS - MINES, clueCount);
    }
}