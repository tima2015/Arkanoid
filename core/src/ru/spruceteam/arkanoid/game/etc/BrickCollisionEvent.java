package ru.spruceteam.arkanoid.game.etc;

import ru.spruceteam.arkanoid.game.model.Ball;
import ru.spruceteam.arkanoid.game.model.Brick;

public class BrickCollisionEvent implements Event {
    private final Ball ball;
    private final Brick brick;

    public BrickCollisionEvent(Ball ball, Brick brick) {
        this.ball = ball;
        this.brick = brick;
    }

    public Ball getBall() {
        return ball;
    }

    public Brick getBrick() {
        return brick;
    }
}
