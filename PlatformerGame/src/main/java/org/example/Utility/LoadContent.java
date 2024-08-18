package org.example.Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoadContent {
    public static final String PLAYER_NO_SWORD_ATLAS = "animations/entities/player_no_sword_sprites.png";
    public static final String PLAYER_SWORD_ATLAS = "animations/entities/player_sword_sprites.png";
    public static final String CRABBY_ATLAS = "animations/entities/crabby_sprite.png";
    public static final String SHARK_ATLAS = "animations/entities/shark_atlas.png";
    public static final String STAR_ATLAS = "animations/entities/star_atlas.png";
    public static final String LEVEL_GROUND_TEXTURE = "level_texture.png";
    public static final String LEVEL_ANIMATED_WATER = "animations/props/water_animated_sprites.png";
    public static final String PLAYSCENE_DEEP_BACKGROUND = "playScene_deep_background.png";
    public static final String MENUSCENE_DEEP_BACKGROUND = "menuScene_deep_background.png";
    public static final String BIG_CLOUD_1 = "objects/big_cloud_1.png";
    public static final String BIG_CLOUD_2 = "objects/big_cloud_2.png";
    public static final String BIG_CLOUD_3 = "objects/big_cloud_3.png";
    public static final String SMALL_CLOUD = "objects/small_cloud.png";
    public static final String SWORD_ATLAS = "animations/props/sword_sprites.png";
    public static final String HEALTH_HUD = "hud/health_hud.png";
    public static final String CANDLE_ATLAS = "animations/props/candle_sprites.png";
    public static final String SEAGULL_ATLAS = "animations/props/seagull_sprites.png";
    public static final String SHIP_ATLAS = "animations/props/ship_sprite_floating.png";
    public static final String MENU_BACKGROUND = "ui/menu_background.png";
    public static final String URM_BUTTONS = "ui/urm_buttons.png";
    public static final String MENU_BUTTONS = "ui/menu_buttons.png";
    public static final String DEATH_SCREEN = "ui/death_screen.png";
    public static final String SETTINGS_BACKGROUND = "ui/settings_background.png";
    public static final String SOUND_BUTTONS = "ui/sound_buttons.png";
    public static final String VOLUME_BUTTONS = "ui/volume_buttons.png";
    public static final String PAUSE_BACKGROUND = "ui/pause_background.png";
    public static final String LVL_COMPLETE_SCREEN = "ui/lvl_completed_screen.png";
    public static final String SETTINGS_DEEP_BACKGROUND = "ui/settings_deep_background.png";
    public static final String CANNON_ATLAS = "animations/props/cannon_atlas.png";
    public static final String CANNON_BALL = "objects/cannon_ball.png";
    public static final String POTION_ATLAS = "animations/props/potion_sprites.png";
    public static final String UAP_BARRELS = "objects/uap_barrels.png";
    public static final String UAP_BOTTLES = "objects/uap_bottles.png";



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

    public static int GetNumberOfFilesInFolder(String folderPath){
        int fileCount = 0;
        try {
            URL url = LoadContent.class.getClassLoader().getResource(folderPath);

            if (url != null) {
                List<String> fileNames = listFiles(folderPath);
                fileCount = fileNames.size();
            } else {
                System.out.println("La cartella '" + folderPath + "' non esiste.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileCount;
    }


    private static List<String> listFiles(String folderPath) throws IOException {
        List<String> fileNames = new ArrayList<>();
        try (InputStream is = LoadContent.class.getResourceAsStream("/"+folderPath)) {
            if (is != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    String resource;
                    while ((resource = br.readLine()) != null) {
                        fileNames.add(resource);
                    }
                }
            }
        }
        return fileNames;
    }

}
