package ru.spruceteam.arkanoid.game.model;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Rectangle;

public class Brick extends Rectangle {

    private final TextureMapObject mapObject;

    public Brick(TextureMapObject mapObject) {
        this.mapObject = mapObject;
        setPosition(mapObject.getX(), mapObject.getY());
        MapProperties prop = mapObject.getProperties();
        setSize(prop.get("width", Float.class), prop.get("height", Float.class));
    }

    public TextureMapObject getMapObject() {
        return mapObject;
    }
}
