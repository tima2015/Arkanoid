package ru.spruceteam.arkanoid.game.etc;

import ru.spruceteam.arkanoid.game.model.Ball;

public class BorderCollisionEvent implements Event {
    private final Ball ball;

    public BorderCollisionEvent(Ball ball) {
        this.ball = ball;
    }

    public Ball getBall() {
        return ball;
    }
}
