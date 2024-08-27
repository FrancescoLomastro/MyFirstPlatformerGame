package org.example.GameScenes;

import org.example.Main.Game;
import org.example.UI.AudioOptions;
import org.example.UI.UrmButton;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static org.example.Constants.UI.URMButtons.URM_SIZE;
import static org.example.Constants.Window.*;
import static org.example.GameScenes.Scene.MENU;
import static org.example.Utility.HelpMethods.IsMouseIn;
import static org.example.Utility.LoadContent.SETTINGS_DEEP_BACKGROUND;

public class SettingsScene implements SceneMethods {
    private BufferedImage deepBackgroundImg;
    private AudioOptions audioOptions;
    private BufferedImage settingsBackground;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuB;


    public SettingsScene(Game game) {
        loadImages();
        loadButton();
        audioOptions = game.getAudioOptions();
    }

    private void loadButton() {
        int menuX = (int) (387 * SCALE);
        int menuY = (int) (325 * SCALE);

        menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
    }

    private void loadImages() {
        settingsBackground = LoadContent.GetResourceAsBufferedImage(LoadContent.SETTINGS_BOARD);
        deepBackgroundImg = LoadContent.GetResourceAsBufferedImage(SETTINGS_DEEP_BACKGROUND);

        bgW = (int) (settingsBackground.getWidth() * SCALE);
        bgH = (int) (settingsBackground.getHeight() * SCALE);
        bgX = GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (33 * SCALE);
    }

    @Override
    public void update() {
        menuB.update();
        audioOptions.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(deepBackgroundImg, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        g.drawImage(settingsBackground, bgX, bgY, bgW, bgH, null);

        menuB.draw(g);
        audioOptions.draw(g);

    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (IsMouseIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else audioOptions.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (IsMouseIn(e, menuB)) {
            if (menuB.isMousePressed())
                Scene.changeScene(Scene.MENU);
        } else audioOptions.mouseReleased(e);

        menuB.resetBooleans();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (IsMouseIn(e, menuB))
            menuB.setMouseOver(true);
        else {
            menuB.setMouseOver(false);
            audioOptions.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            Scene.changeScene(Scene.MENU);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }


}
