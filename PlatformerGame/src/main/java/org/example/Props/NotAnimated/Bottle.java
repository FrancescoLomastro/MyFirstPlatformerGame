package org.example.Props.NotAnimated;

import org.example.Props.Prop;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.Bottles.*;
import static org.example.Utility.LoadContent.BOTTLES;

/**
 * Bottle class
 */
public class Bottle extends Prop {
    private static BufferedImage[] images = LoadImages();

    private static BufferedImage[] LoadImages() {
        BufferedImage[] imgs = new BufferedImage[2];
        BufferedImage img = LoadContent.GetResourceAsBufferedImage(BOTTLES);
        for(int i = 0; i < imgs.length; i++) {
            imgs[i] = img.getSubimage(i*BOTTLE_WIDTH_DEFAULT, 0, BOTTLE_WIDTH_DEFAULT, BOTTLE_HEIGHT_DEFAULT);
        }
        return imgs;
    }

    public Bottle(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    /**
     * Draw the bottle basing on its object type
     */
    @Override
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(images[objectType], x - xLvlOffset, y, BOTTLE_WIDTH, BOTTLE_HEIGHT, null);
    }
}