package org.example.Props;


import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.Cannon.*;
import static org.example.Constants.Window.SCALE;

public class CannonBall {
    private static BufferedImage image = LoadImage();
    private Rectangle2D.Float hitbox;
    private int dir;
    private boolean active=true;

    public CannonBall(int x, int y, int dir) {
        int xOffset = (int) (-3 * SCALE);
        int yOffset = (int) (5 * SCALE);
        if(dir == 1) {
            xOffset = (int) (29 *SCALE);
        }
        this.hitbox = new Rectangle2D.Float(x + xOffset,y + yOffset,CANNON_BALL_WIDTH,CANNON_BALL_HEIGHT);
        this.dir = dir;
    }

    private static BufferedImage LoadImage() {
        return LoadContent.GetResourceAsBufferedImage(LoadContent.CANNON_BALL);
    }

    public void updatePosition(){
        hitbox.x += dir * CANNON_BALL_SPEED;
    }

    public void draw(Graphics g, int xLvlOffset){
        g.drawImage(image, (int) (hitbox.x - xLvlOffset), (int) hitbox.y, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);
        debug_drawHitbox(g,xLvlOffset);
    }

    public void debug_drawHitbox(Graphics g, int xLvlOffset) {
        // For debugging the hitbox
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);

    }

    public void setPos(int x, int y){
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
