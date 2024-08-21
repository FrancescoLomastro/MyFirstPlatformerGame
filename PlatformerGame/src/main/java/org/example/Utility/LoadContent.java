package org.example.Utility;

import org.example.Exceptions.FolderNotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to load content from the resources folder.
 * It contains the paths to the resources and methods to load them.
 * Each resource has a static final string to be accessible from other classes.
 */
public class LoadContent {
    /* Animated Entities */
    public static final String PLAYER_ATLAS_NO_SWORD = "animations/entities/player_atlas_no_sword.png";
    public static final String PLAYER_ATLAS_SWORD = "animations/entities/player_atlas_sword.png";
    public static final String CRABBY_ATLAS = "animations/entities/crabby_sprite.png";
    public static final String SHARK_ATLAS = "animations/entities/shark_atlas.png";
    public static final String STAR_ATLAS = "animations/entities/star_atlas.png";

    /* Animated Props */
    public static final String WATER_LIGHT_1_SPRITE = "animations/props/water_light_1_sprite.png";
    public static final String WATER_LIGHT_2_SPRITE = "animations/props/water_light_2_sprite.png";
    public static final String BACK_PALM_1_SPRITE = "animations/props/back_palm_1_sprite.png";
    public static final String BACK_PALM_2_SPRITE = "animations/props/back_palm_2_sprite.png";
    public static final String BACK_PALM_3_SPRITE = "animations/props/back_palm_3_sprite.png";
    public static final String WATER_SPRITE = "animations/props/water_animated_sprite.png";
    public static final String CANDLE_SPRITE = "animations/props/candle_sprite.png";
    public static final String SEAGULL_SPRITE = "animations/props/seagull_sprite.png";
    public static final String SHIP_SPRITE = "animations/props/floating_ship_sprite.png";
    public static final String SWORD_SPRITE = "animations/props/sword_sprite.png";
    public static final String CANNON_SPRITE = "animations/props/cannon_sprite.png";
    public static final String POTION_SPRITE = "animations/props/potion_sprite.png";

    /* HUD */
    public static final String HEALTH_HUD = "hud/health_hud.png";

    /* Non-Animated Prop */
    public static final String BIG_CLOUD_1 = "objects/big_cloud_1.png";
    public static final String BIG_CLOUD_2 = "objects/big_cloud_2.png";
    public static final String BIG_CLOUD_3 = "objects/big_cloud_3.png";
    public static final String SMALL_CLOUD = "objects/small_cloud.png";
    public static final String CANNON_BALL = "objects/cannon_ball.png";
    public static final String BARRELS = "objects/uap_barrels.png";
    public static final String BOTTLES = "objects/uap_bottles.png";
    public static final String DOOR = "objects/door.png";

    /* User Interface */
    public static final String MENU_BOARD = "ui/menu_board.png";
    public static final String SETTINGS_BOARD = "ui/settings_board.png";
    public static final String PAUSE_BOARD = "ui/pause_board.png";
    public static final String DEATH_BOARD = "ui/death_board.png";
    public static final String LEVEL_COMPLETE_BOARD = "ui/level_completed_board.png";
    public static final String URM_BUTTONS = "ui/urm_buttons.png";
    public static final String MENU_BUTTONS = "ui/menu_buttons.png";
    public static final String SOUND_BUTTONS = "ui/sound_buttons.png";
    public static final String VOLUME_BUTTONS = "ui/volume_buttons.png";

    /* Textures */
    public static final String TILES_TEXTURES = "textures/tiles_textures.png";
    public static final String PLAYSCENE_DEEP_BACKGROUND = "textures/playScene_deep_background.png";
    public static final String MENUSCENE_DEEP_BACKGROUND = "textures/menuScene_deep_background.png";
    public static final String SETTINGS_DEEP_BACKGROUND = "ui/settings_deep_background.png";


    /**
     * This method loads a sprite atlas from the resources folder.
     * @param fileName The name of the file to load.
     * @return the resource as BufferedImage
     */
    public static BufferedImage GetResourceAsBufferedImage(String fileName) {
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


    /**
     * This methods retrieves the number of files in a folder.
     * It is used to get the number of levels.
     * @param folderPath The path to the folder
     * @return the number of files in the folder
     */
    public static int GetNumberOfFilesInFolder(String folderPath){
        int fileCount = 0;
        try {
            URL url = LoadContent.class.getClassLoader().getResource(folderPath);

            if (url != null) {
                List<String> fileNames = listFiles(folderPath);
                fileCount = fileNames.size();
            } else {
                throw new FolderNotFoundException("The folder " + folderPath + " do not exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileCount;
    }

    /**
     * This method lists the files in a folder.
     * @param folderPath The path to the folder
     * @return a list of file names
     * @throws IOException
     */
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
