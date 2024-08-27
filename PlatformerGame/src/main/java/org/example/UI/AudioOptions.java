package org.example.UI;


import org.example.Audio.AudioManager;

import java.awt.*;
import java.awt.event.MouseEvent;

import static org.example.Constants.UI.Audio.*;
import static org.example.Constants.UI.VolumeButtons.*;
import static org.example.Constants.UI.SoundButtons.*;
import static org.example.Constants.Window.SCALE;
import static org.example.Utility.HelpMethods.IsMouseIn;


/**
 * Class that creates the audio options for the game.
 * A logic class that handles the audio options for the game.
 * It creates the sound buttons and the volume button.
 * And is used to share the settings from MENU to PLAY scenes
 */
public class AudioOptions {
    private VolumeButton volumeButton;
    private SoundButton musicButton, sfxButton;


    public AudioOptions() {
        createSoundButtons();
        createVolumeButton();
        musicButton.setMuted(SOUND_MUTED);
        sfxButton.setMuted(SFX_MUTED);
        volumeButton.setVolume(GLOBAL_VOLUME);
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
                AudioManager.getInstance().setVolume(valueAfter);
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
                AudioManager.getInstance().setSoundMuted(musicButton.isMuted());
            }

        } else if (IsMouseIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
                AudioManager.getInstance().setSFXMuted(sfxButton.isMuted());
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();
        volumeButton.resetBooleans();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(IsMouseIn(e, musicButton));
        sfxButton.setMouseOver(IsMouseIn(e, sfxButton));
        volumeButton.setMouseOver(IsMouseIn(e, volumeButton));
    }

}
