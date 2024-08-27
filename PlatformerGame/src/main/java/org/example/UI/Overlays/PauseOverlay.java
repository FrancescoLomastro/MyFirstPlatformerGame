package org.example.UI.Overlays;



import org.example.Audio.AudioManager;
import org.example.GameScenes.PlayScene;
import org.example.GameScenes.Scene;
import org.example.UI.AudioOptions;
import org.example.UI.UrmButton;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static org.example.Constants.UI.URMButtons.*;
import static org.example.Constants.Window.*;
import static org.example.Utility.HelpMethods.IsMouseIn;
import static org.example.Utility.LoadContent.PAUSE_BOARD;

public class PauseOverlay {
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgWidth, bgHeight;
    private UrmButton menuB, replayB, unpauseB;
    private AudioOptions audioOptions;

    public PauseOverlay(AudioOptions audioOptions) {
        loadBackground();
        this.audioOptions = audioOptions;
        createUrmButtons();
    }



    private void createUrmButtons() {
        int menuX = (int) (313 * SCALE);
        int replayX = (int) (387 * SCALE);
        int unpauseX = (int) (462 * SCALE);
        int urmY = (int) (325 * SCALE);

        menuB = new UrmButton(menuX, urmY, URM_SIZE, URM_SIZE, 2);
        replayB = new UrmButton(replayX, urmY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new UrmButton(unpauseX, urmY, URM_SIZE, URM_SIZE, 0);
    }



    private void loadBackground() {
        backgroundImg = LoadContent.GetResourceAsBufferedImage(PAUSE_BOARD);
        bgWidth = (int) (backgroundImg.getWidth() * SCALE);
        bgHeight = (int) (backgroundImg.getHeight() * SCALE);
        bgX  = GAME_WIDTH / 2 - bgWidth/ 2;
        bgY = (int) (25 * SCALE);
    }

    public void update(){
        menuB.update();
        replayB.update();
        unpauseB.update();

        audioOptions.update();
    }

    public void draw(Graphics g){
        g.setColor(new Color(0,0,0,150));
        g.fillRect(0,0, GAME_WIDTH, GAME_HEIGHT);
        g.drawImage(backgroundImg, bgX, bgY, bgWidth, bgHeight, null);
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);

        audioOptions.draw(g);
    }

    public void mouseDragged(MouseEvent e){
        audioOptions.mouseDragged(e);
    }

    public void mousePressed(MouseEvent e) {

        if(IsMouseIn(e,menuB)){
            menuB.setMousePressed(true);
        }else if(IsMouseIn(e,replayB)){
            replayB.setMousePressed(true);
        }else if(IsMouseIn(e,unpauseB)){
            unpauseB.setMousePressed(true);
        }else
            audioOptions.mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
        PlayScene playScene = PlayScene.getInstance();
       if(IsMouseIn(e,menuB)){
            if(menuB.isMousePressed()) {
                playScene.reset();
                Scene.changeScene(Scene.MENU);
                playScene.unpauseGame();
            }
        }else if(IsMouseIn(e,replayB)){
            if(replayB.isMousePressed()){
                playScene.reset();
                playScene.unpauseGame();
                AudioManager.getInstance().goToRestartLevel();
            }
        }else if(IsMouseIn(e,unpauseB)){
            if(unpauseB.isMousePressed())
                playScene.unpauseGame();
        }else
            audioOptions.mouseReleased(e);


        menuB.resetBooleans();
        replayB.resetBooleans();
        unpauseB.resetBooleans();
    }

    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(IsMouseIn(e,menuB));
        replayB.setMouseOver(IsMouseIn(e,replayB));
        unpauseB.setMouseOver(IsMouseIn(e,unpauseB));
        audioOptions.mouseMoved(e);
    }


}
