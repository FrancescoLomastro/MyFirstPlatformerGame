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

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;



    private PlayScene playScene;
    private MenuScene menuScene;
    private SettingsScene settingsScene;

    private AudioOptions audioOptions;

    public Game() {
        initClasses();
        startLoop();
    }

    /**
     * Metodo usato per inizializzare i principali attributi della classe Game
     */
    private void initClasses() {
        PlayScene.createInstance(this);
        this.audioOptions = new AudioOptions();

        this.playScene = PlayScene.getInstance();
        this.menuScene = new MenuScene(this);
        this.settingsScene = new SettingsScene(this);

        this.gamePanel = new GamePanel(this);
        this.gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

    }

    /**
     * Metodo usato per lanciare un thread secondorio rispetto Main, che si occupi del Loop di gioco
     */
    private void startLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }




    /**
     * Questo metodo è il Loop del gioco, è progettato per aggiornare il frame corrente {@code FPS_SET} volte ongi
     * secondo attraverso il metodo draw(), e aggiorna la logica di gioco {@code UPS_SET} volte ogni secondo attraverso
     * il metodo update()
     *
     * In realtà il loop non chiama direttamente il metodo draw() ma ha bisogno di passare per il metodo repaint() del
     * JPanel, questo metodo è gia implementato dal JPanel e consente di aggiornare il contenuto grafico al suo interno.
     * Attraverso un override del metodo paintComponent() di gamePanel è possibile richiamare il metodo draw() di questa
     * classe
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
                System.out.println("FPS: " + debug_FPS_frames_num + " | UPS: " + debug_FPS_updates_num);
                debug_FPS_frames_num = 0;
                debug_FPS_updates_num = 0;
            }
        }
    }


    /**
     * Aggiornamento logica di gioco
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
     * Aggiornamento frame mostrato a schermo
     * @param g Parametro necessario per poter disegnre sul JPanel (non verrà descritto nelle altre classi)
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



    public void mouseDragged(MouseEvent e) {
        switch (Scene.CurrentScene){
            case PLAY -> {
                //playScene.mouseDragged(e);
            }
            case SETTINGS -> {
                settingsScene.mouseDragged(e);
            }
        }
    }

    public AudioOptions getAudioOptions() {
        return audioOptions;
    }
}
