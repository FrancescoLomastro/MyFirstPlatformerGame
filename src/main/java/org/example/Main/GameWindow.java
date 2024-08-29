package org.example.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class used to create the game window.
 * It uses a JFrame to create the window.
 */
public class GameWindow {
    private JFrame jframe;

    public GameWindow(GamePanel gamePanel) {
        this.jframe = new JFrame();

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setVisible(true);
        jframe.setLocationRelativeTo(null);

        InputStream iconStream = getClass().getResourceAsStream("/icon.png");
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(iconStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        jframe.setIconImage(icon);

        jframe.setTitle("MyFirstPlatformGame");


        /**
         * This listener has been used to manage a situation when the player is moving the character using the keyboard
         * and then clicks outside the game window. Without this listener the character will keep moving because the game
         * will never receive the release event of the keyboard.
         */
        jframe.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.windowFocusLost();
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                // TODO Auto-generated method stub
            }
        });
    }
}
