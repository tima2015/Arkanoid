package ru.spruceteam.arkanoid.game.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.game.etc.*;
import ru.spruceteam.arkanoid.game.model.Level;
import ru.spruceteam.arkanoid.game.model.State;
import ru.spruceteam.arkanoid.screen.LevelEndScreen;

public class UIView extends Stage {

    private final Level level;
    private final Label score;
    private final Label lives;
    private final Label pause;

    public UIView(Level level) {
        super(new FitViewport(level.getWorldWidth()*2, level.getWorldHeight()));
        this.level = level;
        Skin skin = Core.getCore().getSkin();
        Label scoreTitle = new Label("Score: ", skin);
        addActor(scoreTitle);
        scoreTitle.setPosition(getWidth()/2f + 8, getHeight() - scoreTitle.getHeight() - 8);
        getViewport().setWorldWidth(scoreTitle.getX() + scoreTitle.getWidth()*2);
        getViewport().apply(true);

        score = new Label(Integer.toString(level.getScore()), skin);
        addActor(score);
        score.setPosition(scoreTitle.getX(), scoreTitle.getY() - scoreTitle.getHeight() - 8);

        Label livesTitle = new Label("Lives: " , skin);
        addActor(livesTitle);
        livesTitle.setPosition(scoreTitle.getX(), score.getY() - score.getHeight()*2 - 16);

        lives = new Label(Integer.toString(level.getLives()), skin);
        addActor(lives);
        lives.setPosition(livesTitle.getX() + livesTitle.getWidth(), livesTitle.getY());

        pause = new Label("PAUSE", skin);
        addActor(pause);
        pause.setPosition(scoreTitle.getX(), getHeight()*.25f);
        pause.setVisible(false);

        level.addListener(new LevelEventListener(){
            @Override
            protected void stateChange(StateChangeEvent event) {
                super.stateChange(event);
                pause.setVisible(event.getCurrent() == State.PAUSE);
                if (event.getCurrent() == State.LOSE){
                    Core.getCore().getManager().get("audio/lose.mp3", Sound.class)
                            .play(Core.getCore().getSettings().sound.getValue());
                    Core.getCore().setScreen(new LevelEndScreen(false, level));
                }else if (event.getCurrent() == State.WIN){
                    Core.getCore().getManager().get("audio/win.mp3", Sound.class)
                            .play(Core.getCore().getSettings().sound.getValue());
                    Core.getCore().setScreen(new LevelEndScreen(true, level));
                }
            }

            @Override
            protected void borderCollision(BorderCollisionEvent event) {
                super.borderCollision(event);
                Core.getCore().getManager().get("audio/border_hit.mp3", Sound.class)
                        .play(Core.getCore().getSettings().sound.getValue());
            }

            @Override
            protected void platformCollision(PlatformCollisionEvent event) {
                super.platformCollision(event);
                Core.getCore().getManager().get("audio/platform_hit.mp3", Sound.class)
                        .play(Core.getCore().getSettings().sound.getValue());
            }

            @Override
            protected void brickCollision(BrickCollisionEvent event) {
                super.brickCollision(event);
                score.setText(level.getScore());
                Core.getCore().getManager().get("audio/brick_hit.mp3", Sound.class)
                        .play(Core.getCore().getSettings().sound.getValue());
            }

            @Override
            protected void ballDestroyed(BallDestroyedEvent event) {
                super.ballDestroyed(event);
                lives.setText(level.getLives());
                Core.getCore().getManager().get("audio/ball_destroy.mp3", Sound.class)
                        .play(Core.getCore().getSettings().sound.getValue());
            }
        });
        TextureMapObject bg = (TextureMapObject) level.getMap().getLayers().get("misc").getObjects().get("bg");
        Image bgImage = new Image(bg.getTextureRegion());
        addActor(bgImage);
        bgImage.setPosition(bg.getX(), bg.getY());
        bgImage.setSize(bg.getProperties().get("width", Float.class),
                bg.getProperties().get("height", Float.class));
    }
}
