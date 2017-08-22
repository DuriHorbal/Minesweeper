package minesweeper.swingui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Formatter;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import minesweeper.core.Field;
import minesweeper.Minesweeper;
import minesweeper.core.GameState;
import minesweeper.Settings;
import minesweeper.UserInterface;

/**
 * Swing interface.
 * @author Ďuri
 */
public class SwingUI extends javax.swing.JFrame implements UserInterface, MouseListener {

    /**
     * Playing field.
     */
    private Field field;
    /**
     * Playing timer.
     */
    private Timer timer;
    /**
     * Time of playing from minesweeper.
     */
    private int time;
    /**
     * Time of the best player.
     */
    private int bestTime;

    /**
     * Constructor.
     */
    public SwingUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        initComponents();

        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/img/logo.gif")).getImage());
        setVisible(true);

        if (Minesweeper.getInstance().getSetting().equals(Settings.BEGINNER)) {
            beginnerMenuItem.setSelected(true);
        } else if (Minesweeper.getInstance().getSetting().equals(Settings.INTERMEDIATE)) {
            intermediateMenuItem.setSelected(true);
        } else if (Minesweeper.getInstance().getSetting().equals(Settings.EXPERT)) {
            expertMenuItem.setSelected(true);
        } else {
            ownMenuItem.setSelected(true);
        }
        this.timer = new Timer(100, listener);
    }
    ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (field.getState().equals(GameState.PLAYING)) {
                setTimeLabelText();
            }
        }
    };

    /**
     * Starts the game.
     *
     * @param field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        contentPanel.removeAll();
        contentPanel.setLayout(new GridLayout(this.field.getRowCount(), this.field.getColumnCount()));
        for (int i = 0; i < this.field.getRowCount(); i++) {
            for (int j = 0; j < this.field.getColumnCount(); j++) {
                TileComponent comp = new TileComponent(this.field.getTile(i, j), i, j);
                contentPanel.add(comp);
                comp.addMouseListener(this);
            }
        }
        if (Odratavanie.getState()) {
            this.bestTime = Minesweeper.getInstance().getBestTimes().getTime(1);
        } else {
            this.bestTime = 0;
        }
        this.timer.stop();
        Minesweeper.getInstance().stopTimer();
        this.setMinesLeftLabelText();
        this.setTimeLabelText();
        pack();
    }

    /**
     * Produces a reaction on mouse events
     *
     * @param e is mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (field.getState().equals(GameState.PLAYING)) {
            final int MASK = InputEvent.BUTTON1_DOWN_MASK | InputEvent.BUTTON3_DOWN_MASK;
            if (time == 0) {
                this.timer.start();
            }
            TileComponent button = (TileComponent) e.getSource();
            if (SwingUtilities.isLeftMouseButton(e)) {
                field.openTile(button.getRow(), button.getColumn());
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                field.markTile(button.getRow(), button.getColumn());
                this.setMinesLeftLabelText();
            }
            if ((e.getModifiersEx() & MASK) == MASK) {
                field.rightLeftClick(button.getRow(), button.getColumn());
            }
            update();
            if (field.getState().equals(GameState.FAILED)) {
                JOptionPane.showMessageDialog(rootPane, "Prehral si", "PREHRA", WIDTH);
                this.timer.stop();
                bestTime = 0;
            }
            if (field.getState().equals(GameState.SOLVED)) {
                this.timer.stop();
                this.bestTime = Minesweeper.getInstance().getBestTimes().getTime(1);
                if (this.bestTime > this.time) {
                    JOptionPane.showMessageDialog(rootPane, "     NewRecord!\n     Name: " + System.getProperty("user.name") + "\n      Time: " + this.time + "sec");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "     Vyhral si \n     Name: " + System.getProperty("user.name") + "\n      Time: " + this.time + "sec");
                }
                Minesweeper.getInstance().getBestTimes().addPlayerTime(System.getProperty("user.name"), this.time);
                bestTime = 0;
            }
        }
    }

    /**
     * Set number of mines in field
     */
    private void setMinesLeftLabelText() {
        StringBuilder sb = new StringBuilder();
        new Formatter(sb).format("%03d", field.getRemainingMineCount());
        minesLeftLabel.setText(sb.toString());
    }

    /**
     * Set time of plaing
     */
    private void setTimeLabelText() {
        StringBuilder sb = new StringBuilder();
        this.time = Minesweeper.getInstance().getPlayingSeconds();
        if ((this.bestTime - this.time) >= 0) {
            new Formatter(sb).format("%03d", bestTime - this.time);
        } else {
            new Formatter(sb).format("%03d", this.time);
        }
        timeLabel.setText(sb.toString());
    }

    /**
     * Updates user interface - components of the field.
     */
    @Override
    public void update() {
        int num = contentPanel.getComponentCount();
        for (int i = 0; i < num; i++) {
            TileComponent component = (TileComponent) contentPanel.getComponent(i);
            component.updateStyle();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        infoPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        minesLeftLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        timeLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        newButton = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        gameMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        beginnerMenuItem = new javax.swing.JRadioButtonMenuItem();
        intermediateMenuItem = new javax.swing.JRadioButtonMenuItem();
        expertMenuItem = new javax.swing.JRadioButtonMenuItem();
        ownMenuItem = new javax.swing.JRadioButtonMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        bestTimesMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        exitMenuItem = new javax.swing.JMenuItem();
        Odratavanie = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.BorderLayout());

        topPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 2, 5), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        topPanel.setLayout(new java.awt.BorderLayout());

        infoPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        infoPanel.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel2.setLayout(new java.awt.BorderLayout());

        minesLeftLabel.setBackground(java.awt.Color.black);
        minesLeftLabel.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        minesLeftLabel.setForeground(java.awt.Color.red);
        minesLeftLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        minesLeftLabel.setText("888");
        minesLeftLabel.setMaximumSize(new java.awt.Dimension(50, 30));
        minesLeftLabel.setMinimumSize(new java.awt.Dimension(50, 30));
        minesLeftLabel.setOpaque(true);
        minesLeftLabel.setPreferredSize(new java.awt.Dimension(50, 30));
        jPanel2.add(minesLeftLabel, java.awt.BorderLayout.CENTER);

        infoPanel.add(jPanel2, java.awt.BorderLayout.WEST);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel3.setLayout(new java.awt.BorderLayout());

        timeLabel.setBackground(java.awt.Color.black);
        timeLabel.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        timeLabel.setForeground(java.awt.Color.red);
        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLabel.setText("888");
        timeLabel.setMaximumSize(new java.awt.Dimension(50, 30));
        timeLabel.setMinimumSize(new java.awt.Dimension(50, 30));
        timeLabel.setOpaque(true);
        timeLabel.setPreferredSize(new java.awt.Dimension(50, 30));
        jPanel3.add(timeLabel, java.awt.BorderLayout.CENTER);

        infoPanel.add(jPanel3, java.awt.BorderLayout.EAST);

        newButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/smile.gif"))); // NOI18N
        newButton.setFocusPainted(false);
        newButton.setFocusable(false);
        newButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });
        jPanel4.add(newButton);

        infoPanel.add(jPanel4, java.awt.BorderLayout.CENTER);

        topPanel.add(infoPanel, java.awt.BorderLayout.CENTER);

        jPanel1.add(topPanel, java.awt.BorderLayout.NORTH);

        contentPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 5, 5), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        jPanel1.add(contentPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        gameMenu.setMnemonic('g');
        gameMenu.setText("Game");
        gameMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameMenuActionPerformed(evt);
            }
        });

        newMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        newMenuItem.setMnemonic('n');
        newMenuItem.setText("New");
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(newMenuItem);
        gameMenu.add(jSeparator1);

        buttonGroup.add(beginnerMenuItem);
        beginnerMenuItem.setMnemonic('b');
        beginnerMenuItem.setText("Beginner");
        beginnerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beginnerMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(beginnerMenuItem);

        buttonGroup.add(intermediateMenuItem);
        intermediateMenuItem.setMnemonic('i');
        intermediateMenuItem.setText("Intermediate");
        intermediateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intermediateMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(intermediateMenuItem);

        buttonGroup.add(expertMenuItem);
        expertMenuItem.setMnemonic('e');
        expertMenuItem.setSelected(true);
        expertMenuItem.setText("Expert");
        expertMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expertMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(expertMenuItem);

        buttonGroup.add(ownMenuItem);
        ownMenuItem.setMnemonic('o');
        ownMenuItem.setText("OWN");
        ownMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OwnSettings(evt);
            }
        });
        gameMenu.add(ownMenuItem);
        gameMenu.add(jSeparator3);

        bestTimesMenuItem.setText("Best times...");
        bestTimesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bestTimesMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(bestTimesMenuItem);
        gameMenu.add(jSeparator2);

        exitMenuItem.setMnemonic('e');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(exitMenuItem);

        Odratavanie.setText("Beat Best Time Mode");
        Odratavanie.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Odratavanie.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                OdratavanieStateChanged(evt);
            }
        });
        Odratavanie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OdratavanieActionPerformed(evt);
            }
        });
        Odratavanie.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                OdratavaniePropertyChange(evt);
            }
        });
        gameMenu.add(Odratavanie);

        menuBar.add(gameMenu);

        setJMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        newMenuItemActionPerformed(null);
    }//GEN-LAST:event_newButtonActionPerformed

    private void expertMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expertMenuItemActionPerformed
        Minesweeper.getInstance().setSetting(Settings.EXPERT);
        Minesweeper.getInstance().newGame();
    }//GEN-LAST:event_expertMenuItemActionPerformed

    private void intermediateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intermediateMenuItemActionPerformed
        Minesweeper.getInstance().setSetting(Settings.INTERMEDIATE);
        Minesweeper.getInstance().newGame();
    }//GEN-LAST:event_intermediateMenuItemActionPerformed

    private void beginnerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beginnerMenuItemActionPerformed
        Minesweeper.getInstance().setSetting(Settings.BEGINNER);
        Minesweeper.getInstance().newGame();
    }//GEN-LAST:event_beginnerMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        Minesweeper.getInstance().newGame();
    }//GEN-LAST:event_newMenuItemActionPerformed

    private void gameMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameMenuActionPerformed
    }//GEN-LAST:event_gameMenuActionPerformed

    private void bestTimesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bestTimesMenuItemActionPerformed
        new BestTimesDialog(this, true).setVisible(true);
    }//GEN-LAST:event_bestTimesMenuItemActionPerformed

    private void OwnSettings(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OwnSettings
        new OwnSettingsDialog(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_OwnSettings

    private void OdratavanieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OdratavanieActionPerformed
        Minesweeper.getInstance().newGame();
    }//GEN-LAST:event_OdratavanieActionPerformed

    private void OdratavanieStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_OdratavanieStateChanged
    }//GEN-LAST:event_OdratavanieStateChanged

    private void OdratavaniePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_OdratavaniePropertyChange
    }//GEN-LAST:event_OdratavaniePropertyChange

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem Odratavanie;
    private javax.swing.JRadioButtonMenuItem beginnerMenuItem;
    private javax.swing.JMenuItem bestTimesMenuItem;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JRadioButtonMenuItem expertMenuItem;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JRadioButtonMenuItem intermediateMenuItem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel minesLeftLabel;
    private javax.swing.JButton newButton;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JRadioButtonMenuItem ownMenuItem;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
