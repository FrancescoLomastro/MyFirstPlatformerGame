package org.example.Entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class EnemyManager {

    private ArrayList<Enemy> enemies;

    public EnemyManager(ArrayList<Enemy> enemies) {
        this.enemies = enemies;



    }


    public void update(){
        for(Enemy e : enemies){
            e.update();
        }
    }

    public void draw(Graphics g, int xLvlOffset){
        for(Enemy e : enemies){
            if (e.isActive()) {
                e.draw(g, xLvlOffset);
            }
        }
    }


    public void checkEnemyAttacked(Rectangle2D.Float attackBox) {
        for(Enemy e : enemies){
            if(e.getCurrentHealth() > 0) {
                if (e.isActive()) {
                    if (attackBox.intersects(e.getHitbox())) {
                        e.alterHealth(-10);
                        return;
                    }
                }
            }
        }
    }

    public void reset() {
    }
}
