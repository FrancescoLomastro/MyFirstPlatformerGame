package org.example.Audio;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.util.Random;

public class AudioManager {
    private static AudioManager instance;
    private Music music;
    private Sound sound;


    private String[] attackSounds = {"audio/attack1.wav", "audio/attack2.wav", "audio/attack3.wav"};
    private String[] walkSounds = {"audio/walk1.wav", "audio/walk2.wav", "audio/walk3.wav", "audio/walk4.wav"};
    private CountingThread walkThread;
    private int walkIndex;
    private Random rand;

    public AudioManager() {
        TinySound.init();
        this.rand = new Random();
        this.walkIndex = 0;
    }

    public static AudioManager getInstance(){
        if(instance == null)
            instance = new AudioManager();
        return instance;
    }

    public void playPlayerAttack(){
        int sourceIndex = rand.nextInt(attackSounds.length);
        sound = TinySound.loadSound(attackSounds[sourceIndex]);
        sound.play();
    }

    public void playPlayerJump(){
        sound = TinySound.loadSound("audio/jump.wav");
        sound.play();
    }

    public void playPlayerDead(){
        sound = TinySound.loadSound("audio/die.wav");
        sound.play();
    }

    public void playGameOver(){
        sound = TinySound.loadSound("audio/gameover.wav");
        sound.play();
    }
    
}
