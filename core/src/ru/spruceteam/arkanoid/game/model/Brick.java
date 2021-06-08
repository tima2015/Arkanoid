package ru.spruceteam.arkanoid.game.model;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.dongbat.jbump.Item;

public class Brick extends Item<Level> {

    private final TextureMapObject mapObject;

    public Brick(TextureMapObject mapObject, Level level) {
        super(level);
        this.mapObject = mapObject;
    }

    public TextureMapObject getMapObject() {
        return mapObject;
    }
}
