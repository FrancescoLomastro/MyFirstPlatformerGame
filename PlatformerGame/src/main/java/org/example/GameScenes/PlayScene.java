package org.example.GameScenes;

import org.example.Entities.Player;
import org.example.Levels.LevelManager;
import org.example.Main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import static org.example.Constants.Motion.LEFT_LEVEL_BORDER;
import static org.example.Constants.Motion.RIGHT_LEVEL_BORDER;
import static org.example.Constants.Window.GAME_HEIGHT;
import static org.example.Constants.Window.SCALE;

public class PlayScene implements SceneMethods{

    private Player player;
    private LevelManager levelManager;


    private int xLevelOffset;
    private int maxLevelCameraOffset;


    private static PlayScene instance;

    public static void createInstance(Game game){
        if(instance == null)
            instance = new PlayScene(game);
    }

    public static PlayScene getInstance(){
        return instance;
    }

    private PlayScene(Game game) {
        this.levelManager = new LevelManager();
        this.player = new Player(
                levelManager.getPlayerX(),
                levelManager.getPlayerY());
        this.player.addLevelData(levelManager.getBlockIndexes());
        this.maxLevelCameraOffset = levelManager.getMaxLevelCameraOffset();
    }

    public void update() {
        levelManager.update();
        player.update();
        checkCameraOffset();
    }



    public void draw(Graphics g) {
        levelManager.draw(g,xLevelOffset);
        player.draw(g,xLevelOffset);

        //debug_drawLevelBorders(g);
    }




    private void checkCameraOffset() {
        int playerX = (int)player.getX();
        int XFromCurrentCamera = playerX - xLevelOffset;
        if(XFromCurrentCamera > RIGHT_LEVEL_BORDER)
            xLevelOffset += XFromCurrentCamera - RIGHT_LEVEL_BORDER;
        else if(XFromCurrentCamera < LEFT_LEVEL_BORDER)
            xLevelOffset += XFromCurrentCamera - LEFT_LEVEL_BORDER;

        if(xLevelOffset < 0)
            xLevelOffset = 0;
        else if(xLevelOffset > maxLevelCameraOffset)
            xLevelOffset = maxLevelCameraOffset;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        player.mouseClicked(e);
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

    private void debug_drawLevelBorders(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(RIGHT_LEVEL_BORDER,0,2, GAME_HEIGHT);
        g.fillRect(LEFT_LEVEL_BORDER, 0,-2, GAME_HEIGHT);
    }

    public boolean isSwordPicked() {
        return levelManager.isSwordPicked(player);
    }

    public Rectangle2D.Float getPlayerHitbox(){
        return player.getHitbox();
    }

    public void hitPlayer(int damage) {
        player.alterHealth(damage);
    }

    public void checkEnemyAttacked(Rectangle2D.Float attackBox) {
        levelManager.checkEnemyAttacked(attackBox);
    }
}
