package ru.spruceteam.arkanoid.screen.menu;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.game.LevelData;
import ru.spruceteam.arkanoid.screen.LevelScreen;
import ru.spruceteam.arkanoid.screen.etc.SoundedClickListener;

public class SelectDifficultGroup extends Group {

    SelectDifficultGroup() {
        Skin skin = Core.getCore().getSkin();
        Label title = new Label("Choose difficult!",skin);
        title.setHeight(title.getHeight()*2);
        title.setAlignment(Align.center);
        addActor(title);

        TextButton hardButton = new TextButton("Hard", skin);
        addActor(hardButton);
        hardButton.setSize(title.getWidth(),title.getHeight());

        TextButton mediumButton = new TextButton("Medium", skin);
        addActor(mediumButton);
        mediumButton.setSize(hardButton.getWidth(), hardButton.getHeight());

        TextButton easyButton = new TextButton("Easy", skin);
        addActor(easyButton);
        easyButton.setSize(mediumButton.getWidth(), mediumButton.getHeight());

        easyButton.setPosition(0,0);
        mediumButton.setPosition(0, easyButton.getY() + easyButton.getHeight()*1.5f);
        hardButton.setPosition(0, mediumButton.getY() + mediumButton.getHeight()*1.5f);
        title.setPosition(0, hardButton.getY() + hardButton.getHeight()*1.5f);

        setSize(title.getWidth(), title.getY() + title.getHeight());

        hardButton.addListener(new SoundedClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                startGame(3);
            }
        });
        mediumButton.addListener(new SoundedClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                startGame(2);
            }
        });
        easyButton.addListener(new SoundedClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                startGame(1);
            }
        });
    }

    public void startGame(int difficult){
        LevelData data = new LevelData(1, difficult, 5, 0);
        Core.getCore().getManager().get("audio/mainmenu.mp3", Music.class).stop();
        Core.getCore().setScreen(new LevelScreen(data));
    }
}
