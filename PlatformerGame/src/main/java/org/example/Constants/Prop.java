package org.example.Constants;

import static org.example.Constants.Window.SCALE;

public class Prop {

    public class BigCloud {
        public static final int[] BIG_CLOUD_WIDTH_DEFAULT = big_clouds_width_def();
        public static final int[] BIG_CLOUD_HEIGHT_DEFAULT = big_clouds_height_def();
        public static final int[] BIG_CLOUD_WIDTH = big_clouds_width();
        public static final int[] BIG_CLOUD_HEIGHT =  big_clouds_height();

        public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;
        public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * SCALE);

        private static int[] big_clouds_width_def() {
            int[] big_clouds_width = new int[3];
            big_clouds_width[0] = 591;
            big_clouds_width[1] = 179;
            big_clouds_width[2] = 190;
            return big_clouds_width;
        }

        private static int[] big_clouds_height_def() {
            int[] big_clouds_height = new int[3];
            big_clouds_height[0] = 102;
            big_clouds_height[1] = 102;
            big_clouds_height[2] = 102;
            return big_clouds_height;
        }

        private static int[] big_clouds_width() {
            int[] big_clouds_width = new int[3];
            for(int i = 0; i<3; i++){
                big_clouds_width[i] = (int) (BIG_CLOUD_WIDTH_DEFAULT[i] * SCALE);
            }
            return big_clouds_width;
        }

        private static int[] big_clouds_height() {
            int[] big_clouds_height = new int[3];
            for(int i = 0; i<3; i++){
                big_clouds_height[i] = (int) (BIG_CLOUD_HEIGHT_DEFAULT[i] * SCALE);
            }
            return big_clouds_height;
        }

        public BigCloud(){

        }
    }

    public class SmallCloud {
        public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;
        public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * SCALE);
    }

    public class Sword {
        public static final int SWORD_SPRITE_AMOUNT = 5;
        public static final int SWORD_ANIMATION_SPEED = 60;

        public static final int SWORD = 0;
        public static final int SWORD_WIDTH_DEFAULT = 16;
        public static final int SWORD_HEIGHT_DEFAULT = 17;
        public static final int SWORD_WIDTH = (int) (SWORD_WIDTH_DEFAULT * SCALE);
        public static final int SWORD_HEIGHT = (int) (SWORD_HEIGHT_DEFAULT * SCALE);
    }

    public class Ship {
        public static final int SHIP_SPRITE_AMOUNT = 8;
        public static final int SHIP_ANIMATION_SPEED = 25;


        public static final int SHIP_WIDTH_DEFAULT = 80;
        public static final int SHIP_HEIGHT_DEFAULT = 76;
        public static final int SHIP_WIDTH = (int) (SHIP_WIDTH_DEFAULT * SCALE);
        public static final int SHIP_HEIGHT = (int) (SHIP_HEIGHT_DEFAULT * SCALE);

    }

    public class Seagull {

        public static final int SEAGULL_SPRITE_AMOUNT = 6;
        public static final int SEAGULL_ANIMATION_SPEED = 80;

        public static final int SEAGULL_WIDTH_DEFAULT = 30;
        public static final int SEAGULL_HEIGHT_DEFAULT = 37;
        public static final int SEAGULL_WIDTH = (int) (SEAGULL_WIDTH_DEFAULT * SCALE);
        public static final int SEAGULL_HEIGHT = (int) (SEAGULL_HEIGHT_DEFAULT * SCALE);

    }

    public class Cannon {
        public static final int CANNON_SPRITE_AMOUNT = 7;
        public static final int CANNON_ANIMATION_SPEED = 25;

        public static final int CANNON_WIDTH_DEFAULT = 40;
        public static final int CANNON_HEIGHT_DEFAULT = 26;
        public static final int CANNON_WIDTH = (int) (CANNON_WIDTH_DEFAULT * SCALE);
        public static final int CANNON_HEIGHT = (int) (CANNON_HEIGHT_DEFAULT * SCALE);


        public static final int CANNON_RIGHT = 1;
        public static final int CANNON_LEFT = 2;


        public static final int CANNON_BALL_DEFAULT_WIDTH = 15;
        public static final int CANNON_BALL_DEFAULT_HEIGHT = 15;
        public static final int CANNON_BALL_WIDTH = (int)(SCALE * CANNON_BALL_DEFAULT_WIDTH);
        public static final int CANNON_BALL_HEIGHT = (int)(SCALE * CANNON_BALL_DEFAULT_HEIGHT);
        public static final float CANNON_BALL_SPEED = 0.75f * SCALE;
    }

    public class Potion {

        public static final int POTION_SPRITE_AMOUNT = 7;
        public static final int POTION_ANIMATION_SPEED = 25;

        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (POTION_WIDTH_DEFAULT * SCALE);
        public static final int POTION_HEIGHT = (int) (POTION_HEIGHT_DEFAULT * SCALE);


        public static final int POTION = 3;
    }

    public class Barrels {
        public static final int BARREL_WIDTH_DEFAULT = 32;
        public static final int BARREL_HEIGHT_DEFAULT = 32;
        public static final int BARREL_WIDTH = (int) (BARREL_WIDTH_DEFAULT * SCALE);
        public static final int BARREL_HEIGHT = (int) (BARREL_HEIGHT_DEFAULT * SCALE);


        public static final int STAND_BARREL = 0;
        public static final int SIDE_BARREL = 1;
        public static final int DOUBLE_SIDE_BARREL = 2;
        public static final int BARREL_POTION = 3;
    }

    public class Bottles {
        public static final int BOTTLE_WIDTH_DEFAULT = 32;
        public static final int BOTTLE_HEIGHT_DEFAULT = 32;
        public static final int BOTTLE_WIDTH = (int) (BOTTLE_WIDTH_DEFAULT * SCALE);
        public static final int BOTTLE_HEIGHT = (int) (BOTTLE_HEIGHT_DEFAULT * SCALE);


        public static final int STAND_POTION1 = 0;
        public static final int SIDE_POTION2 = 1;
    }

    public class Door {
        public static final int DOOR_WIDTH_DEFAULT = 64;
        public static final int DOOR_HEIGHT_DEFAULT = 64;
        public static final int DOOR_WIDTH = (int) (DOOR_WIDTH_DEFAULT * SCALE);
        public static final int DOOR_HEIGHT = (int) (DOOR_HEIGHT_DEFAULT * SCALE);


        public static final int DOOR = 0;
    }

    public class Candle {

        public static final int CANDLE_SPRITE_AMOUNT = 6;
        public static final int CANDLE_ANIMATION_SPEED = 25;


        public static final int CANDLE_WIDTH_DEFAULT = 32;
        public static final int CANDLE_HEIGHT_DEFAULT = 32;
        public static final int CANDLE_WIDTH = (int) (CANDLE_WIDTH_DEFAULT * SCALE);
        public static final int CANDLE_HEIGHT = (int) (CANDLE_HEIGHT_DEFAULT * SCALE);


    }


}
