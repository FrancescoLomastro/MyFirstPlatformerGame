package org.example.Props.UnAnimated;

import org.example.Props.UnAnimated.UnAnimatedProp;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.Barrels.*;
import static org.example.Utility.LoadContent.SWORD_ATLAS;
import static org.example.Utility.LoadContent.UAP_BARRELS;

public class Barrel extends UnAnimatedProp{
    private static BufferedImage[] images = LoadImages();

    private static BufferedImage[] LoadImages() {
        BufferedImage[] imgs = new BufferedImage[4];
        BufferedImage img = LoadContent.GetSpriteAtlas(UAP_BARRELS);
        for(int i = 0; i < imgs.length; i++) {
            imgs[i] = img.getSubimage(i*BARREL_WIDTH_DEFAULT, 0, BARREL_WIDTH_DEFAULT, BARREL_HEIGHT_DEFAULT);
        }
        return imgs;
    }

    public Barrel(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    @Override
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(images[objectType], x - xLvlOffset, y, BARREL_WIDTH, BARREL_HEIGHT, null);
    }
}