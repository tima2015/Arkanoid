package ru.spruceteam.arkanoid.screen.menu;

import com.badlogic.gdx.scenes.scene2d.Group;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.screen.etc.SettingsGroup;
import ru.spruceteam.arkanoid.screen.etc.SettingsKeyBind;
import ru.spruceteam.arkanoid.screen.etc.SettingsLevel;

public class SettingsMenuGroup extends Group {
    private final SettingsGroup[] group = new SettingsGroup[]{
            new SettingsLevel(Core.getCore().getSettings().music),
            new SettingsLevel(Core.getCore().getSettings().sound),
            new SettingsKeyBind(Core.getCore().getSettings().leftKey),
            new SettingsKeyBind(Core.getCore().getSettings().rightKey),
            new SettingsKeyBind(Core.getCore().getSettings().startKey),
            new SettingsKeyBind(Core.getCore().getSettings().pauseKey)
    };

    public SettingsMenuGroup(){
        addActor(group[5]);
        group[5].setPosition(8,0);
        for (int i = 4; i >= 0 ; i--) {
            addActor(group[i]);
            group[i].setPosition(8, group[i+1].getY() + group[i+1].getHeight()*1.5f);
        }
        setHeight(group[0].getY() + group[0].getHeight());
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();//getStage().getWidth(),
        for (SettingsGroup settingsGroup : group) {
            settingsGroup.setWidth(getWidth()-16);
        }
    }
}
