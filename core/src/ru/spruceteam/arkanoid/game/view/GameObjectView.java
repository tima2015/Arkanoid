package ru.spruceteam.arkanoid.game.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dongbat.jbump.Rect;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.game.model.Brick;
import ru.spruceteam.arkanoid.game.model.Level;

public class GameObjectView {

    private final Level level;

    private final TextureRegion ballTextureRegion;
    private final TextureRegion platformTextureRegion;

    public GameObjectView(Level level) {
        this.level = level;
        TextureAtlas atlas = Core.getCore().getManager().get("misc.txt", TextureAtlas.class);
        ballTextureRegion = atlas.findRegion("EnergyBall");
        platformTextureRegion = atlas.findRegion("VausSpacecraft");
    }

    public void draw(Batch batch) {
        batch.begin();
        drawBricks(batch);
        drawBall(batch);
        drawPlatform(batch);
        batch.end();
    }

    private void drawBricks(Batch batch) {
        for (Brick brick : level.getBricks()) {
            Rect rect = level.getWorld().getRect(brick);
            batch.draw(brick.getMapObject().getTextureRegion(), rect.x, rect.y, rect.w, rect.h);
        }
    }

    private void drawBall(Batch batch) {
        if (level.getBall() == null)
            return;
        Rect rect = level.getWorld().getRect(level.getBall());
        batch.draw(ballTextureRegion, rect.x, rect.y, rect.w, rect.h);
    }

    private void drawPlatform(Batch batch) {
        Rect rect = level.getWorld().getRect(level.getPlatform());
        batch.draw(platformTextureRegion, rect.x, rect.y, rect.w, rect.h);
    }
}
