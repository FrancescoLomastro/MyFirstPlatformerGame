package org.example.Props.Animated;


import org.example.Constants.Prop;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.example.Constants.Motion.Dirctions.RIGHT;
import static org.example.Constants.Prop.Cannon.*;
import static org.example.Constants.Window.SCALE;

public class CannonBall extends AnimatedProp {
    private static BufferedImage image = LoadImage();
    private int direction;

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
    public void update() {}


    /**
     * This method is used by the cannon to move the projective around
     */
    public void updatePosition(){
        hitbox.x += direction * CANNON_BALL_SPEED;
    }

    public void draw(Graphics g, int xLvlOffset){
        g.drawImage(image, (int) (hitbox.x - xLvlOffset), (int) hitbox.y, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);
        debug_drawHitbox(g,xLvlOffset);
    }



    private static BufferedImage LoadImage() {
        return LoadContent.GetResourceAsBufferedImage(LoadContent.CANNON_BALL);
    }
}
