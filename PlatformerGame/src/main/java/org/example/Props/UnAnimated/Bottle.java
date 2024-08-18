package org.example.Props.UnAnimated;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.Bottles.*;
import static org.example.Utility.LoadContent.UAP_BOTTLES;

public class Bottle extends UnAnimatedProp{
    private static BufferedImage[] images = LoadImages();

    private static BufferedImage[] LoadImages() {
        BufferedImage[] imgs = new BufferedImage[2];
        BufferedImage img = LoadContent.GetSpriteAtlas(UAP_BOTTLES);
        for(int i = 0; i < imgs.length; i++) {
            imgs[i] = img.getSubimage(i*BOTTLE_WIDTH_DEFAULT, 0, BOTTLE_WIDTH_DEFAULT, BOTTLE_HEIGHT_DEFAULT);
        }
        return imgs;
    }

    public Bottle(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    @Override
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(images[objectType], x - xLvlOffset, y, BOTTLE_WIDTH, BOTTLE_HEIGHT, null);
    }
}