package org.example.Entities;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Sprites.PLAYER_ANIMATION_SPEED;
import static org.example.Constants.Sprites.Player.*;

public class Player extends Entity{
    private BufferedImage[][][] playerImages;


    public Player(float y, float x, int width, int height) {
        super(y, x, width, height);
        initHitbox(20,27);
        loadAnimations();
    }

    private void loadAnimations() {
        BufferedImage img = LoadContent.GetSpriteAtlas(LoadContent.PLAYER_NO_SWORD_ATLAS);

        playerImages = new BufferedImage[2][7][8];
        for (int j = 0; j < playerImages[0].length; j++)
            for (int i = 0; i < playerImages[0][j].length; i++)
                playerImages[0][j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
    }

    public void update(){
        updateAnimationTick();
    }

    public void draw(Graphics g, int xLvlOffset){
        g.drawImage(playerImages[0][state][animationFrame],(int) (hitbox.x) - xLvlOffset, (int) (hitbox.y), hitbox_width , hitbox_height, null);
    }







    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= PLAYER_ANIMATION_SPEED){
            animationTick = 0;
            animationFrame++;
            if(animationFrame >= getPlayerSpriteAmount(state))
                animationFrame = 0;
        }
    }

    private int getPlayerSpriteAmount(int state) {
        switch (state) {
            case IDLE:
                return 5;
            case RUN:
                return 6;
            default:
                return 1;
        }
    }


}
