package ru.spruceteam.arkanoid.game.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.game.model.Ball;
import ru.spruceteam.arkanoid.game.model.Brick;
import ru.spruceteam.arkanoid.game.model.Level;
import ru.spruceteam.arkanoid.game.model.Platform;

public class GameObjectView {

    private final Level level;

    private final TextureRegion ballTextureRegion;
    private final TextureRegion platformTextureRegion;

    public GameObjectView(Level level) {
        this.level = level;
        TextureAtlas atlas = Core.getCore().getManager().get("misc.txt", TextureAtlas.class);
        ballTextureRegion = atlas.findRegion("EnergyBall");
        platformTextureRegion = atlas.findRegion("VausSpacecraftLarge");
    }

    public void draw(Batch batch) {
        batch.begin();
        drawBricks(batch);
        drawBalls(batch);
        drawPlatform(batch);
        batch.end();
    }

    private void drawBricks(Batch batch){
        for (Brick brick : level.getBricks())
            batch.draw(brick.getMapObject().getTextureRegion(), brick.x, brick.y, brick.width, brick.height);
    }

    private void drawBalls(Batch batch){
        for (Ball ball : level.getBalls())
            batch.draw(ballTextureRegion, ball.x - ball.radius, ball.y - ball.radius,
                    ball.radius*2, ball.radius*2);
    }

    private void drawPlatform(Batch batch){
        Platform platform = level.getPlatform();
        batch.draw(platformTextureRegion, platform.x, platform.y, platform.width, platform.height);
    }
}
