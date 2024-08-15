package org.example.Constants;

import static org.example.Constants.Window.SCALE;

public class UI{
    public static final int MENU_BACKGROUND_WIDTH_DEFAULT = (int) (141 * SCALE);
    public static final int MENU_BACKGROUND_HEIGHT_DEFAULT = (int) (168 * SCALE);
    public static final int MENU_BACKGROUND_WIDTH = (int) (MENU_BACKGROUND_WIDTH_DEFAULT * SCALE);
    public static final int MENU_BACKGROUND_HEIGHT = (int) (MENU_BACKGROUND_HEIGHT_DEFAULT * SCALE);

    public static class URMButtons {
        public static final int URM_SIZE_DEFAULT = 56;
        public static final int URM_SIZE = (int) (URM_SIZE_DEFAULT*SCALE);
    }

    public static class Buttons {
        public static final int B_WIDTH_DEFAULT = 140;
        public static final int B_HEIGHT_DEFAULT = 56;
        public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * SCALE);
        public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * SCALE);
    }

    public static class SoundButtons{
        public static final int SOUND_SIZE_DEFAULT = 42;
        public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT*SCALE);
    }

    public static class VolumeButtons{
        public static final int VOLUME_WIDTH_DEFAULT = 28;
        public static final int VOLUME_HEIGHT_DEFAULT = 44;
        public static final int SLIDER_WIDTH_DEFAULT = 215;
        public static final int VOLUME_WIDTH = (int) (VOLUME_WIDTH_DEFAULT*SCALE);
        public static final int VOLUME_HEIGHT = (int) (VOLUME_HEIGHT_DEFAULT*SCALE);
        public static final int SLIDER_WIDTH = (int) (SLIDER_WIDTH_DEFAULT*SCALE);
    }


}