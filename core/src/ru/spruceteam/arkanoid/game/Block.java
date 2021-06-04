package ru.spruceteam.arkanoid.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Block extends Actor {
    private final TextureMapObject mapObject;
    private final Sprite sprite;
    private Rectangle body;
    private int lives;

    Block(TextureMapObject mapObject){
        this.mapObject = mapObject;
        sprite = new Sprite(mapObject.getTextureRegion());
        body = new Rectangle(0,0,0,0);
        MapProperties prop = mapObject.getProperties();
        setSize(prop.get("width", Float.class), prop.get("height", Float.class));
        setPosition(mapObject.getX(), mapObject.getY());
        setRotation(prop.get("rotation", 0f, Float.class));
        lives = prop.get("lives", 1, Integer.class);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch, parentAlpha);
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        body.setSize(getWidth(), getHeight());
        sprite.setSize(getWidth(), getHeight());
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        body.setPosition(getX(), getY());
        sprite.setPosition(getX(), getY());
    }

    @Override
    protected void scaleChanged() {
        super.scaleChanged();// TODO: 04.05.2021 scale rectangle?
        sprite.setScale(getScaleX(), getScaleY());
    }

    @Override
    protected void rotationChanged() {
        super.rotationChanged();// TODO: 04.05.2021 rotate rectangle?
        sprite.setRotation(getRotation());
    }

    public void hit(){
        lives--;
    }

    public boolean isGone() {
        return lives == 0;
    }

    public Rectangle getBody() {
        return body;
    }

    public int getLives() {
        return lives;
    }
}
