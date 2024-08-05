package org.example.Props;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.*;
import static org.example.Constants.Window.SCALE;
import static org.example.Utility.LoadContent.SWORD_IMAGE;

public class Sword extends Prop {
    private BufferedImage image;


    public Sword(int x, int y, int objectType) {
        super(x, y, objectType);
        this.image = LoadContent.GetSpriteAtlas(SWORD_IMAGE);
        initHitbox(SWORD_WIDTH_DEFAULT, SWORD_HEIGHT_DEFAULT);
        xDrawOffset = (int) (5 * SCALE);
        yDrawOffset = (int) (19 * SCALE);
        hitbox.y += yDrawOffset;
        hitbox.x += xDrawOffset;
        active = true;
    }

    @Override
    public void update(){
    }

    @Override
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(image, (int) (hitbox.x - xLvlOffset), (int) hitbox.y, SWORD_WIDTH, SWORD_HEIGHT, null);
        debug_drawHitbox(g, xLvlOffset);
    }
}
