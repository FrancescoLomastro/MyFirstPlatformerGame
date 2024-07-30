package org.example.Main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

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



        jframe.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowLostFocus(WindowEvent e) {
               // TODO Auto-generated method stub
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                // TODO Auto-generated method stub
            }
        });
    }
}
