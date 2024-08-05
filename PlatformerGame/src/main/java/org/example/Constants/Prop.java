package org.example.Constants;

import static org.example.Constants.Window.SCALE;

public class Prop {

    public class Environment {
        public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
        public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
        public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * SCALE);
        public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * SCALE);

        public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;
        public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * SCALE);
    }



    public static final int SWORD = 0;
    public static final int SWORD_WIDTH_DEFAULT = 8;
    public static final int SWORD_HEIGHT_DEFAULT = 13;
    public static final int SWORD_WIDTH = (int) (SWORD_WIDTH_DEFAULT * SCALE);
    public static final int SWORD_HEIGHT = (int) (SWORD_HEIGHT_DEFAULT * SCALE);
}
