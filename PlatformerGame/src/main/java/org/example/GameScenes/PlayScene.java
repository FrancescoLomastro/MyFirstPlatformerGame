package org.example.GameScenes;

import org.example.Entities.Player;
import org.example.Levels.LevelManager;
import org.example.Main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.example.Constants.Window.SCALE;

public class PlayScene implements SceneMethods{

    private Player player;
    private LevelManager levelManager;


    public PlayScene(Game game) {
        this.levelManager = new LevelManager();
        this.player = new Player(
                levelManager.getPlayerX(),
                levelManager.getPlayerY(),
                (int) (64 * SCALE), (int) (40 * SCALE));
        this.player.addLevelData(levelManager.getBlockIndexes());
    }

    public void update() {
        player.update();
    }

    public void draw(Graphics g) {
        player.draw(g,0);
        levelManager.draw(g,0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }
}
