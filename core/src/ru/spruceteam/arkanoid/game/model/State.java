package ru.spruceteam.arkanoid.game.model;

import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.*;
import ru.spruceteam.arkanoid.Constants;
import ru.spruceteam.arkanoid.game.etc.BorderCollisionEvent;
import ru.spruceteam.arkanoid.game.etc.LevelEventListener;
import ru.spruceteam.arkanoid.game.etc.PlatformCollisionEvent;

public enum State {
    BEGIN, PROCESS {
        private boolean checkBorderCollision(Level level){
            Ball ball = level.getBall();
            Rect rect = level.getWorld().getRect(ball);
            Vector2 u = ball.getU();
            if (rect.x < 0) {
                u.set(Math.abs(u.x), u.y);
                return true;
            } else if (rect.x + rect.w > level.getWorldWidth()) {
                u.set(-Math.abs(u.x), u.y);
                return true;
            } else if (rect.y < 0) {
                u.set(u.x, Math.abs(u.y));
                level.destroyBall();
                return true;
            } else if (rect.y + rect.h > level.getWorldHeight()) {
                u.set(u.x, -Math.abs(u.y));
                return true;
            }
            return false;
        }

        @Override
        public void update(Level level, float delta) {
            super.update(level, delta);
            Vector2 u = level.getBall().getU();
            Rect rect = level.getWorld().getRect(level.getBall());
            level.getWorld().move(level.getBall(), rect.x + u.x * delta, rect.y + u.y * delta,
                    (item, other) -> (world, collision, x, y, w, h, goalX, goalY, filter, result) -> {
                        Ball ball = (Ball) collision.item;
                        if (collision.other instanceof Brick) {
                            if (collision.touch.y <= collision.otherRect.y)
                                ball.getU().set(ball.getU().x, -Math.abs(ball.getU().y));
                            else if (collision.touch.y >= collision.otherRect.y + collision.otherRect.h)
                                ball.getU().set(ball.getU().x, Math.abs(ball.getU().y));
                            else if (collision.touch.x <= collision.otherRect.x)
                                ball.getU().set(-Math.abs(ball.getU().x), ball.getU().y);
                            else if (collision.touch.x >= collision.otherRect.x + collision.otherRect.w)
                                ball.getU().set(Math.abs(ball.getU().x), ball.getU().y);
                            level.destroyBrick((Brick) collision.other);
                        } else if (collision.other.equals(level.getPlatform())){

                            ball.getU().set(new Vector2(collision.touch.x - (collision.otherRect.x + collision.otherRect.w*.5f),
                                    collision.touch.y + 150).nor().scl(Constants.BALL_SPEED));
                            for (LevelEventListener listener : level.getListeners()) {
                                listener.handle(new PlatformCollisionEvent(ball, level.getPlatform()));
                            }
                        }
                        return Response.touch.response(world, collision, x, y, w, h, goalX, goalY, filter, result);
                    });
            if (checkBorderCollision(level))
                for (LevelEventListener listener : level.getListeners())
                    listener.handle(new BorderCollisionEvent(level.getBall()));

        }
    }, PAUSE, LOSE, WIN;

    public void update(Level level, float delta) {
    }
}
