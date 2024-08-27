package org.example.UI;


import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.awt.*;
import java.awt.event.MouseEvent;

import static org.example.Constants.UI.VolumeButtons.*;
import static org.example.Constants.UI.SoundButtons.*;
import static org.example.Constants.Window.SCALE;
import static org.example.Utility.HelpMethods.IsMouseIn;


/**
 * Class that creates the audio options for the game.
 * A logic class that handles the audio options for the game.
 *
 * TODO still incomplete
 */
public class AudioOptions {
    private VolumeButton volumeButton;
    private SoundButton musicButton, sfxButton;


    public AudioOptions() {
        createSoundButtons();
        createVolumeButton();

    }

    private void createVolumeButton() {
        int vX = (int) (309 * SCALE);
        int vY = (int) (278 * SCALE);
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createSoundButtons() {
        int soundX = (int) (450 * SCALE);
        int musicY = (int) (140 * SCALE);
        int sfxY = (int) (186 * SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    public void update() {
        musicButton.update();
        sfxButton.update();
        volumeButton.update();
    }

    public void draw(Graphics g) {
        musicButton.draw(g);
        sfxButton.draw(g);

        volumeButton.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        if (volumeButton.isMousePressed()) {
            float valueBefore = volumeButton.getFloatValue();
            volumeButton.changeX(e.getX());
            float valueAfter = volumeButton.getFloatValue();
            if(valueBefore != valueAfter)
                System.out.println("Volume changed to " + valueAfter+ " by " + valueBefore);
                //game.getAudioPlayer().setVolume(valueAfter);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (IsMouseIn(e, musicButton))
            musicButton.setMousePressed(true);
        else if (IsMouseIn(e, sfxButton))
            sfxButton.setMousePressed(true);
        else if (IsMouseIn(e, volumeButton))
            volumeButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (IsMouseIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
                //game.getAudioPlayer().toggleSongMute();
            }

        } else if (IsMouseIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
                //game.getAudioPlayer().toggleEffectMute();
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();

        volumeButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(IsMouseIn(e, musicButton));
        sfxButton.setMouseOver(IsMouseIn(e, sfxButton));
        volumeButton.setMouseOver(IsMouseIn(e, volumeButton));
    }

}
