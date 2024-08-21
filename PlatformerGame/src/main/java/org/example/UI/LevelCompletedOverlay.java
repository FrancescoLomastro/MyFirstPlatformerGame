package org.example.UI;


import org.example.GameScenes.PlayScene;
import org.example.GameScenes.Scene;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static org.example.Constants.UI.URMButtons.URM_SIZE;
import static org.example.Constants.Window.*;
import static org.example.Utility.HelpMethods.IsMouseIn;

public class LevelCompletedOverlay {
    private UrmButton menu, next;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;

    public LevelCompletedOverlay() {
        initImg();
        initButtons();
    }

    private void initButtons() {
        int menuX = (int) (330 * SCALE);
        int nextX = (int) (445 * SCALE);
        int y = (int) (195 * SCALE);
        next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void initImg() {
        img = LoadContent.GetResourceAsBufferedImage(LoadContent.LEVEL_COMPLETE_BOARD);
        bgW = (int) (img.getWidth() * SCALE);
        bgH = (int) (img.getHeight() * SCALE);
        bgX = GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (75 * SCALE);
    }

    public void update(){
        next.update();
        menu.update();
    }

    public void draw(Graphics g){
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        g.drawImage(img, bgX, bgY, bgW, bgH, null);
        next.draw(g);
        menu.draw(g);
    }

    public void mouseMoved(MouseEvent e) {
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if(IsMouseIn(e, menu)){
            menu.setMouseOver(true);
        }else if(IsMouseIn(e, next)){
            next.setMouseOver(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        PlayScene playScene = PlayScene.getInstance();
        if(IsMouseIn(e, menu)){
            if(menu.isMousePressed()){
                playScene.reset();
                Scene.CurrentScene = Scene.MENU;
            }
        }else if(IsMouseIn(e, next)){
            if(next.isMousePressed()){
                playScene.loadNextLevel();
            }
        }

        menu.resetBools();
        next.resetBools();
    }

    public void mousePressed(MouseEvent e) {
        if(IsMouseIn(e, menu)){
            menu.setMousePressed(true);
        }else if(IsMouseIn(e, next)){
            next.setMousePressed(true);
        }
    }


}

