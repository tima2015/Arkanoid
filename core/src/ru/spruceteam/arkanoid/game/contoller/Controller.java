package ru.spruceteam.arkanoid.game.contoller;

import com.badlogic.gdx.InputAdapter;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.GameSettings;
import ru.spruceteam.arkanoid.game.model.Level;

public class Controller extends InputAdapter {

    private final MoveLeftActivatedCommand moveLeftCommand;
    private final MoveRightActivatedCommand moveRightCommand;
    private final StartCommand startCommand;
    private final PauseGameCommand pauseGameCommand;


    public Controller(Level level){
        moveLeftCommand = new MoveLeftActivatedCommand(level);
        moveRightCommand = new MoveRightActivatedCommand(level);
        startCommand = new StartCommand(level);
        pauseGameCommand = new PauseGameCommand(level);
    }

    public void act(float delta){
        moveLeftCommand.act(delta);
        moveRightCommand.act(delta);
    }

    @Override
    public boolean keyDown(int keycode) {
        GameSettings settings = Core.getCore().getSettings();
        if (keycode == settings.leftKey.getValue()){
            moveLeftCommand.activate();
            return true;
        }
        if (keycode == settings.rightKey.getValue()){
            moveRightCommand.activate();
            return true;
        }
        if (keycode == settings.startKey.getValue()){
            startCommand.run();
            return true;
        }
        if (keycode == settings.pauseKey.getValue()){
            pauseGameCommand.run();
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        GameSettings settings = Core.getCore().getSettings();
        if (keycode == settings.leftKey.getValue()){
            moveLeftCommand.deactivate();
            return true;
        }
        if (keycode == settings.rightKey.getValue()){
            moveRightCommand.deactivate();
            return true;
        }
        return false;
    }


}
