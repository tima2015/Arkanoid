package ru.spruceteam.arkanoid.game.contoller;

import ru.spruceteam.arkanoid.Constants;
import ru.spruceteam.arkanoid.game.etc.ActivatedCommand;
import ru.spruceteam.arkanoid.game.model.Level;
import ru.spruceteam.arkanoid.game.model.Platform;

public class MoveLeftActivatedCommand extends ActivatedCommand {

    private final Level level;

    public MoveLeftActivatedCommand(Level level) {
        this.level = level;
    }

    @Override
    public void execute(float delta) {
        Platform platform = level.getPlatform();
        if (platform.getX() > 0)
            platform.setX(platform.getX() - Constants.PLAYER_PLATFORM_SPEED* delta);
    }
}
