package ru.spruceteam.arkanoid.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.screen.etc.SoundedClickListener;

public class MenuGroup extends Group {

    private static final String TAG = "MenuGroup";

    MenuGroup(MenuScreen menuScreen){
        Skin skin = Core.getCore().getSkin();
        TextButton exitButton = new TextButton("Exit", skin);
        addActor(exitButton);
        exitButton.setPosition(0,0);

        TextButton aboutButton = new TextButton("About", skin);
        addActor(aboutButton);
        aboutButton.setPosition(0, exitButton.getY() + exitButton.getHeight()*3f);

        TextButton settingsButton = new TextButton("Settings", skin);
        addActor(settingsButton);
        settingsButton.setPosition(0, aboutButton.getY() + aboutButton.getHeight()*3f);

        TextButton highScoreButton = new TextButton("High score", skin);
        addActor(highScoreButton);
        highScoreButton.setPosition(0, settingsButton.getY() + settingsButton.getHeight()*3f);

        TextButton playButton = new TextButton("Start game", skin);
        addActor(playButton);
        playButton.setPosition(0,highScoreButton.getY() + highScoreButton.getHeight()*3f);

        //setWidth
        playButton.setSize(playButton.getWidth()*2f, playButton.getHeight()*2f);
        exitButton.setSize(playButton.getWidth(), playButton.getHeight());
        aboutButton.setSize(playButton.getWidth(), playButton.getHeight());
        settingsButton.setSize(playButton.getWidth(), playButton.getHeight());
        highScoreButton.setSize(playButton.getWidth(), playButton.getHeight());

        exitButton.addListener(new SoundedClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });

        aboutButton.addListener(new SoundedClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                menuScreen.getMenuGroup().setVisible(false);
                menuScreen.getAboutTextArea().setVisible(true);
                menuScreen.getBackButton().setVisible(true);
            }
        });
        settingsButton.addListener(new SoundedClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                menuScreen.getMenuGroup().setVisible(false);
                menuScreen.getSettingsMenuGroup().setVisible(true);
                menuScreen.getBackButton().setVisible(true);
            }
        });
        highScoreButton.addListener(new SoundedClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                menuScreen.getMenuGroup().setVisible(false);
                menuScreen.getHighScoreGroup().setVisible(true);
                menuScreen.getBackButton().setVisible(true);
            }
        });
        playButton.addListener(new SoundedClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                menuScreen.getMenuGroup().setVisible(false);
                menuScreen.getSelectDifficultGroup().setVisible(true);
                menuScreen.getBackButton().setVisible(true);
            }
        });

        //initialize logo
        Image logo = new Image(Core.getCore().getManager().<Texture>get("logo.png"));
        addActor(logo);
        float prop = playButton.getHeight()/playButton.getWidth();
        logo.setWidth(playButton.getWidth());
        logo.setHeight(logo.getWidth()*prop*2);
        logo.setPosition(playButton.getX() + (playButton.getWidth()-logo.getWidth())*.5f,
                playButton.getY() + playButton.getHeight()*2f);
        setSize(playButton.getWidth(), logo.getY() + logo.getHeight());
    }
}
