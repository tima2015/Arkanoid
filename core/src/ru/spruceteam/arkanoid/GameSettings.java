package ru.spruceteam.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

public class GameSettings {

    public final static class SettingIntField{
        private final String key;
        private final int defValue;
        private int value;
        private float fVal;

        private SettingIntField(String key, int defValue) {
            this.key = key;
            this.defValue = defValue;
        }

        private void load(Preferences pref){
            value = pref.getInteger(key, defValue);
            fVal = value/100f;
        }

        private void save(Preferences pref){
             pref.putInteger(key, value);
        }

        public void setValue(int value) {
            this.value = value;
            fVal = value/100f;
        }

        public int getValue() {
            return value;
        }

        public float getFVal() {
            return fVal;
        }

        public String getKey() {
            return key;
        }
    }

    public final SettingIntField music = new SettingIntField("Music", 100);
    public final SettingIntField sound = new SettingIntField("Sound", 100);
    public final SettingIntField leftKey = new SettingIntField("Left", Input.Keys.LEFT);
    public final SettingIntField rightKey = new SettingIntField("Right", Input.Keys.RIGHT);
    public final SettingIntField startKey = new SettingIntField("Start", Input.Keys.SPACE);
    public final SettingIntField pauseKey = new SettingIntField("Start", Input.Keys.ESCAPE);

    GameSettings(){}

    void load(){
        Preferences pref = Gdx.app.getPreferences(Constants.SETTINGS_NAME);
        music.load(pref);
        sound.load(pref);
        leftKey.load(pref);
        rightKey.load(pref);
        startKey.load(pref);
    }

    void save(){
        Preferences pref = Gdx.app.getPreferences(Constants.SETTINGS_NAME);
        music.save(pref);
        sound.save(pref);
        leftKey.save(pref);
        rightKey.save(pref);
        startKey.save(pref);
        pref.flush();
    }
}
