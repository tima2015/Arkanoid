package ru.spruceteam.arkanoid.game.etc;

public class LevelEventListener {

    public void handle(Event event){
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

    }

    protected void platformCollision(PlatformCollisionEvent event){

    }

    protected void brickCollision(BrickCollisionEvent event){

    }

    protected void stateChange(StateChangeEvent event){

    }

    protected void ballDestroyed(BallDestroyedEvent event){

    }
}
