package org.example.Props;

import org.example.Entities.Enemy;
import org.example.Entities.Player;

import java.awt.*;
import java.util.ArrayList;

public class PropManager {

    private ArrayList<Prop> props;
    private Sword sword;

    public PropManager(ArrayList<Prop> props) {
        this.props = props;
        sword = findSword();
    }

    private Sword findSword() {
        for (Prop prop : props) {
            if (prop instanceof Sword) {
                return (Sword) prop;
            }
        }
        return null;
    }


    public void update(){
        for(Prop p : props){
            p.update();
        }
    }

    public void draw(Graphics g, int xLvlOffset){
        for(Prop p : props){
            p.draw(g,xLvlOffset);
        }
    }



    public boolean isSwordPicked(Player player) {
        if(sword != null && sword.isActive()){
            if(player.getHitbox().intersects(sword.getHitbox())){
                props.remove(sword);
                return true;
            }
        }
        return false;
    }
}
