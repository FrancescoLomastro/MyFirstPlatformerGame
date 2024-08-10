package org.example.GameScenes;

import org.example.Main.Game;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Random;

import static org.example.Constants.Prop.Environment.*;
import static org.example.Constants.Sprites.Level.*;
import static org.example.Constants.Window.*;

public class MenuScene implements SceneMethods{
    private Game game;
    private BufferedImage deepBackground;
    private BufferedImage[] bigClouds, smallCloud;
    private float bigCloudsDrawn;
    private Random random;
    private float cloudsOffset;
    private int maxCloudsOffset;


    private int[] drawnBigCloudsIndexes;
    private int initialCloudOffset;


    private BufferedImage[] shipImages;
    private int shipAnimationFrame;
    private int shipAnimationTick;


    private int direction = 1;
    private float yoffset = 0;


    public MenuScene(Game game) {
        this.game = game;
        this.random = new Random();
        this.bigCloudsDrawn = 0.0f;
        this.cloudsOffset = 0;
        this.initialCloudOffset = 0;
        this.drawnBigCloudsIndexes = new int[3];
        this.shipAnimationTick = 0;
        loadAnimations();
        createBigClouds();
    }

    private void loadAnimations() {
        deepBackground = LoadContent.GetSpriteAtlas(LoadContent.MENUSCENE_DEEP_BACKGROUND);
        bigClouds = new BufferedImage[3];
        bigClouds[0] = LoadContent.GetSpriteAtlas(LoadContent.BIG_CLOUD_1);
        bigClouds[1] = LoadContent.GetSpriteAtlas(LoadContent.BIG_CLOUD_2);
        bigClouds[2] = LoadContent.GetSpriteAtlas(LoadContent.BIG_CLOUD_3);


        BufferedImage img = LoadContent.GetSpriteAtlas(LoadContent.SHIP_ATLAS);
        shipImages = new BufferedImage[8];
        for(int i = 0; i < SHIP_SPRITE_AMOUNT; i++) {
            shipImages[i] = img.getSubimage(i*80, 0, 80, 65);
        }
    }


    private void drawBigClouds(Graphics g, int offset) {
        int cloudInitialPosition = 0;
        for(int i = 0; i<drawnBigCloudsIndexes.length; i++){
            g.drawImage(bigClouds[drawnBigCloudsIndexes[i]],
                    cloudInitialPosition - offset + initialCloudOffset,
                    (int)(218 * SCALE), BIG_CLOUD_WIDTH[drawnBigCloudsIndexes[i]], BIG_CLOUD_HEIGHT[drawnBigCloudsIndexes[i]], null);
            cloudInitialPosition += (int) (BIG_CLOUD_WIDTH[drawnBigCloudsIndexes[i]] + (300*SCALE));
        }
    }

    private void drawSmallClouds(Graphics g, int xLvlOffset) {
        int sizeDrawn = 0;
        //g.drawImage(smallCloud, sizeDrawn - (int)(xLvlOffset * 0.7), (int)(110* SCALE), SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
    }

    @Override
    public void update() {
        updateClouds();
        updateShipAnimationTick();
    }

    private void updateShipAnimationTick() {
        shipAnimationTick++;
        if(yoffset >= 3 || yoffset <= -3){
            direction = direction*-1;
        }
        yoffset+=direction*0.025f;

        if (shipAnimationTick >= SHIP_ANIMATION_SPEED) {
            shipAnimationTick = 0;
            shipAnimationFrame++;
            if (shipAnimationFrame >= SHIP_SPRITE_AMOUNT)
                shipAnimationFrame = 0;
        }
    }

    private void updateClouds() {
        cloudsOffset = cloudsOffset+ 0.2f * SCALE;
        if(cloudsOffset > maxCloudsOffset){
            cloudsOffset = 0;
            initialCloudOffset = GAME_WIDTH;
            createBigClouds();
        }
    }

    private void createBigClouds() {
        int cloudIndex;
        for (int i = 0; i<3; i++){
            cloudIndex= random.nextInt(3);
            drawnBigCloudsIndexes[i] = cloudIndex;
            maxCloudsOffset += BIG_CLOUD_WIDTH[cloudIndex];
        }
        maxCloudsOffset += (int) (600 * SCALE);
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(deepBackground, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        drawBigClouds(g, (int) cloudsOffset);
        drawShip(g);
    }

    private void drawShip(Graphics g) {
        g.drawImage(shipImages[shipAnimationFrame], 100, (int) (500 + yoffset), (int) (80*SCALE), (int) (76*SCALE), null);
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

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
