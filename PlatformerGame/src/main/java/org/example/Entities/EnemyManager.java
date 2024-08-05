package org.example.Entities;

import org.example.Constants.Sprites;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.example.Constants.Sprites.Enemy.Crabby.*;
import static org.example.Constants.Sprites.Enemy.Shark.*;

public class EnemyManager {

    private ArrayList<Enemy> enemies;

    public EnemyManager(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }



    public void update(){

    }

    public void draw(Graphics g, int xLvlOffset){
        for(Enemy e : enemies){
            e.draw(g,xLvlOffset);
        }
    }


}
