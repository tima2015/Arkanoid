package ru.spruceteam.arkanoid.game.etc;

import com.badlogic.gdx.Gdx;

public class LevelEventListener {

    private static final String TAG = "LevelEventListener";

    public void handle(Event event){
        Gdx.app.debug(TAG, "handle() called with: event = [" + event + "]");
        if (event instanceof BorderCollisionEvent)
            borderCollision((BorderCollisionEvent) event);
        else if (event instanceof PlatformCollisionEvent)
            platformCollision((PlatformCollisionEvent) event);
        else if (event instanceof BrickCollisionEvent)
            brickCollision((BrickCollisionEvent) event);
        else if (event instanceof StateChangeEvent)
            stateChange((StateChangeEvent) event);
        else if (event instanceof BallDestroyedEvent)
            ballDestroyed((BallDestroyedEvent) event);
    }

    protected void borderCollision(BorderCollisionEvent event){
        Gdx.app.debug(TAG, "borderCollision() called with: event = [" + event + "]");
    }

    protected void platformCollision(PlatformCollisionEvent event){
        Gdx.app.debug(TAG, "platformCollision() called with: event = [" + event + "]");
    }

    protected void brickCollision(BrickCollisionEvent event){
        Gdx.app.debug(TAG, "brickCollision() called with: event = [" + event + "]");
    }

    protected void stateChange(StateChangeEvent event){
        Gdx.app.debug(TAG, "stateChange() called with: event = [" + event + "]");
    }

    protected void ballDestroyed(BallDestroyedEvent event){
        Gdx.app.debug(TAG, "ballDestroyed() called with: event = [" + event + "]");
    }
}
