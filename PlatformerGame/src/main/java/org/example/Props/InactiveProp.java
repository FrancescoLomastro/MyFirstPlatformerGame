package org.example.Props;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.InactiveProp.*;

public class InactiveProp {
    private int x;
    private int y;
    private int objectType;
    private static BufferedImage[] images = LoadAnimation();

    private static BufferedImage[] LoadAnimation() {
        BufferedImage[] imgs = new BufferedImage[6];
        BufferedImage temp = LoadContent.GetSpriteAtlas(LoadContent.INACTIVE_PROPS);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(IP_WIDTH_DEFAULT*i, 0, IP_WIDTH_DEFAULT, IP_WIDTH_DEFAULT);
        }
        return imgs;
    }

    public InactiveProp(int x, int y, int objectType) {
        this.y = y;
        this.x = x;
        this.objectType = objectType;
    }


    public void draw(Graphics g, int xLvlOffset){
        g.drawImage(images[objectType], x - xLvlOffset, y,IP_WIDTH,IP_HEIGHT, null);
    }
}
