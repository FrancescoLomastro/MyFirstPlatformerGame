package org.example.Props.UnAnimated;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.Door.DOOR_HEIGHT;
import static org.example.Constants.Prop.Door.DOOR_WIDTH;
import static org.example.Utility.LoadContent.DOOR;


public class Door extends UnAnimatedProp {
    private static BufferedImage image = LoadImage();

    private static BufferedImage LoadImage() {
        BufferedImage img = LoadContent.GetSpriteAtlas(DOOR);
        return img;
    }

    public Door(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    @Override
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(image, x - xLvlOffset, y - DOOR_HEIGHT/2 , DOOR_WIDTH, DOOR_HEIGHT, null);
    }
}
