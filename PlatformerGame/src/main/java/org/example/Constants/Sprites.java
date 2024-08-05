package org.example.Constants;

import static org.example.Constants.Window.SCALE;

public class Sprites {
    public static final int PLAYER_ANIMATION_SPEED = 25;

    public class Player{
        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int JUMP = 2;
        public static final int FALL = 3;
        public static final int LAND = 4;
        public static final int ATTACK = 6;
        public static final int HIT = 7;
        public static final int DEAD = 8;


        
        public static final int PLAYER_WIDTH_DEFAULT = 64;
        public static final int PLAYER_HEIGHT_DEFAULT = 40;
        public static final int PLAYER_WIDTH = (int) (PLAYER_WIDTH_DEFAULT * SCALE);
        public static final int PLAYER_HEIGHT = (int) (PLAYER_HEIGHT_DEFAULT * SCALE);

    }

    public class Enemy{
        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;
        
        
        public class Crabby{
            public static final int CRABBY_WIDTH_DEFAULT = 72;
            public static final int CRABBY_HEIGHT_DEFAULT = 32;
            public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * SCALE);
            public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * SCALE);
        }
    }

    public class Level{
        public static final int SPRITE_HOLE = 11;
        public static final int ANIMATED_WATER_SPRITE_AMOUNT = 4;
        public static final int WATER_ANIMATION_SPEED = 25;
    }
}
