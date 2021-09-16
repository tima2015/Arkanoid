package ru.spruceteam.arkanoid.screen.etc;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.GameSettings;

public class SettingsKeyBind extends SettingsGroup{

    private final Label keyCodeLabel;

    public SettingsKeyBind(GameSettings.SettingIntField field) {
        super(field);
        int keyCode = field.getValue();

        addActor(keyCodeLabel = new Label(Input.Keys.toString(keyCode), Core.getCore().getSkin()));
        keyCodeLabel.setPosition(0,0);
        keyCodeLabel.setAlignment(Align.right);

        keyCodeLabel.addListener(new SettingsKeyBindInputLister());
        setHeight(keyCodeLabel.getHeight()*2);
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        keyCodeLabel.setSize(getWidth() - 8, getHeight());
    }

    private class SettingsKeyBindInputLister extends ClickListener {

        private boolean active = false;
        private String lastText;

        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            active = !active;
            if (active) {
                getStage().setKeyboardFocus(event.getListenerActor());
                lastText = keyCodeLabel.getText().toString();
                keyCodeLabel.setText("Press key...");
            } else {
                getStage().setKeyboardFocus(null);
                keyCodeLabel.setText(lastText);
            }
            Core.getCore().getManager().get("audio/click.wav", Sound.class)
                    .play(Core.getCore().getSettings().sound.getFVal());
        }

        @Override
        public boolean keyUp(InputEvent event, int keycode) {
            if (active){
                getField().setValue(keycode);
                keyCodeLabel.setText(Input.Keys.toString(keycode));
                getStage().setKeyboardFocus(null);
                active = false;
                Core.getCore().getManager().get("audio/click.wav", Sound.class)
                        .play(Core.getCore().getSettings().sound.getFVal());
                return true;
            }
            return super.keyUp(event, keycode);
        }
    }
}
