package ru.spruceteam.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;

public final class Constants {

    private static final String TAG = "Constants";

    private Constants(){}

    public static final String MAPS_DIR = "map/";
    public static final String SETTINGS_NAME = "settings";
    public static final int PLAYER_PLATFORM_WIDTH;
    public static final int PLAYER_PLATFORM_HEIGHT;
    public static final float PLAYER_PLATFORM_SPEED;
    public static final float LEVEL_INFO_PANEL_WIDTH = 100;
    public static final int SCORE_PER_BLOCK;
    public static final int LEVELS_COUNT;
    public static final float BALL_RADIUS;
    public static final float BALL_SPEED;

    static {
        Properties properties = new Properties();
        try {
            FileHandle file = Gdx.files.local("conf.properties");
            if (!file.exists()) {
                Gdx.app.debug(TAG, "Properties file not exists! Creating default...");
                file.writeString("player.platform.width=256\n" +
                        "player.platform.height=64\n" +
                        "player.platform.speed=2000\n" +
                        "player.spb=1\n" +
                        "level.count=5\n" +
                        "ball.radius=20\n" +
                        "ball.speed=750", false);
            }
            properties.load(new FileReader(file.file()));
            Gdx.app.debug(TAG, "Properties load successful!");
        } catch (IOException e) {
            String message = "Config file corrupted!\n" + e.getMessage();
            Gdx.app.error(TAG, message, e);
            Gdx.app.exit();
            JOptionPane.showMessageDialog(null, message);
        }
        PLAYER_PLATFORM_WIDTH = Integer.parseInt(properties.getProperty("player.platform.width", "64"));
        PLAYER_PLATFORM_HEIGHT = Integer.parseInt(properties.getProperty("player.platform.height", "8"));
        PLAYER_PLATFORM_SPEED = Integer.parseInt(properties.getProperty("player.platform.speed", "250"));
        SCORE_PER_BLOCK = Integer.parseInt(properties.getProperty("player.spb", "1"));
        LEVELS_COUNT = Integer.parseInt(properties.getProperty("level.count", "5"));
        BALL_RADIUS = Integer.parseInt(properties.getProperty("ball.radius", "10"));
        BALL_SPEED = Integer.parseInt(properties.getProperty("ball.speed", "2"));

        Gdx.app.debug(TAG, "static initializer: Properties read successful!");
    }
}
