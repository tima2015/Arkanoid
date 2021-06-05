package ru.spruceteam.arkanoid.game.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import ru.spruceteam.arkanoid.Constants;

public class Ball extends Circle {

    private final Vector2 u = new Vector2();

    public Ball(float x, float y){
        super(x,y, Constants.BALL_RADIUS);
        u.y = Constants.BALL_SPEED;
    }
    
    public void update(float delta){
        setPosition(x + u.x*delta, y + u.y*delta);

    }

    public Vector2 getU() {
        return u;
    }
}
