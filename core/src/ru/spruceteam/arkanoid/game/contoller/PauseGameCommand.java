package ru.spruceteam.arkanoid.game.contoller;

import ru.spruceteam.arkanoid.game.model.Level;
import ru.spruceteam.arkanoid.game.model.State;

public class PauseGameCommand implements Runnable{

    private final Level level;

    public PauseGameCommand(Level level) {
        this.level = level;
    }

    @Override
    public void run() {
        if (level.getState() == State.PROCESS)
            level.setState(State.PAUSE);
        else if (level.getState() == State.PAUSE)
            level.setState(State.PROCESS);
    }
}
