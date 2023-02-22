package knight.arkham.helpers;

import knight.arkham.Playground;

public class Constants {

//    Para saber cual seria mi ppm ideal, debería de tomar en cuenta la altura de mi personaje, si su altura es 32,
//    pues asi sera mi ppm
    public static final float PIXELS_PER_METER = 32.0f;

    public static final int FULL_SCREEN_HEIGHT = Playground.INSTANCE.getScreenHeight();
    public static final float BOX2D_FULL_SCREEN_HEIGHT = Playground.INSTANCE.getScreenHeight() / PIXELS_PER_METER;
    public static final int FULL_SCREEN_WIDTH = Playground.INSTANCE.getScreenWidth();
    public static final float BOX2D_FULL_SCREEN_WIDTH = Playground.INSTANCE.getScreenWidth() / PIXELS_PER_METER;
    public static final int MID_SCREEN_HEIGHT = Playground.INSTANCE.getScreenHeight() / 2;
    public static final int MID_SCREEN_WIDTH = Playground.INSTANCE.getScreenWidth() / 2;
}
