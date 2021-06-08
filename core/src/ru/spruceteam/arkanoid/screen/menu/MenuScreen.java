package ru.spruceteam.arkanoid.screen.menu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.screen.etc.SoundedClickListener;

public class MenuScreen extends ScreenAdapter {
    private static final String TAG = "MenuScreen";

    static InputListener createLabelInputListener(final Label prev, final Label next, final Runnable onEnter){
        return new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                Gdx.app.debug(TAG, "keyDown() called with: event = [" + event + "], keycode = [" + keycode + "]");
                Skin skin = Core.getCore().getSkin();
                Label listenerActor = (Label) event.getListenerActor();
                switch (keycode){
                    case Input.Keys.DOWN:
                        event.getListenerActor().getStage().setKeyboardFocus(prev);
                        listenerActor.getStage().setKeyboardFocus(prev);
                        listenerActor.setStyle(skin.get("default", Label.LabelStyle.class));
                        prev.setStyle(skin.get("selection", Label.LabelStyle.class));
                        return true;
                    case Input.Keys.UP:
                        event.getListenerActor().getStage().setKeyboardFocus(next);
                        listenerActor.getStage().setKeyboardFocus(next);
                        listenerActor.setStyle(skin.get("default", Label.LabelStyle.class));
                        next.setStyle(skin.get("selection", Label.LabelStyle.class));
                        return true;
                    case Input.Keys.ENTER:
                        listenerActor.setStyle(skin.get("default", Label.LabelStyle.class));
                        onEnter.run();
                        return true;
                }
                return super.keyDown(event, keycode);
            }
        };
    }

    private final MenuGroup menuGroup;
    private final SettingsMenuGroup settingsMenuGroup;
    private final HighScoreGroup highScoreGroup;
    private final TextArea aboutTextArea;
    private final TextButton backButton;
    private final SelectDifficultGroup selectDifficultGroup;

    private final Stage stage;

    public MenuScreen(){
        stage = new Stage(new ScreenViewport());
        Skin skin = Core.getCore().getSkin();

        backButton = new TextButton("Back", skin);
        stage.addActor(backButton);
        backButton.setSize(backButton.getWidth()*2f, backButton.getHeight()*2f);
        backButton.setPosition(stage.getWidth() - backButton.getWidth() - 8, 8);
        backButton.setVisible(false);
        backButton.addListener(new SoundedClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                hideSubMenus();
            }
        });

        menuGroup = new MenuGroup(this);
        settingsMenuGroup = new SettingsMenuGroup();
        highScoreGroup = new HighScoreGroup();
        selectDifficultGroup = new SelectDifficultGroup();
        stage.addActor(menuGroup);
        stage.addActor(settingsMenuGroup);
        stage.addActor(highScoreGroup);
        stage.addActor(selectDifficultGroup);

        menuGroup.setPosition((stage.getWidth() - menuGroup.getWidth())*.5f,
                (stage.getHeight() - menuGroup.getHeight())*.5f);
        settingsMenuGroup.setPosition(0, (stage.getHeight() - settingsMenuGroup.getHeight())*.5f);
        settingsMenuGroup.setWidth(stage.getWidth());
        settingsMenuGroup.setVisible(false);

        highScoreGroup.setPosition(0, backButton.getY() + backButton.getHeight());
        highScoreGroup.setSize(stage.getWidth(), stage.getHeight() - highScoreGroup.getY());
        highScoreGroup.setVisible(false);

        aboutTextArea = new TextArea(Gdx.files.internal("about.txt").readString(), skin);
        stage.addActor(aboutTextArea);
        aboutTextArea.setPosition(0, backButton.getY() + backButton.getHeight());
        aboutTextArea.setSize(stage.getWidth(), stage.getHeight() - aboutTextArea.getY());
        aboutTextArea.setAlignment(Align.left);
        aboutTextArea.setVisible(false);

        selectDifficultGroup.setPosition((stage.getWidth() - selectDifficultGroup.getWidth())*.5f,
                (stage.getHeight() - selectDifficultGroup.getHeight())*.5f);
        selectDifficultGroup.setVisible(false);
    }

    private void hideSubMenus(){
        backButton.setVisible(false);
        aboutTextArea.setVisible(false);
        settingsMenuGroup.setVisible(false);
        highScoreGroup.setVisible(false);
        selectDifficultGroup.setVisible(false);
        menuGroup.setVisible(true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.setDebugAll(Gdx.app.getLogLevel() == Application.LOG_DEBUG);
        menuGroup.setVisible(true);
        Music music = Core.getCore().getManager().get("audio/mainmenu.mp3", Music.class);
        music.setVolume(Core.getCore().getSettings().music.getFVal());
        hideSubMenus();
        music.play();
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    MenuGroup getMenuGroup() {
        return menuGroup;
    }

    SettingsMenuGroup getSettingsMenuGroup(){
        return settingsMenuGroup;
    }

    HighScoreGroup getHighScoreGroup() {
        return highScoreGroup;
    }

    public TextArea getAboutTextArea() {
        return aboutTextArea;
    }

    public SelectDifficultGroup getSelectDifficultGroup() {
        return selectDifficultGroup;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
