package org.example.Audio;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.util.Random;

import static org.example.Constants.UI.Audio.*;

/**
 * Class that handles the audio for the game.
 * It uses the external library TinySound to play the music and sounds.
 * It has methods to play the different sounds and music for the game.
 * It also has methods to stop the music and sounds, and set their volume.
 */
public class AudioManager {
    private static AudioManager instance;

    // Music
    private Music levelMusic;
    private int levelMusicIndex;
    private Music menuMusic;

    // Sounds
    private Sound dieSound;
    private Sound jumpSound;
    private Sound attackSound;
    private Sound gameOverSound;
    private Sound cannonBangSound;
    private Sound bottleOpenSound;
    private Sound playerPainSound;
    private Sound levelCompletedSound;
    private Sound selectionSound;
    private Sound pauseSound;
    private Sound swordPickSound;

    // Attack sources to randomize the attack sound
    private String[] attackSources = {"audio/sounds/attack1.wav", "audio/sounds/attack2.wav", "audio/sounds/attack3.wav"};
    private Random rand;


    private double volume;
    private double realSFXVolume;

    private AudioManager() {
        TinySound.init();
        this.rand = new Random();

        playerPainSound = TinySound.loadSound("audio/sounds/pain.wav");
        jumpSound = TinySound.loadSound("audio/sounds/jump.wav");
        dieSound = TinySound.loadSound("audio/sounds/die.wav");
        gameOverSound = TinySound.loadSound("audio/sounds/gameover.wav");
        levelCompletedSound = TinySound.loadSound("audio/sounds/lvl_completed.wav");
        cannonBangSound = TinySound.loadSound("audio/sounds/bang.wav");
        bottleOpenSound = TinySound.loadSound("audio/sounds/bottle_open.wav");
        playerPainSound = TinySound.loadSound("audio/sounds/pain.wav");
        selectionSound = TinySound.loadSound("audio/sounds/selection.wav");
        pauseSound = TinySound.loadSound("audio/sounds/pause.wav");
        swordPickSound = TinySound.loadSound("audio/sounds/sword_pick.wav");


        menuMusic = TinySound.loadMusic("audio/songs/menu.wav");
        setVolume(GLOBAL_VOLUME);
        setSFXMuted(SFX_MUTED);
        setSoundMuted(SOUND_MUTED);
    }

    /**
     * Singleton pattern
     * @return the instance of the AudioManager
     */
    public static AudioManager getInstance(){
        if(instance == null)
            instance = new AudioManager();
        return instance;
    }

    /**
     * Plays the attack sound of the player, randomizing the source
     */
    public void playPlayerAttack(){
        int sourceIndex = rand.nextInt(attackSources.length);
        attackSound = TinySound.loadSound(attackSources[sourceIndex]);
        attackSound.play(realSFXVolume);
    }

    /**
     * Plays the jump sound of the player
     */
    public void playPlayerJump(){
        jumpSound.play(realSFXVolume);
    }

    /**
     * Plays the death sound of the player
     */
    public void playPlayerDead(){
        dieSound.play(realSFXVolume);
    }

    /**
     * Plays the game over sound
     */
    public void playGameOver(){
        stopAll();
        gameOverSound.play(realSFXVolume);
    }

    /**
     * Plays the cannon bang sound
     */
    public void playCannonBang(){
        cannonBangSound.play(realSFXVolume);
    }

    /**
     * Plays the bottle open sound
     */
    public void playBottleOpen() {
        bottleOpenSound.play(realSFXVolume);
    }

    /**
     * Plays the player pain sound
     */
    public void playPlayerPain() {
        playerPainSound.play(realSFXVolume);
    }

    /**
     * Plays the level completed sound
     */
    public void playLevelCompleted() {
        stopAll();
        levelCompletedSound.play(realSFXVolume);
    }

    /**
     * Plays the selection sound for buttons
     */
    public void playSelectionSound() {
        selectionSound.play(realSFXVolume);
    }

    /**
     * Plays the pause sound of the game
     */
    public void playGamePauseSound() {
        pauseSound.play(realSFXVolume);
    }


    /**
     * Sets the manager handle the play scene
     */
    public void goToPlay() {
        stopAll();
        levelMusicIndex = 0;
        playLevelSong();
    }

    /**
     * Sets the manager handle the menu scene
     */
    public void goToMenu() {
        stopAll();
        menuMusic.play(true);
    }

    /**
     * Sets the manager handle the restart of the level
     */
    public void goToRestartLevel() {
        stopAll();
        playLevelSong();
    }

    /**
     * Plays the music of the level
     */
    private void playLevelSong() {
        levelMusic = TinySound.loadMusic("audio/songs/level_"+ levelMusicIndex+".wav");
        levelMusic.play(true);
    }

    /**
     * Plays the sound of the sword pick
     */
    public void playSwordPickSound() {
        swordPickSound = TinySound.loadSound("audio/sounds/sword_pick.wav");
        swordPickSound.play(realSFXVolume);
    }


    /**
     * Stops all the sounds and music
     */
    public void stopAll() {
        dieSound.stop();
        jumpSound.stop();
        gameOverSound.stop();
        cannonBangSound.stop();
        bottleOpenSound.stop();
        playerPainSound.stop();
        levelCompletedSound.stop();
        menuMusic.stop();
        selectionSound.stop();
        pauseSound.stop();
        if(levelMusic != null)
            levelMusic.stop();
        if(attackSound != null)
            attackSound.stop();
    }

    /**
     * Sets the manager handle the next level
     */
    public void goToNextLevel() {
        stopAll();
        levelMusicIndex++;
        levelMusicIndex = levelMusicIndex % 2;
        playLevelSong();
    }


    /**
     * Sets the sound muted or not
     * @param soundMuted true if the sound is muted, false otherwise
     */
    public void setSoundMuted(boolean soundMuted) {
        if (soundMuted){
            if(menuMusic != null)
                menuMusic.setVolume(0);
            if(levelMusic != null)
                levelMusic.setVolume(0);
        } else {
            if(menuMusic != null)
                menuMusic.setVolume(volume);
            if(levelMusic != null)
                levelMusic.setVolume(volume);
        }
    }

    /**
     * Sets the SFX muted or not
     * @param sfxMuted true if the SFX is muted, false otherwise
     */
    public void setSFXMuted(boolean sfxMuted) {
        if (sfxMuted){
            realSFXVolume = 0;
        } else {
            realSFXVolume = volume;
        }
    }

    /**
     * Sets the global volume of the audio
     * @param volume from 0 to 1
     */
    public void setVolume(double volume) {
        this.volume = volume;
        TinySound.setGlobalVolume(volume);
    }



}
