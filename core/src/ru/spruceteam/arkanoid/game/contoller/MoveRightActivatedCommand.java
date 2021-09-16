package ru.spruceteam.arkanoid.game.contoller;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import ru.spruceteam.arkanoid.Constants;
import ru.spruceteam.arkanoid.game.etc.ActivatedCommand;
import ru.spruceteam.arkanoid.game.model.Level;
import ru.spruceteam.arkanoid.game.model.State;

public class MoveRightActivatedCommand extends ActivatedCommand {
    private final Level level;

    public MoveRightActivatedCommand(Level level) {
        this.level = level;
    }

    @Override
    public void execute(float delta) {
        if(level.getState() == State.PAUSE)
            return;
        Item<Level> platform = level.getPlatform();
        Rect rect = level.getWorld().getRect(platform);
        if (rect.x + rect.w < level.getWorldWidth())
            level.getWorld().move(platform.userData.getPlatform(),
                    rect.x  + Constants.PLAYER_PLATFORM_SPEED*delta, rect.y, CollisionFilter.defaultFilter);
    }
}
