package org.example.GameScenes;

import org.example.Audio.AudioManager;

/**
 * Enum for the different scenes in the game
 */
public enum Scene {
    PLAY, MENU, QUIT, SETTINGS;

    /**
     * The current scene of the game, globally accessible from anywhere
     */
    private static Scene CurrentScene = initScene();

    private static Scene initScene() {
        AudioManager.getInstance().goToMenu();
        return MENU;
    }

    public static void changeScene(Scene scene){
        switch (scene){
            case PLAY -> {
                AudioManager.getInstance().goToPlay();
                CurrentScene = PLAY;
            }
            case MENU -> {
                AudioManager.getInstance().goToMenu();
                CurrentScene = MENU;
            }
            case QUIT -> {
                CurrentScene = QUIT;
            }
            case SETTINGS -> {
                CurrentScene = SETTINGS;
            }
        }

    }
    public static Scene getCurrentScene(){
        return CurrentScene;
    }
}