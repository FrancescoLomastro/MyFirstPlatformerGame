package org.example.Props.Animated;


import org.example.Audio.AudioManager;
import org.example.GameScenes.PlayScene;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Motion.Dirctions.DOWN;
import static org.example.Constants.Motion.Dirctions.UP;
import static org.example.Constants.Prop.Potion.*;
import static org.example.Constants.Window.SCALE;
import static org.example.Utility.LoadContent.POTION_SPRITE;

/**
 * Potion class for animated potion prop
 */
public class Potion extends AnimatedProp {
    private static BufferedImage[] sprites = LoadImages();

    private float floatingOffset;
    private final int maxFloatingOffset;
    private int floatingDir = UP;


    public Potion(int x, int y) {
        super(x, y);
        initHitbox(POTION_HIT_BOX_WIDTH,POTION_HIT_BOX_HEIGHT);
        hitbox.x += (int)(9 * SCALE);
        hitbox.y += (int)(12 * SCALE);
        maxFloatingOffset = (int) (10 * SCALE);
    }

    public void draw(Graphics g, int xLvlOffset){
        g.drawImage(sprites[animationFrame],
                    (int)(hitbox.x - xDrawOffset - xLvlOffset),
                    (int)(hitbox.y - yDrawOffset),
                    POTION_WIDTH,
                    POTION_HEIGHT,
                    null);
        //debug_drawHitbox(g, xLvlOffset);
    }

    public void update() {
        updateAnimationTick();
        updateFloating();
        updatePicking();
    }

    private void updatePicking() {
        PlayScene playScene = PlayScene.getInstance();
        if(hitbox.intersects(playScene.getPlayerHitbox())){
            playScene.hitPlayer(-30);
            AudioManager.getInstance().playBottleOpen();
            active = false;
        }
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= POTION_ANIMATION_SPEED) {
            animationTick = 0;
            animationFrame++;
            if (animationFrame >= POTION_SPRITE_AMOUNT) {
                animationFrame = 0;
            }
        }
    }


    @Override
    public void reset() {
        super.reset();
        floatingOffset = 0;
        floatingDir = UP;
    }

    /**
     * Updates the floating animation of the potion.
     */
    private void updateFloating() {
        floatingOffset += SCALE *0.075f * floatingDir;
        if(floatingOffset >= maxFloatingOffset) {
            floatingDir = DOWN;
        }else if(floatingOffset <= 0) {
            floatingDir = UP;
        }
        hitbox.y = y +floatingOffset;
    }


    private static BufferedImage[] LoadImages() {
        BufferedImage[] imgs = new BufferedImage[7];
        BufferedImage temp = LoadContent.GetResourceAsBufferedImage(POTION_SPRITE);
        for(int i = 0; i < POTION_SPRITE_AMOUNT; i++) {
            imgs[i] = temp.getSubimage(i*POTION_WIDTH_DEFAULT, 0, POTION_WIDTH_DEFAULT, POTION_HEIGHT_DEFAULT);
        }
        return imgs;
    }

}
