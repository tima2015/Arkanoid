package ru.spruceteam.arkanoid.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import ru.spruceteam.arkanoid.game.contoller.Controller;
import ru.spruceteam.arkanoid.game.model.Level;
import ru.spruceteam.arkanoid.game.view.GameObjectView;
import ru.spruceteam.arkanoid.game.view.UIView;

public class GameScreen extends ScreenAdapter {

    private final Level level;
    private final Controller controller;
    private final UIView uiView;
    private final GameObjectView gameObjectView;

    public GameScreen(Level level){
        this.level = level;
        controller = new Controller(level);
        uiView = new UIView(level);
        gameObjectView = new GameObjectView(level);
        uiView.setDebugAll(Gdx.app.getLogLevel() == Application.LOG_DEBUG);
    }

    @Override
    public void show() {
        super.show();
        InputMultiplexer processor = new InputMultiplexer();
        processor.addProcessor(controller);
        processor.addProcessor(uiView);
        Gdx.input.setInputProcessor(processor);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        level.update(delta);
        controller.act(delta);
        uiView.act();
        uiView.draw();
        gameObjectView.draw(uiView.getBatch());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        uiView.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        uiView.dispose();
    }
}
