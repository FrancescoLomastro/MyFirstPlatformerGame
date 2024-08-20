package org.example.Main;

import org.example.GameScenes.Scene;
import org.example.GameScenes.MenuScene;
import org.example.GameScenes.PlayScene;
import org.example.GameScenes.SettingsScene;
import org.example.UI.AudioOptions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.example.Constants.FrameRate.*;

/**
 * This class is used to create the game.
 * It creates the game and starts the game loop.
 * It also manages the scenes, and the audio options.
 */
public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;

    /* Scenes */
    private PlayScene playScene;
    private MenuScene menuScene;
    private SettingsScene settingsScene;

    /* Audio */
    private AudioOptions audioOptions;


    public Game() {
        initClasses();
        startLoop();
    }

    /**
     * This method is used to initialize the classes used by the game.
     */
    private void initClasses() {

        this.audioOptions = new AudioOptions();
        PlayScene.createInstance(this);

        this.playScene = PlayScene.getInstance();
        this.menuScene = new MenuScene(this);
        this.settingsScene = new SettingsScene(this);

        this.gamePanel = new GamePanel(this);
        this.gameWindow = new GameWindow(gamePanel);

        /* Set the focus on the game panel */
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
    }

    /**
     * This method is used to start the game loop using a new thread.
     */
    private void startLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }




    /**
     * This method is the Loop of the game, it is designed to update the current frame {@code FPS_SET} times ongi
     * second through the draw() method, and update the game logic {@code UPS_SET} times every second through
     * the update() method.
     *
     * Actually the loop does not call the draw() method directly but needs to go through the repaint() method of the
     * JPanel, this method is already implemented by the JPanel and allows updating the graphical content inside it.
     * Through an override of the paintComponent() method of gamePanel it is possible to call the draw() method of this
     * class
     */
    @Override
    public void run() {
        double oneSecondInNanoSeconds =  1000000000.0;
        double timePerFrame = oneSecondInNanoSeconds / FPS_SET;
        double timePerUpdate = oneSecondInNanoSeconds / UPS_SET;

        long previousTime = System.nanoTime();
        long currentTime;
        double deltaU = 0;
        double deltaF = 0;

        long debug_FPS_previousTime = System.currentTimeMillis();
        int debug_FPS_frames_num = 0;
        int debug_FPS_updates_num = 0;

        while (true) {
            currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                debug_FPS_updates_num++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                debug_FPS_frames_num++;
                deltaF--;
            }

            
            if (System.currentTimeMillis() - debug_FPS_previousTime >= 1000) {
                debug_FPS_previousTime = System.currentTimeMillis();
                debug_FPS_UPS_printer(debug_FPS_frames_num,debug_FPS_updates_num);
                debug_FPS_frames_num = 0;
                debug_FPS_updates_num = 0;
            }
        }
    }

    /**
     * This method is used to print the current FPS and UPS on the console.
     * @param debug_FPS_frames_num the number of frames in the last second.
     * @param debug_FPS_updates_num the number of updates in the last second.
     */
    private void debug_FPS_UPS_printer(int debug_FPS_frames_num, int debug_FPS_updates_num) {
        System.out.println("FPS: " + debug_FPS_frames_num + " | UPS: " + debug_FPS_updates_num);
    }


    /**
     * Update the game logic, movement, animations...  without drawing anything.
     * It calls the update() method of the current scene.
     */
    public void update() {
        switch (Scene.CurrentScene){
            case PLAY -> {
                playScene.update();
            }
            case MENU -> {
                menuScene.update();
            }
            case QUIT -> {

            }
            case SETTINGS -> {
                settingsScene.update();
            }
        }
    }

    /**
     * Frame update shown on screen.
     * It calls the draw() method of the current scene.
     * @param g Parameter needed to be able to draw on the JPanel (will not be described in the other classes).
     */
    public void draw(Graphics g) {
        switch (Scene.CurrentScene){
            case PLAY -> {
                playScene.draw(g);
            }
            case MENU -> {
                menuScene.draw(g);
            }
            case QUIT -> {

            }
            case SETTINGS -> {
                settingsScene.draw(g);
            }
        }
    }

    /**
     * This method is used to handle the key pressed event.
     * It calls the keyPressed() method of the current scene.
     * @param e the key event.
     */
    public void keyPressed(KeyEvent e) {
        switch (Scene.CurrentScene){
            case PLAY -> {
                playScene.keyPressed(e);
            }
            case MENU -> {
                menuScene.keyPressed(e);
            }
            case QUIT -> {

            }
            case SETTINGS -> {
                settingsScene.keyPressed(e);
            }
        }
    }
    
    
    /**
     * This method is used to handle the key released event.
     * It calls the keyReleased() method of the current scene.
     * @param e the key event.
     */
    public void keyReleased(KeyEvent e) {
        switch (Scene.CurrentScene){
            case PLAY -> {
                playScene.keyReleased(e);
            }
            case MENU -> {
                menuScene.keyReleased(e);
            }
            case QUIT -> {

            }
            case SETTINGS -> {
                settingsScene.keyReleased(e);
            }
        }
    }

    /**
     * This method is used to handle the mouse clicked event.
     * It calls the mouseClicked() method of the current scene.
     * @param e the mouse event.
     */
    public void mouseClicked(MouseEvent e) {
        switch (Scene.CurrentScene){
            case PLAY -> {
                playScene.mouseClicked(e);
            }
            case MENU -> {
                menuScene.mouseClicked(e);
            }
            case QUIT -> {

            }
            case SETTINGS -> {
                settingsScene.mouseClicked(e);
            }
        }
    }

    /**
     * This method is used to handle the mouse moved event.
     * It calls the mouseMoved() method of the current scene.
     * @param e the mouse event.
     */
    public void mouseMoved(MouseEvent e) {
        switch (Scene.CurrentScene){
            case PLAY -> {
                playScene.mouseMoved(e);
            }
            case MENU -> {
                menuScene.mouseMoved(e);
            }
            case QUIT -> {

            }
            case SETTINGS -> {
                settingsScene.mouseMoved(e);
            }
        }
    }

    /**
     * This method is used to handle the mouse released event.
     * It calls the mouseReleased() method of the current scene.
     * @param e the mouse event.
     */
    public void mouseReleased(MouseEvent e) {
        switch (Scene.CurrentScene){
            case PLAY -> {
                playScene.mouseReleased(e);
            }
            case MENU -> {
                menuScene.mouseReleased(e);
            }
            case QUIT -> {

            }
            case SETTINGS -> {
                settingsScene.mouseReleased(e);
            }
        }
    }

    /**
     * This method is used to handle the mouse pressed event.
     * It calls the mousePressed() method of the current scene.
     * @param e the mouse event.
     */
    public void mousePressed(MouseEvent e) {
        switch (Scene.CurrentScene){
            case PLAY -> {
                playScene.mousePressed(e);
            }
            case MENU -> {
                menuScene.mousePressed(e);
            }
            case QUIT -> {

            }
            case SETTINGS -> {
                settingsScene.mousePressed(e);
            }
        }
    }

    /**
     * This method is used to handle the mouse dragged event.
     * It calls the mouseDragged() method of the current scene.
     * @param e the mouse event.
     */
    public void mouseDragged(MouseEvent e) {
        switch (Scene.CurrentScene){
            case PLAY -> {
            }
            case SETTINGS -> {
                settingsScene.mouseDragged(e);
            }
        }
    }

    /** This method is a getter for the audio options.
     * @return
     */
    public AudioOptions getAudioOptions() {
        return audioOptions;
    }

    /**
     * This method is used to handle the focus lost event.
     * It calls the resetPlayerDirBooleans() method of the PlayScene.
     */
    public void windowFocusLost() {
        if(Scene.CurrentScene == Scene.PLAY) {
            PlayScene.getInstance().resetPlayerDirBooleans();
        }

    }
}
