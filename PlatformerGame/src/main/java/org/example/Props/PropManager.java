package org.example.Props;

import org.example.Entities.Player;

import java.awt.*;
import java.util.ArrayList;

public class PropManager {

    private ArrayList<Prop> props;
    private Sword sword;

    private ArrayList<InactiveProp> inactiveProps;

    public PropManager(ArrayList<Prop> props, ArrayList<InactiveProp> inactiveProps) {
        this.props = props;
        this.inactiveProps = inactiveProps;
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
            if(p.isActive()){
                p.update();
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset){
        for(Prop p : props){
            if(p.isActive()) {
                p.draw(g, xLvlOffset);
            }
        }
        for (InactiveProp p : inactiveProps){
            p.draw(g, xLvlOffset);
        }
    }



    public boolean isSwordPicked(Player player) {
        if(sword != null && sword.isActive()){
            if(player.getHitbox().intersects(sword.getHitbox())){
                sword.setActive(false);
                return true;
            }
        }
        return false;
    }

    public void reset() {
        for(Prop p : props){
            p.reset();
        }
    }
}
