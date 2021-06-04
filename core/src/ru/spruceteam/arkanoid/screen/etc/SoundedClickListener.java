package ru.spruceteam.arkanoid.screen.etc;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.spruceteam.arkanoid.Core;

public class SoundedClickListener extends ClickListener {

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        Core.getCore().getManager().get("audio/click.wav", Sound.class)
                .play(Core.getCore().getSettings().sound.getFVal());
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        Core.getCore().getManager().get("audio/checked.wav", Sound.class)
                .play(Core.getCore().getSettings().sound.getFVal());
    }
}
