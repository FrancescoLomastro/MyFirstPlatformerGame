package org.example.Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadContent {
    public static final String PLAYER_NO_SWORD_ATLAS = "animations/Entities/player_no_sword_sprites.png";
    public static final String PLAYER_SWORD_ATLAS = "animations/Entities/player_sword_sprites.png";
    public static final String CRABBY_ATLAS = "animations/Entities/crabby_sprite.png";
    public static final String SHARK_ATLAS = "animations/Entities/shark_atlas.png";
    public static final String STAR_ATLAS = "animations/Entities/star_atlas.png";
    public static final String LEVEL_GROUND_TEXTURE = "level_texture.png";
    public static final String LEVEL_ANIMATED_WATER = "animations/Objects/water_animated_sprites.png";
    public static final String PLAYSCENE_DEEP_BACKGROUND = "playScene_deep_background.png";
    public static final String MENUSCENE_DEEP_BACKGROUND = "menuScene_deep_background.png";
    public static final String BIG_CLOUD_1 = "objects/big_cloud_1.png";
    public static final String BIG_CLOUD_2 = "objects/big_cloud_2.png";
    public static final String BIG_CLOUD_3 = "objects/big_cloud_3.png";
    public static final String SMALL_CLOUD = "objects/small_cloud.png";
    public static final String SWORD_IMAGE = "objects/sword.png";
    public static final String HEALTH_HUD = "hud/health_hud.png";
    public static final String SEAGULL_ATLAS = "animations/Objects/seagull_sprites.png";
    public static final String SHIP_ATLAS = "animations/Objects/ship_sprite_floating.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String URM_BUTTONS = "ui/urm_buttons.png";
    public static final String MENU_BUTTONS = "ui/menu_buttons.png";


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
