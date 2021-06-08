package ru.spruceteam.arkanoid.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ru.spruceteam.arkanoid.Constants;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.game.model.Level;
import ru.spruceteam.arkanoid.screen.etc.SoundedClickListener;

public class LevelEndScreen extends ScreenAdapter {

    private final Stage stage;


    public LevelEndScreen(boolean win, Level level){
        stage = new Stage(new ScreenViewport());
        Skin skin = Core.getCore().getSkin();

        Label title = new Label("title", skin);
        stage.addActor(title);
        title.setPosition(0,stage.getHeight()*.7f);
        title.setWidth(stage.getWidth());
        title.setAlignment(Align.center);

        Label hint = new Label("write you name", skin);
        TextButton toMainMenu = new TextButton("To menu...", skin);
        TextButton nextLevel = new TextButton("Next level", skin);
        TextField nameInput = new TextField("", skin);
        nameInput.setHeight(nameInput.getHeight()*3);
        if (win){
            if (level.getNumber() == Constants.LEVELS_COUNT) {
                title.setText("You are win!\nAnd complete game!");

                stage.addActor(hint);
                hint.setPosition(0,title.getY() - hint.getHeight()*4f);
                hint.setWidth(stage.getWidth());
                hint.setAlignment(Align.center);

                stage.addActor(nameInput);
                nameInput.setPosition(0,hint.getY() - hint.getHeight() - nameInput.getHeight());
                nameInput.setWidth(stage.getWidth());
                nameInput.setAlignment(Align.center);

                stage.addActor(toMainMenu);
                toMainMenu.setWidth(toMainMenu.getWidth()*2);
                toMainMenu.setPosition((stage.getWidth()-toMainMenu.getWidth())*.5f,
                        nameInput.getY() - toMainMenu.getHeight()*3f);
                toMainMenu.setVisible(false);
            } else {
                title.setText("You win!");

                stage.addActor(nextLevel);
                nextLevel.setWidth(nextLevel.getWidth()*2);
                nextLevel.setPosition((stage.getWidth()-nextLevel.getWidth())*.5f,
                        title.getY() - title.getHeight()*2 - nextLevel.getHeight());
            }
        } else {
            title.setText("You lose!");

            stage.addActor(hint);
            hint.setPosition(0,title.getY() - hint.getHeight()*3f);
            hint.setWidth(stage.getWidth());
            hint.setAlignment(Align.center);

            stage.addActor(nameInput);
            nameInput.setPosition(0,hint.getY() - hint.getHeight() - nameInput.getHeight());
            nameInput.setWidth(stage.getWidth());
            nameInput.setAlignment(Align.center);

            stage.addActor(toMainMenu);
            toMainMenu.setWidth(toMainMenu.getWidth()*2);
            toMainMenu.setPosition((stage.getWidth()-toMainMenu.getWidth())*.5f,
                    nameInput.getY() - toMainMenu.getHeight()*3f);
        }
        
        toMainMenu.addListener(new SoundedClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                FileHandle hs = Gdx.files.local("hs.txt");
                hs.writeString((hs.exists() ? "\n" : "") + nameInput.getText() + "--" + level.getScore(), true);
                Core.getCore().getScreen().dispose();
                Core.getCore().setScreen(Core.getCore().getMenuScreen());
            }
        });
        nextLevel.addListener(new SoundedClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Core.getCore().setScreen(new GameScreen(new Level(level.getNumber()+1,
                        level.getDifficult(), level.getScore(), level.getLives())));
            }
        });
        nameInput.setTextFieldListener((textField, c) -> toMainMenu.setVisible(!textField.getText().isEmpty()));
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
        stage.setDebugAll(Gdx.app.getLogLevel() == Application.LOG_DEBUG);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
