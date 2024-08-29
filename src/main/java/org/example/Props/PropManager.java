package org.example.Props;

import org.example.Exceptions.SwordNotFoundException;
import org.example.Props.Animated.AnimatedProp;
import org.example.Props.Animated.Sword;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * This class manages all the props in the game.
 */
public class PropManager {

    private final ArrayList<AnimatedProp> animatedProps;
    private final Sword sword;
    private final ArrayList<Prop> notAnimatedProps;

    public PropManager(ArrayList<AnimatedProp> animatedProps, ArrayList<Prop> notAnimatedProps) {
        this.animatedProps = animatedProps;
        this.notAnimatedProps = notAnimatedProps;
        sword = findSword();
    }

    /**
     * This method finds the sword in the list of animated props.
     * @return The sword prop.
     */
    private Sword findSword() {
        for (AnimatedProp animatedProp : animatedProps) {
            if (animatedProp instanceof Sword) {
                return (Sword) animatedProp;
            }
        }
        throw new SwordNotFoundException("Sword not found, check that the level has a sword prop");
    }


    /**
     * This method updates all the props in the game.
     */
    public void update(){
        for(AnimatedProp p : animatedProps){
            if(p.isActive()){
                p.update();
            }
        }
    }

    /**
     * This method draws all the props in the game.
     * @param g
     * @param xLvlOffset
     */
    public void draw(Graphics g, int xLvlOffset){
        for(AnimatedProp p : animatedProps){
            if(p.isActive()) {
                p.draw(g, xLvlOffset);
            }
        }
        for (Prop p : notAnimatedProps){
            p.draw(g, xLvlOffset);
        }
    }

    /**
     * This method checks if the player has picked up the sword.
     * @param hitbox The player hitbox.
     * @return True if the player has picked up the sword, false otherwise.
     */
    public boolean isSwordPicked(Rectangle2D.Float hitbox) {
        if(sword != null && sword.isActive()){
            if(hitbox.intersects(sword.getHitbox())){
                sword.setActive(false);
                return true;
            }
        }
        return false;
    }

    /**
     * This method resets all the props in the game.
     */
    public void reset() {
        for(AnimatedProp p : animatedProps){
            p.reset();
        }
    }
}
