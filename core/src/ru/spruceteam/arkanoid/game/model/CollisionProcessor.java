package ru.spruceteam.arkanoid.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Array;
import ru.spruceteam.arkanoid.Constants;
import ru.spruceteam.arkanoid.game.etc.*;

class CollisionProcessor {

    private static final String TAG = "CollisionProcessor";
    
    private final Level level;
    
    CollisionProcessor(Level level){
        this.level = level;
    }

    public void onBrickCollision(Ball ball, Brick brick){
        level.addScore(Constants.SCORE_PER_BLOCK* level.getDifficult());
        if (level.getBricks().isEmpty()){
            level.setState(State.WIN);
        }
        for (LevelEventListener listener : level.getListeners()) {
            listener.handle(new BrickCollisionEvent(ball, brick));
        }
    }

    public void onPlatformCollision(Ball ball, Platform platform){
        for (LevelEventListener listener : level.getListeners()) {
            listener.handle(new PlatformCollisionEvent(ball, platform));
        }
    }

    public void onBorderCollision(Ball ball){
        for (LevelEventListener listener : level.getListeners()) {
            listener.handle(new BorderCollisionEvent(ball));
        }
    }

    public void onBallDestroyed(Ball ball){
        if (level.getBalls().isEmpty()){
            if (level.getLives() == 0)
                level.setState(State.LOSE);
            else {
                level.removeLive();
                level.setState(State.BEGIN);
            }
        }
        for (LevelEventListener listener : level.getListeners()) {
            listener.handle(new BallDestroyedEvent(ball));
        }
    }
    
    void act(){
        Array.ArrayIterator<Ball> ballArrayIterator = level.getBalls().iterator();
        while(ballArrayIterator.hasNext()){
            Ball ball = ballArrayIterator.next();
            Side side;
            Array.ArrayIterator<Brick> brickArrayIterator = level.getBricks().iterator();
            while (brickArrayIterator.hasNext()){
                Brick b = brickArrayIterator.next();
                if ((side = checkCollision(ball, b.x, b.y, b.x + b.getWidth(), b.y + b.getHeight())) != Side.NO){
                    Gdx.app.debug(TAG, "Brick Collision");
                    side.changeBallU(ball);
                    brickArrayIterator.remove();
                    onBrickCollision(ball, b);
                    break;
                }
            }
            Platform p = level.getPlatform();
            if ((side = checkCollision(ball, p.x,p.y, p.x + p.getWidth(), p.y + p.getHeight())) != Side.NO){
                Gdx.app.debug(TAG, "Platform Collision");
                side.changeBallU(ball);
                onPlatformCollision(ball, p);
                continue;
            }
            /*if ((side = checkCollision(ball, 0,0, level.getWorldWidth(), level.getWorldHeight())) != Side.NO){
                Gdx.app.debug(TAG, "Border Collision");
                side.changeBallU(ball);
                if (side == Side.BOTTOM){
                    ballArrayIterator.remove();
                    onBallDestroyed(ball);
                } else
                    onBorderCollision(ball);
            }*/
        }
    }

    private enum Side{
        LEFT(-1,1), RIGHT(-1, 1), TOP(1,-1), BOTTOM(1,-1), NO(0,0);

        private final int xm, ym;

        Side(int xm, int ym){
            this.xm = xm;
            this.ym = ym;
        }

        void changeBallU(Ball ball){
            ball.getU().set(ball.getU().x*xm, ball.getU().y*ym);
        }
    }

    private Side checkCollision(Circle circle, float x0, float y0, float x1, float y1){
        if (intersectBall(circle, x0, y0, x0, y1))
            return Side.LEFT;
        if (intersectBall(circle, x0, y0, x1, y1))
            return Side.TOP;
        if (intersectBall(circle, x1, y1, x1, y0))
            return Side.RIGHT;
        if (intersectBall(circle, x1, y0, x0, y0))
            return Side.BOTTOM;
        return Side.NO;
    }

    private boolean intersectBall(Circle circle, float x0, float y0, float x1, float y1){
        float dy = y1 - y0;
        float dx = x1 - x0;
        double d = Math.abs((y1-y0)*circle.x - (x1 - x0)*circle.y + x1*y0  - y1*x0) / Math.sqrt(dy*dy + dx*dx);
        return d <= circle.radius;
    }

    private Side checkBorderCollision(Ball ball){
        Vector2 u = ball.getU();
        if (ball.x - ball.radius <= 0){
            u.set(Math.abs(u.x), u.y);
            return Side.RIGHT;
        }
        if (ball.x + ball.radius >= level.getWorldWidth()){
            u.set(-Math.abs(u.x), u.y);
            return Side.LEFT;
        }
        if (ball.y - ball.radius <= 0){
            u.set(u.x, Math.abs(u.y));
            return Side.TOP;
        }
        if (ball.y + ball.radius >= level.getWorldHeight()){
            u.set(u.x, -Math.abs(u.y));
            return Side.BOTTOM;
        }
    }
}
