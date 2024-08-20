package org.example.Main;

import org.example.Inputs.Keyboard;
import org.example.Inputs.Mouse;

import javax.swing.*;
import java.awt.*;

import static org.example.Constants.Window.*;

/**
 * Class used to create the game panel.
 * It uses a JPanel to create the panel.
 * It also adds listeners for mouse and keyboard inputs.
 */
public class GamePanel extends JPanel {
    private final Game game;

    public GamePanel(Game game) {
        this.game = game;
        setPanelSize();
        Mouse mouseListener = new Mouse(game);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        addKeyListener(new Keyboard(game));
    }


    /**
     * This method is used by a JPanel to update the current frame.
     * @param g the object used to draw figures, images, lines etc.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.draw(g);
    }

    /**
     * This method is used to set the size of the panel.
     */
    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
    }

    /**
     * This method is used to deliver the focus lost event to the game.
     */
    public void windowFocusLost() {
        game.windowFocusLost();
    }
}
