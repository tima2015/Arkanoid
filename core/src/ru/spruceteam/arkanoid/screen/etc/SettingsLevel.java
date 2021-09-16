package ru.spruceteam.arkanoid.screen.etc;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.GameSettings;

public class SettingsLevel extends SettingsGroup {

    private int level;
    private int levelD = 0;
    private final Label levelLabel;
    private final Image arrowLeftImage;
    private final Image arrowRightImage;

    public SettingsLevel(GameSettings.SettingIntField field){
        super(field);
        level = field.getValue();
        Skin skin = Core.getCore().getSkin();
        addActor(levelLabel = new Label("000", skin));
        addActor(arrowLeftImage = new Image(skin, "arrow_left"));
        addActor(arrowRightImage = new Image(skin, "arrow_right"));

        levelLabel.setWidth(levelLabel.getPrefWidth());
        levelLabel.setText(Integer.toString(level));
        levelLabel.setAlignment(Align.center);

        arrowRightImage.addListener(new ArrowInputListener());
        arrowLeftImage.addListener(new ArrowInputListener());

        setHeight(levelLabel.getHeight()*2);
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        arrowRightImage.setX(getWidth() - arrowRightImage.getWidth() - 8);
        arrowRightImage.setHeight(levelLabel.getPrefHeight());
        arrowRightImage.setY((getHeight()- levelLabel.getPrefHeight())*.5f);
        levelLabel.setX(arrowRightImage.getX() - levelLabel.getWidth());
        levelLabel.setHeight(getHeight());
        arrowLeftImage.setX(levelLabel.getX() - arrowLeftImage.getWidth());
        arrowLeftImage.setHeight(levelLabel.getPrefHeight());
        arrowLeftImage.setY((getHeight()- levelLabel.getPrefHeight())*.5f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (levelD != 0){
            if ((levelD == -1 && level == 0) || (levelD == 1 && level == 100)) {
                levelD = 0;
            }
            level += levelD;
            levelLabel.setText(level);
            getField().setValue(level);
            Core.getCore().getManager().get("audio/click.wav", Sound.class)
                    .play(Core.getCore().getSettings().sound.getFVal());
            Core.getCore().getManager().get("audio/mainmenu.mp3", Music.class)
                    .setVolume(Core.getCore().getSettings().music.getFVal());
        }
    }

    private class ArrowInputListener extends InputListener{
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            super.touchDown(event, x, y, pointer, button);
            if (event.getListenerActor().equals(arrowRightImage))
                levelD = 1;
            else if (event.getListenerActor().equals(arrowLeftImage))
                levelD = -1;
            return true;
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            super.exit(event, x, y, pointer, toActor);
            if (levelD != 0)
                levelD = 0;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            super.touchUp(event, x, y, pointer, button);
            if (levelD != 0)
                levelD = 0;
        }
    }

}
