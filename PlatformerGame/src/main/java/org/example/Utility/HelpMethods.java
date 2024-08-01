package org.example.Utility;

import java.awt.geom.Rectangle2D;

import static org.example.Constants.Window.TILES_SIZE;

public class HelpMethods {

    public static float YPositionUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float movementSpeed) {
        //hitbox.y è sempre una posizione accessibile, ma se vi aggiungessimo movementSpeed andremo
        //a collidere col soffitto oppure col terreno
        int currentTileY = (int) (hitbox.y / TILES_SIZE);
        if (movementSpeed > 0) {
            // Caduta sul pavimento
            // La nuova posizione sarà la coordinata Y della casella corrente ma sfasata di un offset che ci assicura che
            // i piedi del personaggio tocchino terra
            int yPosition = currentTileY * TILES_SIZE;
            int yOffset = (int) (TILES_SIZE - hitbox.height);
            return yPosition + yOffset - 1;
        } else {
            // Salto e urto contro il soffitto
            // La nuova posizione sarà la coordinata Y della casella corrente, il  che assicura che la testa del
            // personaggio raggiunga il soffitto
            return currentTileY * TILES_SIZE;
        }
    }

    public static float XPositionNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / TILES_SIZE);
        if (xSpeed > 0) {
            // Movimento a destra
            int tileXPos = currentTile * TILES_SIZE;
            int xOffset = (int) (TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else
            // Movimento a sinistra
            return currentTile * TILES_SIZE;
    }

}
