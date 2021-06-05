package ru.spruceteam.arkanoid.game.etc;

import ru.spruceteam.arkanoid.game.model.Ball;

public class BallDestroyedEvent implements Event{
    private final Ball ball;

    public BallDestroyedEvent(Ball ball) {
        this.ball = ball;
    }

    public Ball getBall() {
        return ball;
    }
}
