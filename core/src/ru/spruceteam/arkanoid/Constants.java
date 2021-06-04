package ru.spruceteam.arkanoid;

import com.badlogic.gdx.Input;

public final class Constants {
    private Constants(){}

    public static final String MAPS_DIR = "map/";
    public static final String SETTINGS_NAME = "settings";
    public static final int PLAYER_PLATFORM_WIDTH = 64;
    public static final int PLAYER_PLATFORM_HEIGHT = 8;
    public static final float PLAYER_PLATFORM_SPEED = 250;
    public static final float LEVEL_INFO_PANEL_WIDTH = 100;
    public static final int SCORE_PER_BLOCK = 10;
    public static final int LEVELS_COUNT = 5;
    public static final float BOX2D_SCALE = 1/30f;
    public static final float BALL_RADIUS = 2;
    public static final float BALL_SPEED = 1.5f;
    public static final int GAME_START_KEY = Input.Keys.SPACE;
}
