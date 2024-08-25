package org.example.UI.Overlays;


import org.example.GameScenes.PlayScene;
import org.example.GameScenes.Scene;
import org.example.UI.UrmButton;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static org.example.Constants.UI.URMButtons.URM_SIZE;
import static org.example.Constants.Window.*;
import static org.example.Utility.HelpMethods.IsMouseIn;

public class GameOverOverlay {
    private BufferedImage img;
    private int imgX, imgY, imgW, imgH;
    private UrmButton rageQuit, playAgain;

    public GameOverOverlay() {
        createImg();
        createButtons();
    }

    private void createButtons() {
        int menuX = (int) (335 * SCALE);
        int playX = (int) (440 * SCALE);
        int y = (int) (195 * SCALE);
        playAgain = new UrmButton(playX, y, URM_SIZE, URM_SIZE, 0);
        rageQuit = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }


    private void createImg() {
        img = LoadContent.GetResourceAsBufferedImage(LoadContent.DEATH_BOARD);
        imgW = (int) (img.getWidth() * SCALE);
        imgH = (int) (img.getHeight() * SCALE);
        imgX =  GAME_WIDTH /2 - imgW / 2;
        imgY = (int) (100 * SCALE);

    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        g.drawImage(img, imgX, imgY, imgW, imgH, null);
        rageQuit.draw(g);
        playAgain.draw(g);

    }


    public void keyPressed(KeyEvent e) {
        
    }


    public void mouseMoved(MouseEvent e) {
        playAgain.setMouseOver(false);
        rageQuit.setMouseOver(false);

        if(IsMouseIn(e, rageQuit)){
            rageQuit.setMouseOver(true);
        }else if(IsMouseIn(e, playAgain)){
            playAgain.setMouseOver(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(IsMouseIn(e, rageQuit)){
            if(rageQuit.isMousePressed()){
                PlayScene.getInstance().restart();
                Scene.changeScene(Scene.MENU);
            }
        }else if(IsMouseIn(e, playAgain)){
            if(playAgain.isMousePressed()){
                PlayScene.getInstance().reset();
            }
        }

        rageQuit.resetBooleans();
        playAgain.resetBooleans();
    }

    public void mousePressed(MouseEvent e) {
        if(IsMouseIn(e, rageQuit)){
            rageQuit.setMousePressed(true);
        }else if(IsMouseIn(e, playAgain)){
            playAgain.setMousePressed(true);
        }
    }

    public void update(){
        rageQuit.update();
        playAgain.update();
    }


}
