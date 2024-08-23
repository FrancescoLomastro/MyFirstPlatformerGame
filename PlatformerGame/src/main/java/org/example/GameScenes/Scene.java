package org.example.GameScenes;

/**
 * Enum for the different scenes in the game
 */
public enum Scene {
    PLAY, MENU, QUIT, SETTINGS;

    /**
     * The current scene of the game, globally accessible from anywhere
     */
    public static Scene CurrentScene = MENU;
}