package ru.spruceteam.arkanoid.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Array;
import ru.spruceteam.arkanoid.Constants;
import ru.spruceteam.arkanoid.Core;
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

    //Игровые объекты
    private final Array<Brick> bricks = new Array<>();
    private final Array<Ball> balls = new Array<>();
    private final Platform platform;

    //Текущее состояние игры
    private State state = State.BEGIN;

    //Обработчик столкновений
    private final CollisionProcessor collisionProcessor = new CollisionProcessor(this);

    //Слушатели, для более гибкого взаимодействия с представлением
    private final Array<LevelEventListener> listeners = new Array<>();

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
        TiledMap map = Core.getCore().getManager().get(path, TiledMap.class);
        MapProperties mapProp = map.getProperties();
        Gdx.app.debug(TAG, "Level: map picked from assets manager");

        //Расчитываем размеры мира. Умножаем количество ячеек в стоке(столбце) умножаем на ширину(высоту) ячейки
        worldWidth = mapProp.get("width", Integer.class) * mapProp.get("tilewidth", Integer.class);
        worldHeight = mapProp.get("height", Integer.class) * mapProp.get("tileheight", Integer.class);
        Gdx.app.debug(TAG, "Level: worldWidth = [" + worldWidth + "], worldHeight = [" + worldHeight + "]");

        //Читаем размер кирпичика
        MapProperties bricksLayerProp = map.getTileSets().getTileSet("blocks").getProperties();
        bricksWidth = bricksLayerProp.get("tilewidth", Integer.class);
        bricksHeight = bricksLayerProp.get("tileheight", Integer.class);
        Gdx.app.debug(TAG, "Level: bricksWidth = [" + bricksWidth + "], brickHeight = [" + bricksHeight + "]");

        //Считываем данные о кирпичиках и заполняем ими массив
        Array<TextureMapObject> bricks = map.getLayers().get("bricks").getObjects().getByType(TextureMapObject.class);
        for (TextureMapObject brick : bricks)
            this.bricks.add(new Brick(brick));
        Gdx.app.debug(TAG, "Level: " + this.bricks.size + " bricks was created");

        //Создание платформы управляемой игроком
         platform = new Platform((worldWidth - Constants.PLAYER_PLATFORM_WIDTH)*.5f, 1);

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

    CollisionProcessor getCollisionProcessor() {
        return collisionProcessor;
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
        return bricks;
    }

    public Array<Ball> getBalls() {
        return balls;
    }

    public Platform getPlatform() {
        return platform;
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
}
