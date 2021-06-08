package ru.spruceteam.arkanoid.game.etc;

import ru.spruceteam.arkanoid.game.model.State;

public class StateChangeEvent implements Event{
    private final State old, current;

    public StateChangeEvent(State old, State current) {
        this.old = old;
        this.current = current;
    }

    public State getOld() {
        return old;
    }

    public State getCurrent() {
        return current;
    }

    @Override
    public String toString() {
        return "StateChangeEvent{" +
                "old=" + old +
                ", current=" + current +
                '}';
    }
}
