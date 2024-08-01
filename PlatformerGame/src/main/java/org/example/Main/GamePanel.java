package org.example.Main;

import org.example.Inputs.Keyboard;
import org.example.Inputs.Mouse;

import javax.swing.*;
import java.awt.*;

import static org.example.Constants.Window.*;

public class GamePanel extends JPanel {
    private final Game game;

    public GamePanel(Game game) {
        this.game = game;
        setPanelSize();
        addMouseListener(new Mouse(game));
        addKeyListener(new Keyboard(game));
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.draw(g);
    }


    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
    }


}
