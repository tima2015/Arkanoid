package ru.spruceteam.arkanoid.game.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.game.etc.LevelEventListener;
import ru.spruceteam.arkanoid.game.etc.StateChangeEvent;
import ru.spruceteam.arkanoid.game.model.Level;
import ru.spruceteam.arkanoid.game.model.State;

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
        scoreTitle.setPosition(getWidth()/2f + 8, getHeight() - getHeight() - scoreTitle.getHeight() - 8);

        score = new Label(Integer.toString(level.getScore()), skin);
        addActor(score);
        score.setPosition(scoreTitle.getX(), scoreTitle.getY() - scoreTitle.getHeight() - 8);

        Label livesTitle = new Label("Lives: " , skin);
        addActor(livesTitle);
        livesTitle.setPosition(scoreTitle.getX(), (getHeight() - livesTitle.getHeight())*0.5f);

        lives = new Label(Integer.toString(level.getLives()), skin);
        addActor(lives);
        lives.setPosition(livesTitle.getX() + livesTitle.getX(), livesTitle.getY());

        pause = new Label("PAUSE", skin);
        addActor(pause);
        pause.setPosition(getWidth()*.75f - pause.getWidth()*.5f, getHeight()*.25f);

        level.addListener(new LevelEventListener(){
            @Override
            protected void stateChange(StateChangeEvent event) {
                super.stateChange(event);
                pause.setVisible(event.getCurrent() == State.PAUSE);
            }
        });
    }

    @Override
    public void act() {
        super.act();
        score.setText(level.getScore());
        lives.setText(level.getScore());
    }
}
