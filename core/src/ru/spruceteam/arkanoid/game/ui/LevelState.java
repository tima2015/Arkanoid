package ru.spruceteam.arkanoid.game.ui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.bcel.internal.Const;
import ru.spruceteam.arkanoid.Constants;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.game.Ball;
import ru.spruceteam.arkanoid.game.Block;
import ru.spruceteam.arkanoid.game.LevelData;
import ru.spruceteam.arkanoid.screen.LevelEndScreen;

import java.lang.reflect.Executable;

import static ru.spruceteam.arkanoid.game.LevelData.State.WIN;

public class LevelState extends Stage {
    private static final String TAG = "LevelState";

    private LevelData data;

    //Render fields
    private BitmapFont dF = new BitmapFont();
    private TiledMapRenderer renderer;
    private Label score;
    private Label lives;
    private Label toStartInfo;

    public LevelState(LevelData data){
        super(new FitViewport(data.getWorldWidth() + data.getBlockWidth()*2 + Constants.LEVEL_INFO_PANEL_WIDTH,
                data.getWorldHeight() + data.getBlockHeight()));
        Gdx.app.debug(TAG, "LevelState() called with: data = [" + data + "]");
        getCamera().position.set(getCamera().position.x-data.getBlockWidth()*.5f, getCamera().position.y, 0);
        getCamera().update();
        this.data = data;
        this.renderer = new OrthoCachedTiledMapRenderer(data.getMap());
        initGameActors();
        initInfoActors();
        data.addOnStateChangeListener(new OnLevelStageChangedListenerImp());
        setDebugAll(Gdx.app.getLogLevel() == Application.LOG_DEBUG);
    }

    private void initInfoActors() {
        Skin skin = Core.getCore().getSkin();
        Label scoreTitle = new Label("Score:",skin);
        addActor(scoreTitle);
        scoreTitle.setPosition(data.getWorldWidth() + data.getBlockWidth()*.5f + 4,
                getViewport().getWorldHeight() - scoreTitle.getHeight() - 8);
        scoreTitle.setWidth(50);
        scoreTitle.setFontScale(0.25f);

        score = new Label("0", skin);
        addActor(score);
        score.setPosition(scoreTitle.getX() + scoreTitle.getWidth() + 2,
                scoreTitle.getY());
        score.setFontScale(0.25f);

        Image platformImg = new Image(Core.getCore().getManager().<TextureAtlas>get("misc.txt")
                .findRegion("VausSpacecraft"));
        addActor(platformImg);
        platformImg.setPosition(scoreTitle.getX(),
                scoreTitle.getY() - (scoreTitle.getHeight() + platformImg.getHeight())*.5f - 16);

        lives = new Label("X" + data.getLives(), skin);
        addActor(lives);
        lives.setPosition(platformImg.getX() + platformImg.getWidth() + 2,
                scoreTitle.getY() - (scoreTitle.getHeight() + 16));
        lives.setFontScale(0.25f);

        Label pauseInfo = new Label("Press Esc for pause", skin);
        pauseInfo.setFontScale(0.7f);
        addActor(pauseInfo);
        pauseInfo.setPosition(scoreTitle.getX(), 8);
        pauseInfo.setFontScale(0.175f);

        toStartInfo =
                new Label("Press " + Input.Keys.toString(Core.getCore().getSettings().startKey.getValue()) +
                        " to start!", skin);
        addActor(toStartInfo);
        toStartInfo.setWidth(data.getWorldWidth());
        toStartInfo.setPosition(0, data.getWorldHeight()*.5f);
        toStartInfo.setAlignment(Align.center);
        toStartInfo.setFontScale(0.25f);
    }

    private void initGameActors(){
        for (Block block : data.getBlocks()) {
            addActor(block);
        }
        addActor(data.getPlayerPlatform());
    }

    public void resize(int width, int height){
        getViewport().update(width, height);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        score.setText(Integer.toString(data.getScore()));
    }

    @Override
    public void draw() {
            renderer.setView((OrthographicCamera) getViewport().getCamera());
            renderer.render();
            super.draw();
    }

    @Override
    public boolean keyUp(int keyCode) {
        return data.getPlayerPlatform().keyUp(keyCode) || super.keyUp(keyCode);
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (data.getState() == LevelData.State.BEGIN && keyCode == Constants.GAME_START_KEY){
            Ball ball = new Ball(data);
            data.getBalls().add(ball);
            addActor(ball);
            data.setState(LevelData.State.PROCESS);
            return true;
        }
        return data.getPlayerPlatform().keyDown(keyCode) || super.keyDown(keyCode);
    }

    @Override
    public void dispose() {
        Gdx.app.debug(TAG, "dispose() called");
        super.dispose();
        dF.dispose();
    }

    private class OnLevelStageChangedListenerImp implements LevelData.OnStateChangeListener {
        @Override
        public void onChange(LevelData.State old, LevelData.State current) {
            switch (current){
                case BEGIN:
                    toStartInfo.setVisible(true);
                    lives.setText("X" + data.getLives());
                    break;
                case PROCESS:
                    toStartInfo.setVisible(false);
                    break;
                case WIN:
                case LOSE:
                    Core.getCore().getScreen().dispose();
                    Core.getCore().setScreen(new LevelEndScreen(current == WIN, data));
                    break;
            }
        }
    }

}
