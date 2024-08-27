package org.example.UI.Overlays;


import org.example.Audio.AudioManager;
import org.example.GameScenes.PlayScene;
import org.example.GameScenes.Scene;
import org.example.UI.UrmButton;
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
        menu.setMouseOver(IsMouseIn(e, menu));
        next.setMouseOver(IsMouseIn(e, next));
    }

    public void mouseReleased(MouseEvent e) {
        PlayScene playScene = PlayScene.getInstance();
        if(IsMouseIn(e, menu)){
            if(menu.isMousePressed()){
                playScene.reset();
                Scene.changeScene(Scene.MENU);
            }
        }else if(IsMouseIn(e, next)){
            if(next.isMousePressed()){
                playScene.loadNextLevel();
                AudioManager.getInstance().goToNextLevel();
            }
        }

        menu.resetBooleans();
        next.resetBooleans();
    }

    public void mousePressed(MouseEvent e) {
        if(IsMouseIn(e, menu)){
            menu.setMousePressed(true);
        }else if(IsMouseIn(e, next)){
            next.setMousePressed(true);
        }
    }


}

