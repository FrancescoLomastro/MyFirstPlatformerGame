package org.example.Main;

import org.example.Entities.Player;
import org.example.Levels.LevelManager;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.example.Constants.FrameRate.*;
import static org.example.Constants.Window.SCALE;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;

    private Player player;
    private LevelManager levelManager;

    public Game() {
        initClasses();
        startLoop();
    }

    /**
     * Metodo usato per inizializzare i principali attributi della classe Game
     */
    private void initClasses() {
        this.levelManager = new LevelManager();
        this.player = new Player(
                levelManager.getPlayerX(),
                levelManager.getPlayerY(),
                (int) (64 * SCALE), (int) (40 * SCALE));
        this.player.addLevelData(levelManager.getBlockIndexes());

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
        player.update();
    }

    /**
     * Aggiornamento frame mostrato a schermo
     * @param g Parametro necessario per poter disegnre sul JPanel (non verrà descritto nelle altre classi)
     */
    public void draw(Graphics g) {
        player.draw(g,0);
        levelManager.draw(g,0);
    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }
}
