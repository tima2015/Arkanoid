package ru.spruceteam.arkanoid.screen.etc;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.GameSettings;

public abstract class SettingsGroup extends Group {

    private final Label titleLabel;
    private final GameSettings.SettingIntField field;

    public SettingsGroup(GameSettings.SettingIntField field){
        this.field = field;
        addActor(titleLabel = new Label(field.getKey(), Core.getCore().getSkin()));
        titleLabel.setPosition(0,0);
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        titleLabel.setHeight(getHeight());
    }

    protected GameSettings.SettingIntField getField() {
        return field;
    }
}
