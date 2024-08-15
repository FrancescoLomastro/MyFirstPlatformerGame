package org.example.Inputs;

import org.example.Main.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {
    private final Game game;

    public Mouse(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        game.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        game.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        game.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        game.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        game.mouseMoved(e);
    }
}
