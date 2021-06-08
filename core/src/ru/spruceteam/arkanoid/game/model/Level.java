package ru.spruceteam.arkanoid.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.World;
import ru.spruceteam.arkanoid.Constants;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.game.etc.BallDestroyedEvent;
import ru.spruceteam.arkanoid.game.etc.BrickCollisionEvent;
import ru.spruceteam.arkanoid.game.etc.LevelEventListener;
import ru.spruceteam.arkanoid.game.etc.StateChangeEvent;

public class Level {

    private static final String TAG = "Level";

    //Параметры
    private final int number;
    private final int difficult;
    private int score;
    private int lives;
    //Вычисляемые и считываемые поля
    private final String path;
    private final float worldWidth;
    private final float worldHeight;
    private final float bricksWidth;
    private final float bricksHeight;

    //Объекты физ движка//Игровые объекты
    private final World<Level> world = new World<>();
    private final Array<Brick> brickItemArray = new Array<>();
    private final Item<Level> platformItem;
    private Ball ballItem;

    //Текущее состояние игры
    private State state = State.BEGIN;

    //Слушатели, для более гибкого взаимодействия с представлением
    private final Array<LevelEventListener> listeners = new Array<>();

    //Объект карты
    private final TiledMap map;

    public Level(int number, int difficult, int score, int lives){
        Gdx.app.debug(TAG, "Level() called with: number = [" + number + "], difficult = [" + difficult + "]," +
                " score = [" + score + "], lives = [" + lives + "]");
        this.number = number;
        this.difficult = difficult;
        this.score = score;
        this.lives = lives;

        //Составляем путь к файлу карты по её номеру
        path = Constants.MAPS_DIR + number + ".tmx";
        Gdx.app.debug(TAG, "Level: map path = [" + path + "]");

        //Получаем карту из менеджера зависимостей
        map = Core.getCore().getManager().get(path, TiledMap.class);
        MapProperties mapProp = map.getProperties();
        Gdx.app.debug(TAG, "Level: map picked from assets manager");

        //Расчитываем размеры мира. Умножаем количество ячеек в стоке(столбце) умножаем на ширину(высоту) ячейки
        worldWidth = mapProp.get("width", Integer.class) * mapProp.get("tilewidth", Integer.class);
        worldHeight = mapProp.get("height", Integer.class) * mapProp.get("tileheight", Integer.class);
        Gdx.app.debug(TAG, "Level: worldWidth = [" + worldWidth + "], worldHeight = [" + worldHeight + "]");

        //Читаем размер кирпичика
        MapProperties bricksLayerProp = map.getTileSets().getTileSet("bricks").getProperties();
        bricksWidth = bricksLayerProp.get("tilewidth", Integer.class);
        bricksHeight = bricksLayerProp.get("tileheight", Integer.class);
        Gdx.app.debug(TAG, "Level: bricksWidth = [" + bricksWidth + "], brickHeight = [" + bricksHeight + "]");

        //Считываем данные о кирпичиках и заполняем ими массив
        Array<TextureMapObject> bricks = map.getLayers().get("bricks").getObjects().getByType(TextureMapObject.class);
        for (TextureMapObject brick : bricks)
            brickItemArray.add((Brick) world.add(new Brick(brick, this),
                    brick.getX(), brick.getY(), bricksWidth, bricksHeight));
        Gdx.app.debug(TAG, "Level: " + this.brickItemArray.size + " bricks was created");

        //Создание платформы управляемой игроком
        platformItem = world.add(new Item<>(this), (worldWidth - Constants.PLAYER_PLATFORM_WIDTH)*.5f,
                1, Constants.PLAYER_PLATFORM_WIDTH, Constants.PLAYER_PLATFORM_HEIGHT);
        Gdx.app.debug(TAG, "Level: player platform created");

        Gdx.app.debug(TAG, "Level: created!");
    }

    public void update(float delta){
        for (int i = 0; i < difficult; i++)
            state.update(this, delta);
    }

    public void addListener(LevelEventListener listener){
        listeners.add(listener);
    }

    public void removeListener(LevelEventListener listener){
        listeners.removeValue(listener, true);
    }

    Array<LevelEventListener> getListeners() {
        return listeners;
    }

    public int getNumber() {
        return number;
    }

    public int getDifficult() {
        return difficult;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int addingScore){
        this.score += addingScore;
    }

    public int getLives() {
        return lives;
    }

    public void removeLive(){
        lives--;
    }

    public String getPath() {
        return path;
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }

    public float getBricksWidth() {
        return bricksWidth;
    }

    public float getBricksHeight() {
        return bricksHeight;
    }

    public Array<Brick> getBricks() {
        return brickItemArray;
    }

    public Ball getBall() {
        return ballItem;
    }

    public Item<Level> getPlatform() {
        return platformItem;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        State old = this.state;
        Gdx.app.debug(TAG, "setState() called with: state = [" + state + "], old state is = [" + old + "]");
        this.state = state;
        for (LevelEventListener listener : listeners) {
            listener.handle(new StateChangeEvent(old, state));
        }
    }

    public void addBall(){
        if (ballItem != null)
            return;
        Rect rect = world.getRect(platformItem);
        ballItem = (Ball) world.add(new Ball(this), rect.x + rect.w * .5f - Constants.BALL_RADIUS,
                rect.y + rect.h + 2, Constants.BALL_RADIUS*2, Constants.BALL_RADIUS*2);
    }

    public void destroyBall(){
        if (ballItem == null)
            return;
        world.remove(ballItem);
        for (LevelEventListener listener : listeners) listener.handle(new BallDestroyedEvent(ballItem));
        ballItem = null;
        if (lives == 0)
            setState(State.LOSE);
        else {
            setState(State.BEGIN);
            lives--;
        }
    }

    public void destroyBrick(Brick brick){
        brickItemArray.removeValue(brick, true);
        world.remove(brick);
        addScore(Constants.SCORE_PER_BLOCK*difficult);
        for (LevelEventListener listener : listeners) listener.handle(new BrickCollisionEvent(ballItem, brick));
        if(brickItemArray.isEmpty())
            setState(State.WIN);
    }

    public World<Level> getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }
}
