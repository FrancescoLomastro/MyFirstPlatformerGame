package org.example.Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadContent {
    public static final String PLAYER_NO_SWORD_ATLAS = "animations/player_no_sword_sprites.png";
    public static final String LEVEL_GROUND_TEXTURE = "level_texture.png";
    public static final String LEVEL_ANIMATED_WATER = "animations/water_animated_sprites.png";
    public static final String PLAYSCENE_DEEP_BACKGROUND = "playScene_deep_background.png";
    public static final String BIG_CLOUD = "big_cloud.png";
    public static final String SMALL_CLOUD = "small_cloud.png";

    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadContent.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }


}
