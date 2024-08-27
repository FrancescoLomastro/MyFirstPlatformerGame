package org.example.Audio;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.util.Random;

public class AudioManager {
    private static AudioManager instance;

    private Music levelMusic;
    private int levelMusicIndex;
    private Music menuMusic;


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


    private String[] attackSources = {"audio/sounds/attack1.wav", "audio/sounds/attack2.wav", "audio/sounds/attack3.wav"};
    private Random rand;

    public AudioManager() {
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
    }

    public static AudioManager getInstance(){
        if(instance == null)
            instance = new AudioManager();
        return instance;
    }

    public void playPlayerAttack(){
        int sourceIndex = rand.nextInt(attackSources.length);
        attackSound = TinySound.loadSound(attackSources[sourceIndex]);
        attackSound.play();
    }

    public void playPlayerJump(){
        jumpSound.play();
    }

    public void playPlayerDead(){
        dieSound.play();
    }

    public void playGameOver(){
        stopAll();
        gameOverSound.play();
    }

    public void playCannonBang(){
        cannonBangSound.play();
    }

    public void playBottleOpen() {
        bottleOpenSound.play();
    }

    public void playPlayerPain() {
        playerPainSound.play();
    }

    public void playLevelCompleted() {
        stopAll();
        levelCompletedSound.play();
    }

    public void playSelectionSound() {
        selectionSound.play();
    }


    public void goToPlay() {
        stopAll();
        levelMusicIndex = 0;
        playLevelSong();
    }


    public void goToMenu() {
        stopAll();
        menuMusic.play(true);
    }

    public void goToRestartLevel() {
        stopAll();
        playLevelSong();
    }

    private void playLevelSong() {
        levelMusic = TinySound.loadMusic("audio/songs/level_"+ levelMusicIndex+".wav");
        levelMusic.play(true);
    }

    public void playGamePauseSound() {
        pauseSound.play();
    }

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

    public void goToNextLevel() {
        stopAll();
        levelMusicIndex++;
        levelMusicIndex = levelMusicIndex % 2;
        playLevelSong();
    }

    public void playSwordPickSound() {
        swordPickSound = TinySound.loadSound("audio/sounds/sword_pick.wav");
        swordPickSound.play();
    }
}
