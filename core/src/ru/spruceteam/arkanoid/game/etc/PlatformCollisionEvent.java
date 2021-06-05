package ru.spruceteam.arkanoid.game.etc;

import ru.spruceteam.arkanoid.game.model.Ball;
import ru.spruceteam.arkanoid.game.model.Platform;

public class PlatformCollisionEvent implements Event {
    private final Ball ball;
    private final Platform platform;

    public PlatformCollisionEvent(Ball ball, Platform platform) {
        this.ball = ball;
        this.platform = platform;
    }

    public Ball getBall() {
        return ball;
    }

    public Platform getPlatform() {
        return platform;
    }
}
