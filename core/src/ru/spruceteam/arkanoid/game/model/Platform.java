package ru.spruceteam.arkanoid.game.model;

import com.badlogic.gdx.math.Rectangle;
import ru.spruceteam.arkanoid.Constants;

public class Platform extends Rectangle {

    Platform(float x, float y){
        super(x,y, Constants.PLAYER_PLATFORM_WIDTH, Constants.PLAYER_PLATFORM_HEIGHT);
    }
}
