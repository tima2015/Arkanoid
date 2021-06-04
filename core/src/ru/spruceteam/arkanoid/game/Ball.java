package ru.spruceteam.arkanoid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import org.w3c.dom.css.Rect;
import ru.spruceteam.arkanoid.Constants;
import ru.spruceteam.arkanoid.Core;

import static java.lang.Math.abs;
import static ru.spruceteam.arkanoid.Constants.BALL_RADIUS;

public class Ball extends Actor {
    private boolean done;
    private final Sprite sprite;
    private final Circle body = new Circle(0,0,0);
    private final Vector2 velocityN = new Vector2(0,1);
    private final LevelData data;

    public Ball(LevelData data) {
        this.data = data;
        sprite = new Sprite(Core.getCore().getManager().<TextureAtlas>get("misc.txt").findRegion("EnergyBall"));
        setSize(BALL_RADIUS*2, BALL_RADIUS*2);
        setPosition(data.getPlayerPlatform().getX() + data.getPlayerPlatform().getWidth()*.5f,
                Constants.PLAYER_PLATFORM_HEIGHT*2f+ BALL_RADIUS + 1);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        checkLevelBorderCollision();
        checkBlocksCollision();
        checkPlatformCollision();
        setPosition(getX() + velocityN.x*Constants.BALL_SPEED*data.getDifficult(),
                getY() + velocityN.y*Constants.BALL_SPEED*data.getDifficult());
    }

    private void checkLevelBorderCollision(){
        if (body.x - BALL_RADIUS < 0){
            velocityN.set(abs(velocityN.x), velocityN.y);
            onLevelBorderCollision();
        } else if (body.x + BALL_RADIUS > data.getWorldWidth()) {
            velocityN.set(-abs(velocityN.x), velocityN.y);
            onLevelBorderCollision();
        } else if (body.y + BALL_RADIUS > data.getWorldHeight()){
            velocityN.set(velocityN.x, -abs(velocityN.y));
            onLevelBorderCollision();
        } else if(body.y - BALL_RADIUS < 0){
            onDone();
        }
    }

    protected void onLevelBorderCollision(){

    }

    protected void onDone(){
        data.getBalls().removeValue(this, true);
        getStage().getActors().removeValue(this, true);
        if (data.getBalls().size == 0){
            if (data.getLives() == 0)
                data.setState(LevelData.State.LOSE);
            else {
                data.loseLive();
                data.setState(LevelData.State.BEGIN);
            }
        }
    }

    private boolean isCollisions(Rectangle rec){
        float dx = abs(body.x - rec.x);
        float dy = abs(body.y - rec.y);

        if (dx > (rec.width*.5f+body.radius) || dy > (rec.height*.5f+body.radius))
            return false;
        if (dx <= (rec.width*.5f) || dy <= (rec.height*.5f))
            return true;
        double d = Math.pow(dx - rec.width*.5f,2) + Math.pow(dy - rec.height*.5f,2);
        return d <= Math.pow(body.radius, 2);
    }

    private void solveBrickCollision(Rectangle rect){
        if (body.x - BALL_RADIUS > rect.x + rect.width)
            velocityN.set(abs(velocityN.x), velocityN.y);
        else if (body.x + BALL_RADIUS< rect.x)
            velocityN.set(-abs(velocityN.x), velocityN.y);
        else if (body.y + BALL_RADIUS < rect.y)
            velocityN.set(velocityN.x, -abs(velocityN.y));
        else
            velocityN.set(velocityN.x, abs(velocityN.y));

    }

    private void checkBlocksCollision(){
        Array.ArrayIterator<Block> blocks = data.getBlocks().iterator();
        while (blocks.hasNext()){
            Block block = blocks.next();
            if (isCollisions(block.getBody())){
                blocks.remove();
                block.getStage().getActors().removeValue(block, true);
                solveBrickCollision(block.getBody());
                onBlockCollision();
            }
        }
    }

    protected void onBlockCollision(){
        data.addScore(Constants.SCORE_PER_BLOCK*data.getDifficult());
    }

    private void checkPlatformCollision(){
        PlayerPlatform platform = data.getPlayerPlatform();
        if (platform.getX() <= body.x && platform.getX() + platform.getWidth() >= body.x &&
                body.y - platform.getY() <= body.radius + platform.getHeight()){
            Vector2 nor = new Vector2(platform.getX() + platform.getWidth() * .5f - body.x,
                    platform.getY() + platform.getHeight() * .5f - body.y).nor();
            velocityN.set(-nor.x*0.5f, abs(nor.y)).nor();
            onPlatformCollision();
        }
    }

    protected void onPlatformCollision(){

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch, parentAlpha);
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        body.setRadius(getWidth()*.5f);
        sprite.setSize(getWidth(), getHeight());
    }

    @Override
    protected void rotationChanged() {
        super.rotationChanged();
        sprite.setRotation(getRotation());
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        body.setPosition(getX() + getWidth()*.5f, getY() + getHeight()*.5f);
        sprite.setPosition(getX(), getY());
    }

    public Circle getBody() {
        return body;
    }
}
