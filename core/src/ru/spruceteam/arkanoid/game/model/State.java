package ru.spruceteam.arkanoid.game.model;

public enum State {
    BEGIN, PROCESS{
        @Override
        public void update(Level level, float delta) {
            super.update(level, delta);
            for(Ball ball : level.getBalls())
                ball.update(delta);
            level.getCollisionProcessor().act();
        }
    }, PAUSE, LOSE, WIN;

    public void update(Level level, float delta) {}
}
