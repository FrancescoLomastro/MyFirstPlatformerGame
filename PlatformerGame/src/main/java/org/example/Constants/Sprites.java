package org.example.Constants;

import static org.example.Constants.Window.SCALE;

public class Sprites {
    public static final int ENTITY_ANIMATION_SPEED = 25;

    public class Player{
        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;
        public static final int JUMP = 5;
        public static final int LAND = 6;
        public static final int FALL = 7;


        
        public static final int PLAYER_WIDTH_DEFAULT = 64;
        public static final int PLAYER_HEIGHT_DEFAULT = 40;
        public static final int PLAYER_WIDTH = (int) (PLAYER_WIDTH_DEFAULT * SCALE);
        public static final int PLAYER_HEIGHT = (int) (PLAYER_HEIGHT_DEFAULT * SCALE);
        public static final int PLAYER_HITTEN_COUNTER_MAX = 6;

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
            public static final int CRABBY_DRAWOFFSET_X = (int) (26 * SCALE);
            public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * SCALE);
            public static final int CRABBY_ATTACKBOX_WIDTH = (int)(75*SCALE);
            public static final int CRABBY_ATTACKBOX_HEIGHT = (int)(19*SCALE);
            public static final int CRABBY_ATTACKBOX_OFFSET_X = (int)(-28*SCALE);
        }

        public class Shark{
            public static final int SHARK_WIDTH_DEFAULT = 34;
            public static final int SHARK_HEIGHT_DEFAULT = 30;
            public static final int SHARK_WIDTH = (int) (SHARK_WIDTH_DEFAULT * SCALE);
            public static final int SHARK_HEIGHT = (int) (SHARK_HEIGHT_DEFAULT * SCALE);
            public static final int SHARK_DRAWOFFSET_X = (int) (8 * SCALE);
            public static final int SHARK_DRAWOFFSET_Y = (int) (6 * SCALE);
            public static final int SHARK_ATTACKBOX_WIDTH = (int)(20*SCALE);
            public static final int SHARK_ATTACKBOX_HEIGHT = (int)(15*SCALE);
            public static final int SHARK_ATTACKBOX_OFFSET_X = (int)(8*SCALE);
        }

        public class Star{
            public static final int STAR_WIDTH_DEFAULT = 34;
            public static final int STAR_HEIGHT_DEFAULT = 30;
            public static final int STAR_WIDTH = (int) (STAR_WIDTH_DEFAULT * SCALE);
            public static final int STAR_HEIGHT = (int) (STAR_HEIGHT_DEFAULT * SCALE);
            public static final int STAR_DRAWOFFSET_X = (int) (9 * SCALE);
            public static final int STAR_DRAWOFFSET_Y = (int) (7 * SCALE);
            public static final int STAR_ATTACKBOX_WIDTH = (int)(22*SCALE);
            public static final int STAR_ATTACKBOX_HEIGHT = (int)(20*SCALE);
            public static final int STAR_ATTACKBOX_OFFSET_X = (int)(-2*SCALE);
        }



    }

    public class Levels {
        public static final int SPRITE_HOLE = 255;
        public static final int INTERNAL_TEXTURE_INIT = 132;
        public static final int INTERNAL_TEXTURE_END = 179;
    }
}
