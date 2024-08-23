package org.example.Props.Animated;


import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.example.Constants.Motion.Dirctions.RIGHT;
import static org.example.Constants.Prop.Cannon.*;
import static org.example.Constants.Window.SCALE;
import static org.example.Utility.LoadContent.CANNON_BALL_EXPLOSION_SPRITE;

public class CannonBall extends AnimatedProp {
    private static BufferedImage image = LoadContent.GetResourceAsBufferedImage(LoadContent.CANNON_BALL);;
    private static BufferedImage[] explosion_image = LoadImages();
    private int direction;
    protected boolean doAnimation;

    public CannonBall(int x, int y, int direction) {
        super(x, y);
        this.direction = direction;
        this.active = true;
        int xOffset = (int) (-3 * SCALE);
        int yOffset = (int) (5 * SCALE);
        if(direction == RIGHT)
            xOffset = (int) (29 *SCALE);
        this.hitbox = new Rectangle2D.Float(x + xOffset,y + yOffset,CANNON_BALL_WIDTH,CANNON_BALL_HEIGHT);
    }

    @Override
    public void update() {
        if(doAnimation){
            updateAnimationTick();
        }
        else
            updatePosition();
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= CANNON_ANIMATION_SPEED) {
            animationTick = 0;
            animationFrame++;
            if (animationFrame >= CANNON_BALL_EXPLOSION_SPRITE_AMOUNT) {
                animationFrame = 0;
                active = false;
            }
        }
    }


    /**
     * This method is used by the cannon to move the projective around
     */
    public void updatePosition(){
        hitbox.x += direction * CANNON_BALL_SPEED;
    }

    public void draw(Graphics g, int xLvlOffset){
        if(doAnimation)
            g.drawImage(explosion_image[animationFrame], (int) (hitbox.x - xLvlOffset), (int) hitbox.y, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);
        else
            g.drawImage(image, (int) (hitbox.x - xLvlOffset), (int) hitbox.y, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);
        //debug_drawHitbox(g,xLvlOffset);
    }

    public void crush() {
        doAnimation = true;
    }

    private static BufferedImage[] LoadImages() {
        BufferedImage[] imgs = new BufferedImage[7];
        BufferedImage img = LoadContent.GetResourceAsBufferedImage(CANNON_BALL_EXPLOSION_SPRITE);
        for(int i = 0; i < CANNON_BALL_EXPLOSION_SPRITE_AMOUNT; i++) {
            imgs[i] = img.getSubimage(i* CANNON_BALL_EXPLOSION_WIDTH_DEFAULT, 0, CANNON_BALL_EXPLOSION_WIDTH_DEFAULT, CANNON_BALL_EXPLOSION_HEIGHT_DEFAULT);
        }
        return imgs;
    }




}
