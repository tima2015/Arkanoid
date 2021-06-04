package ru.spruceteam.arkanoid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.spruceteam.arkanoid.Constants;
import ru.spruceteam.arkanoid.Core;

import static ru.spruceteam.arkanoid.Constants.PLAYER_PLATFORM_HEIGHT;
import static ru.spruceteam.arkanoid.Constants.PLAYER_PLATFORM_WIDTH;

public class PlayerPlatform extends Actor {

    public enum State{
        STAY,
        MOVE_LEFT,
        MOVE_RIGHT
    }

    private State state = State.STAY;
    private final Sprite sprite;
    private final float worldWidth;
    private Rectangle body;

    PlayerPlatform(float worldWidth, int difficult){
        this.worldWidth = worldWidth;
        sprite = new Sprite(Core.getCore().getManager().<TextureAtlas>get("misc.txt")
                .findRegion("VausSpacecraftLarge"));
        body = new Rectangle(0,0,0,0);
        setPosition((worldWidth - PLAYER_PLATFORM_WIDTH)*0.5f, PLAYER_PLATFORM_HEIGHT);
        setSize(PLAYER_PLATFORM_WIDTH/(float)difficult, PLAYER_PLATFORM_HEIGHT);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (state == State.MOVE_LEFT && getX() > 0)
            setX(getX()-Constants.PLAYER_PLATFORM_SPEED*delta);
        else if (state == State.MOVE_RIGHT && getX() + getWidth() < worldWidth)
            setX(getX()+Constants.PLAYER_PLATFORM_SPEED*delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch, parentAlpha);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        body.setPosition(getX(), getY());
        sprite.setPosition(getX(), getY());
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        body.setSize(getWidth(), getHeight());
        sprite.setSize(getWidth(), getHeight());
    }

    public boolean keyUp(int keyCode) {
        if (state == State.MOVE_LEFT){
            if (keyCode == Input.Keys.LEFT) {
                state = Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? State.MOVE_RIGHT : State.STAY;
                return true;
            }
        } else if (state == State.MOVE_RIGHT){
            if (keyCode == Input.Keys.RIGHT) {
                state = Gdx.input.isKeyPressed(Input.Keys.LEFT) ? State.MOVE_LEFT : State.STAY;
                return true;
            }
        }
        return false;
    }

    public boolean keyDown(int keyCode) {
        if (state == State.STAY){
            if (keyCode == Input.Keys.LEFT) {
                state = State.MOVE_LEFT;
                return true;
            }
            if (keyCode == Input.Keys.RIGHT) {
                state = State.MOVE_RIGHT;
                return true;
            }
        }
        return false;
    }

    public State getState() {
        return state;
    }
}
