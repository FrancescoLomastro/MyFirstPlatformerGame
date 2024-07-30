package org.example.Main;

import java.awt.*;

import static org.example.Constants.FrameRate.*;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;

    public Game() {
        initClasses();
        startLoop();
    }

    /**
     * Metodo usato per inizializzare i principali attributi della classe Game
     */
    private void initClasses() {
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

        long FPS_debug_previousTime = System.currentTimeMillis();
        int FPS_debug_frames_num = 0;
        int FPS_debug_updates_num = 0;

        while (true) {
            currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                FPS_debug_updates_num++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                FPS_debug_frames_num++;
                deltaF--;
            }

            if (System.currentTimeMillis() - FPS_debug_previousTime >= 1000) {
                FPS_debug_previousTime = System.currentTimeMillis();
                System.out.println("FPS: " + FPS_debug_frames_num + " | UPS: " + FPS_debug_updates_num);
                FPS_debug_frames_num = 0;
                FPS_debug_updates_num = 0;
            }
        }
    }


    /**
     * Aggiornamento logica di gioco
     */
    public void update() {
    }

    /**
     * Aggiornamento frame mostrato a schermo
     * @param g Parametro necessario per poter disegnre sul JPanel (non verrà descritto nelle altre classi)
     */
    public void draw(Graphics g) {
    }

}
