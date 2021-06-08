package ru.spruceteam.arkanoid.game.etc;

import com.dongbat.jbump.Item;
import ru.spruceteam.arkanoid.game.model.Ball;
import ru.spruceteam.arkanoid.game.model.Level;

public class PlatformCollisionEvent implements Event {
    private final Ball ball;
    private final Item<Level> platform;

    public PlatformCollisionEvent(Ball ball, Item<Level> platform) {
        this.ball = ball;
        this.platform = platform;
    }

    public Ball getBall() {
        return ball;
    }

    public Item<Level> getPlatform() {
        return platform;
    }
}
