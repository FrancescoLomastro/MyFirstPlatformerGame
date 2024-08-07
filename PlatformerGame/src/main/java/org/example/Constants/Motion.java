package org.example.Constants;

import static org.example.Constants.Window.GAME_WIDTH;
import static org.example.Constants.Window.SCALE;

public class Motion {
    public static float GRAVITY = 0.04f * SCALE;
    public static float COLLISION_FALL_SPEED = 0.5f * SCALE;

    public static int LEFT_LEVEL_BORDER = (int)(0.2 * GAME_WIDTH);
    public static int RIGHT_LEVEL_BORDER = (int)(0.8 * GAME_WIDTH);


    public class Dirctions{
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
    }
}
