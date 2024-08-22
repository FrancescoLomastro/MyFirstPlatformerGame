package org.example.Utility;

import org.example.UI.Button;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import static org.example.Constants.Window.TILES_SIZE;

/**
 * This is a support class that contains some useful static methods that are used in the game
 */
public class HelpMethods {

    /**
     * This method is used when the position of the hit box altered by verticalSpeed is not traversable.
     * This can be caused because it is colliding with the ceiling or the floor.
     * The method returns the new Y position of the hit box that, depending on the sign of verticalSpeed (Jump, Fall), puts
     * the entity on the ground or below the ceiling
     * @param hitbox The hit box of the entity
     * @param verticalSpeed The vertical speed of the entity
     * @return
     */
    public static float YPositionUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float verticalSpeed) {
        int currentTileY = (int) (hitbox.y / TILES_SIZE);

        if (verticalSpeed > 0) {
            // Falling to the floor
            // The new position will be a Y coordinate of the current tile but shifted by an offset that ensures that the
            // character's feet touch the ground
            int yPosition = currentTileY * TILES_SIZE;
            int yOffset = (int) (TILES_SIZE - hitbox.height);
            return yPosition + yOffset - 1;
        } else {
            // Jumping and collision with ceiling
            // The new position will be a Y coordinate of the current tile, which ensures that the character's head touches the ceiling
            return currentTileY * TILES_SIZE;
        }
    }

    /**
     * This method is used when the position of the hit box altered by horizontalSpeed is not traversable.
     * This can be caused because it is colliding with a wall.
     * @param hitbox The hit box of the entity
     * @param horizontalSpeed The horizontal speed of the entity
     * @return The new X position of the hit box that, depending on the sign of horizontalSpeed (Right, Left), puts the entity
     */
    public static float XPositionNextToWall(Rectangle2D.Float hitbox, float horizontalSpeed) {
        int currentTile = (int) (hitbox.x / TILES_SIZE);
        if (horizontalSpeed > 0) {
            // Right movement
            int tileXPos = currentTile * TILES_SIZE;
            int xOffset = (int) (TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else
            // Left movement
            return currentTile * TILES_SIZE;
    }

    /**
     * This method checks if the mouse is inside a button
     * @param e The mouse event
     * @param mb The button
     * @return True if the mouse is inside the button, false otherwise
     */
    public static boolean IsMouseIn(MouseEvent e, Button mb){
        return mb.getBounds().contains(e.getX(), e.getY());
    }
}
