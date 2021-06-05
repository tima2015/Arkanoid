package ru.spruceteam.arkanoid.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import ru.spruceteam.arkanoid.game.LevelData;
import ru.spruceteam.arkanoid.game.ui.LevelState;

public class LevelScreen extends ScreenAdapter {

    private final LevelData levelData;
    private final LevelState state;

    public LevelScreen(LevelData levelData){
        this.levelData = levelData;
        state = new LevelState(levelData);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(state);
    }

    @Override
    public void render(float delta) {
        state.act(delta);
        state.draw();
    }

    @Override
    public void resize(int width, int height) {
        state.resize(width, height);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        state.dispose();
    }
}
