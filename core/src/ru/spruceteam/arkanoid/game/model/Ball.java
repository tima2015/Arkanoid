package ru.spruceteam.arkanoid.game.model;

import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Item;
import ru.spruceteam.arkanoid.Constants;

public class Ball extends Item<Level> {
    private final Vector2 u = new Vector2();

    Ball(Level level){
        super(level);
        u.set(u.x, Constants.BALL_SPEED);
    }

    public Vector2 getU() {
        return u;
    }
}
