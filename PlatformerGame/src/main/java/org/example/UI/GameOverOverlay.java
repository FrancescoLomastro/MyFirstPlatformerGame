package org.example.UI;


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
    private UrmButton menu, playAgain;

    public GameOverOverlay() {
        createImg();
        createButtons();
    }

    private void createButtons() {
        int menuX = (int) (335 * SCALE);
        int playX = (int) (440 * SCALE);
        int y = (int) (195 * SCALE);
        playAgain = new UrmButton(playX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }


    private void createImg() {
        img = LoadContent.GetSpriteAtlas(LoadContent.DEATH_SCREEN);
        imgW = (int) (img.getWidth() * SCALE);
        imgH = (int) (img.getHeight() * SCALE);
        imgX =  GAME_WIDTH /2 - imgW / 2;
        imgY = (int) (100 * SCALE);

    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        g.drawImage(img, imgX, imgY, imgW, imgH, null);
        menu.draw(g);
        playAgain.draw(g);

    }


    public void keyPressed(KeyEvent e) {
        
    }


    public void mouseMoved(MouseEvent e) {
        playAgain.setMouseOver(false);
        menu.setMouseOver(false);

        if(IsMouseIn(e, menu)){
            menu.setMouseOver(true);
        }else if(IsMouseIn(e, playAgain)){
            playAgain.setMouseOver(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(IsMouseIn(e, menu)){
            if(menu.isMousePressed()){
                //playing.resetAll();
                //playing.setGameState(Gamestate.MENU);
            }
        }else if(IsMouseIn(e, playAgain)){
            if(playAgain.isMousePressed()){
                //playing.resetAll();
                //playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLvlIndex());
            }
        }

        menu.resetBools();
        playAgain.resetBools();
    }

    public void mousePressed(MouseEvent e) {
        if(IsMouseIn(e, menu)){
            menu.setMousePressed(true);
        }else if(IsMouseIn(e, playAgain)){
            playAgain.setMousePressed(true);
        }
    }

    public void update(){
        menu.update();
        playAgain.update();
    }


}
