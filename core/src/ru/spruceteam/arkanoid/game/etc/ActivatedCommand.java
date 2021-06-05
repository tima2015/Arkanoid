package ru.spruceteam.arkanoid.game.etc;

public abstract class ActivatedCommand {
    private boolean active = false;

    public void activate(){
        active = true;
    }

    public void deactivate(){
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void act(float delta){
        if (active) execute(delta);
    }

    protected abstract void execute(float delta);
}
