package ru.spruceteam.arkanoid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import ru.spruceteam.arkanoid.Core;

import static ru.spruceteam.arkanoid.Constants.*;

public class LevelData {
    private static final String TAG = "LevelData";

    public enum State{
        BEGIN, PROCESS, PAUSE, LOSE, WIN;
    }
    private final int mapNumber;
    private final TiledMap map;
    private final float worldWidth, worldHeight;
    private final int blockWidth, blockHeight;
    private final Array<Block> blocks = new Array<>();
    private final PlayerPlatform playerPlatform;
    private final Array<Ball> balls = new Array<>();
    private State state = State.BEGIN;
    private int score;
    private int lives;
    private final int difficult;

    public LevelData(int mapNumber, int difficult, int lives, int score) {
        this.lives = lives;
        this.score = score;
        Gdx.app.debug(TAG, "LevelData() called with: mapNumber = [" + mapNumber + "]");
        this.mapNumber = mapNumber;
        this.difficult = difficult;
        map = Core.getCore().getManager().get(getMapFilePath(), TiledMap.class);
        MapProperties prop = map.getProperties();
        worldWidth = prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class);
        worldHeight = prop.get("height", Integer.class) * prop.get("tileheight", Integer.class);
        MapProperties blockLayerProp = map.getTileSets().getTileSet("blocks").getProperties();
        blockWidth = blockLayerProp.get("tilewidth", Integer.class);
        blockHeight = blockLayerProp.get("tileheight", Integer.class);
        //Инициализируем блоки
        Array<TextureMapObject> blocks = map.getLayers().get("blocks").getObjects().getByType(TextureMapObject.class);
        for (TextureMapObject block : blocks)
            this.blocks.add(new Block(block));
        Gdx.app.debug(TAG, blocks.size + " blocks was initialized.");
        //Инициализируем платформу игрока
        playerPlatform = new PlayerPlatform(worldWidth, difficult);
        Gdx.app.debug(TAG, "Platform was initialized.");
    }

    public String getMapFilePath(){
        return MAPS_DIR + mapNumber + ".tmx";
    }

    public int getMapNumber() {
        return mapNumber;
    }

    public TiledMap getMap() {
        return map;
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }

    public float getBlockWidth() {
        return blockWidth;
    }

    public float getBlockHeight() {
        return blockHeight;
    }

    public Array<Block> getBlocks() {
        return blocks;
    }

    public PlayerPlatform getPlayerPlatform() {
        return playerPlatform;
    }

    public Array<Ball> getBalls() {
        return balls;
    }

    public interface OnStateChangeListener{
        void onChange(State old, State current);
    }

    private final Array<OnStateChangeListener> onStateChangeListeners = new Array<>();

    public void addOnStateChangeListener(OnStateChangeListener listener){
        onStateChangeListeners.add(listener);
    }

    public void removeOnStateChangeListener(OnStateChangeListener listener){
        onStateChangeListeners.removeValue(listener, true);
    }

    public void setState(State state) {
        State old = this.state;
        this.state = state;
        for (OnStateChangeListener listener : onStateChangeListeners) {
            listener.onChange(old, state);
        }
    }

    public State getState() {
        return state;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getDifficult() {
        return difficult;
    }

    public int getLives() {
        return lives;
    }

    public void loseLive(){
        lives--;
    }
}
