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
        public static final int SWORD = 0;
        public static final int SWORD_WIDTH_DEFAULT = 8;
        public static final int SWORD_HEIGHT_DEFAULT = 13;
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
}
