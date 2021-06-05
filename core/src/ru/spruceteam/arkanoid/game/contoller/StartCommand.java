package ru.spruceteam.arkanoid.game.contoller;

import ru.spruceteam.arkanoid.Constants;
import ru.spruceteam.arkanoid.game.model.Ball;
import ru.spruceteam.arkanoid.game.model.Level;
import ru.spruceteam.arkanoid.game.model.State;

public class StartCommand implements Runnable {

    private final Level level;

    public StartCommand(Level level) {
        this.level = level;
    }

    @Override
    public void run() {
        if (level.getState() == State.BEGIN){
            level.getBalls().add(new Ball(level.getPlatform().x + level.getPlatform().getWidth()*.5f,
                    level.getPlatform().y + level.getPlatform().getHeight() + Constants.BALL_RADIUS + 2));
            level.setState(State.PROCESS);
        }
    }
}
