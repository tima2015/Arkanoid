package ru.spruceteam.arkanoid.game.contoller;

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
            level.addBall();
            level.setState(State.PROCESS);
        }
    }
}
