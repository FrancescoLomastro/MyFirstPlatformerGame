package org.example.Inputs;

import org.example.Main.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class is responsible for handling keyboard inputs for the game.
 * It implements the KeyListener interface and passes the key events to the game class.
 */
public class Keyboard implements KeyListener{
    private final Game game;

    public Keyboard(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        game.keyReleased(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        game.keyPressed(e);
    }
}
