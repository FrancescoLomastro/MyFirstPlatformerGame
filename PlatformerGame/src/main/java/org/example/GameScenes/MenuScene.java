package org.example.GameScenes;

import org.example.Main.Game;
import org.example.UI.MenuButton;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import static org.example.Constants.Prop.BigCloud.*;
import static org.example.Constants.Prop.Seagull.*;
import static org.example.Constants.Prop.Ship.*;
import static org.example.Constants.UI.*;
import static org.example.Constants.Window.*;
import static org.example.Utility.HelpMethods.IsMouseIn;

public class MenuScene implements SceneMethods{
    private Game game;
    private Random random;
    private BufferedImage deepBackground;

    private BufferedImage[] bigClouds;
    private float cloudsOffsetX;
    private int maxCloudsOffsetX;
    private int[] drawnBigCloudsIndexes;

    private BufferedImage smallCloud;

    private BufferedImage[] shipImages;
    private int shipAnimationFrame;
    private int shipAnimationTick;
    private int shipFloatingDirection = 1;
    private float shipFloatingOffset = 0;

    private BufferedImage[] seagullImages;
    private int seagullAnimationFrame;
    private int seagullAnimationTick;
    private float seagullOffsetX;
    private int maxSeagullOffsetX;
    private int seagullFloatingDirection = -1;
    private float seagullFloatingOffset = 0;
    private int seagullRandomOffsetY;

    private BufferedImage menuBackground;
    private MenuButton[] buttons;


    public MenuScene(Game game) {
        this.game = game;
        this.random = new Random();
        this.cloudsOffsetX = 0;
        this.drawnBigCloudsIndexes = new int[3];
        this.shipAnimationTick = 0;
        this.seagullAnimationTick = 0;
        this.shipAnimationFrame = 0;
        this.seagullAnimationFrame = 0;
        this.seagullOffsetX = GAME_WIDTH/2 ;
        this.seagullRandomOffsetY = random.nextInt(100);
        loadAnimations();
        createButtons();
        createBigClouds();
    }

    private void createButtons() {
        this.buttons = new MenuButton[3];
        
        buttons[0] = new MenuButton((int) (520*SCALE), (int) (160 * SCALE), 0, Scene.PLAY);
        buttons[1] = new MenuButton((int) (520*SCALE), (int) (220 * SCALE), 1, Scene.SETTINGS);
        buttons[2] = new MenuButton((int) (520*SCALE), (int) (280 * SCALE), 2, Scene.QUIT);
    }

    private void loadAnimations() {
        deepBackground = LoadContent.GetResourceAsBufferedImage(LoadContent.MENUSCENE_DEEP_BACKGROUND);
        bigClouds = new BufferedImage[3];
        bigClouds[0] = LoadContent.GetResourceAsBufferedImage(LoadContent.BIG_CLOUD_1);
        bigClouds[1] = LoadContent.GetResourceAsBufferedImage(LoadContent.BIG_CLOUD_2);
        bigClouds[2] = LoadContent.GetResourceAsBufferedImage(LoadContent.BIG_CLOUD_3);
        smallCloud = LoadContent.GetResourceAsBufferedImage(LoadContent.SMALL_CLOUD);

        BufferedImage img = LoadContent.GetResourceAsBufferedImage(LoadContent.SHIP_SPRITE);
        shipImages = new BufferedImage[8];
        for(int i = 0; i < SHIP_SPRITE_AMOUNT; i++) {
            shipImages[i] = img.getSubimage(i*80, 0, 80, 65);
        }

        img = LoadContent.GetResourceAsBufferedImage(LoadContent.SEAGULL_SPRITE);
        seagullImages = new BufferedImage[6];
        for(int i = 0; i < SEAGULL_SPRITE_AMOUNT; i++) {
            seagullImages[i] = img.getSubimage(i*30, 0, 30, 37);
        }

        menuBackground = LoadContent.GetResourceAsBufferedImage(LoadContent.MENU_BOARD);
    }






    private void drawBigClouds(Graphics g) {
        int cloudInitialPosition = 0;
        for(int i = 0; i<drawnBigCloudsIndexes.length; i++){
            g.drawImage(bigClouds[drawnBigCloudsIndexes[i]],
                    cloudInitialPosition - (int)(cloudsOffsetX),
                    (int)(218 * SCALE), BIG_CLOUD_WIDTH[drawnBigCloudsIndexes[i]], BIG_CLOUD_HEIGHT[drawnBigCloudsIndexes[i]], null);
            cloudInitialPosition += BIG_CLOUD_WIDTH[drawnBigCloudsIndexes[i]] + 150*SCALE;
        }
    }

