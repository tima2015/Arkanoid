package ru.spruceteam.arkanoid.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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
import ru.spruceteam.arkanoid.game.LevelData;
import ru.spruceteam.arkanoid.screen.etc.SoundedClickListener;

public class LevelEndScreen extends ScreenAdapter {

    private final Stage stage;


    public LevelEndScreen(boolean win, LevelData data){
        stage = new Stage(new ScreenViewport());
        Skin skin = Core.getCore().getSkin();

        Label title = new Label("", skin);
        stage.addActor(title);
        title.setPosition(0,stage.getHeight()*.7f);
        title.setWidth(stage.getWidth());
        title.setAlignment(Align.center);

        Label hint = new Label("Write you name:", skin);
        TextButton toMainMenu = new TextButton("To menu...", skin);
        TextButton nextLevel = new TextButton("Next level", skin);
        TextField nameInput = new TextField("", skin);
        if (win){
            if (data.getMapNumber() == Constants.LEVELS_COUNT) {
                title.setText("You are win and complete game!!!");

                stage.addActor(hint);
                hint.setPosition(0,title.getY() - hint.getHeight()*1.5f);
                hint.setWidth(stage.getWidth());

                stage.addActor(nameInput);
                nameInput.setPosition(0,hint.getY() - nameInput.getHeight()*1.5f);
                nameInput.setAlignment(Align.center);

                stage.addActor(toMainMenu);
                toMainMenu.setWidth(toMainMenu.getWidth()*2);
                toMainMenu.setPosition((stage.getWidth()-toMainMenu.getWidth())*.5f,
                        nameInput.getY() - toMainMenu.getHeight()*1.5f);
            } else {
                title.setText("You win!");

                stage.addActor(nextLevel);
                nextLevel.setPosition((stage.getWidth()-toMainMenu.getWidth())*.5f,
                        title.getY() - toMainMenu.getHeight()*2f);
            }
        } else {
            title.setText("You are win and complete game!!!");

            stage.addActor(hint);
            hint.setPosition(0,title.getY() - hint.getHeight()*1.5f);
            hint.setWidth(stage.getWidth());

            stage.addActor(nameInput);
            nameInput.setPosition(0,hint.getY() - nameInput.getHeight()*1.5f);
            nameInput.setAlignment(Align.center);

            stage.addActor(toMainMenu);
            toMainMenu.setWidth(toMainMenu.getWidth()*2);
            toMainMenu.setPosition((stage.getWidth()-toMainMenu.getWidth())*.5f,
                    nameInput.getY() - toMainMenu.getHeight()*1.5f);
        }
        
        toMainMenu.addListener(new SoundedClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Core.getCore().getScreen().dispose();
                Core.getCore().setScreen(Core.getCore().getMenuScreen());
            }
        });
        nextLevel.addListener(new SoundedClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Core.getCore().getScreen().dispose();
                Core.getCore().setScreen(new LevelScreen(new LevelData(data.getMapNumber()+1,
                        data.getDifficult(), data.getLives(), data.getScore())));
            }
        });
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
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