    private void drawSmallClouds(Graphics g) {
        g.drawImage(smallCloud, (int) (180*SCALE), (int)(90* SCALE), SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
        g.drawImage(smallCloud, (int) (280*SCALE), (int)(150* SCALE), SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
    }

    @Override
    public void update() {
        updateClouds();
        updateShipAnimationTick();
        updateSeagullAnimationTick();
        buttons[0].update();
        buttons[1].update();
        buttons[2].update();
    }

    private void updateShipAnimationTick() {
        shipAnimationTick++;
        if(shipFloatingOffset >= 3 || shipFloatingOffset <= -3){
            shipFloatingDirection = shipFloatingDirection *-1;
        }
        shipFloatingOffset += shipFloatingDirection *0.015f;

        if (shipAnimationTick >= SHIP_ANIMATION_SPEED) {
            shipAnimationTick = 0;
            shipAnimationFrame++;
            if (shipAnimationFrame >= SHIP_SPRITE_AMOUNT)
                shipAnimationFrame = 0;
        }
    }

    private void updateSeagullAnimationTick() {
        seagullAnimationTick++;
        if(seagullFloatingOffset >= 3 || seagullFloatingOffset <= -3){
            seagullFloatingDirection = seagullFloatingDirection *-1;
        }
        seagullFloatingOffset += seagullFloatingOffset *0.025f;

        if (seagullAnimationTick >= SEAGULL_ANIMATION_SPEED) {
            seagullAnimationTick = 0;
            seagullAnimationFrame++;
            if (seagullAnimationFrame >= SEAGULL_SPRITE_AMOUNT)
                seagullAnimationFrame = 0;
        }


        seagullOffsetX = seagullOffsetX - 0.5f*SCALE;
        if(seagullOffsetX <= -SEAGULL_WIDTH){
            seagullOffsetX = GAME_WIDTH ;
            seagullRandomOffsetY = random.nextInt(100);
        }


    }

    private void updateClouds() {
        cloudsOffsetX = cloudsOffsetX + 0.2f*SCALE;
        if(cloudsOffsetX > maxCloudsOffsetX){
            createBigClouds();
            cloudsOffsetX = -GAME_WIDTH;
        }
    }

    private void createBigClouds() {
        int cloudIndex;
        maxCloudsOffsetX = 0;
        for (int i = 0; i<3; i++){
            cloudIndex= random.nextInt(3);
            drawnBigCloudsIndexes[i] = cloudIndex;
            maxCloudsOffsetX += BIG_CLOUD_WIDTH[cloudIndex];
        }
        maxCloudsOffsetX += 300*SCALE;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(deepBackground, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        drawBigClouds(g);
        drawShip(g);
        drawSeagull(g);
        drawSmallClouds(g);
        g.drawImage(menuBackground, (int) (450*SCALE), (int) (50*SCALE), MENU_BACKGROUND_WIDTH, MENU_BACKGROUND_HEIGHT, null);
        buttons[0].draw(g);
        buttons[1].draw(g);
        buttons[2].draw(g);
    }

    private void drawSeagull(Graphics g) {
        g.drawImage(seagullImages[seagullAnimationFrame],
                (int) seagullOffsetX,
                (int) ((300 + shipFloatingOffset + seagullRandomOffsetY) * SCALE),
                SEAGULL_WIDTH, SEAGULL_HEIGHT, null);
    }

    private void drawShip(Graphics g) {
        g.drawImage(shipImages[shipAnimationFrame], (int) (50 * SCALE), (int) ((252 + shipFloatingOffset) * SCALE), SHIP_WIDTH, SHIP_HEIGHT, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton button : buttons) {
            if (IsMouseIn(e,button)) {
                button.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButton mb : buttons){
            if(IsMouseIn(e,mb)){
                if(mb.isMousePressed()){  //l'utente potrebbe cliccare fuori e rilasciare dentro. Non voglio che succeda
                    mb.changeScene();
                }
                break;
            }
        }
        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton button : buttons){
            button.setMouseOver(false);
        }
        for(MenuButton button : buttons){
            if(IsMouseIn(e,button)){
                button.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void resetButtons() {
        for(MenuButton mb : buttons){
            mb.resetBooleans();
        }
    }
}
