package ru.spruceteam.arkanoid.screen.etc;

import java.util.Comparator;

public class HighScoreRow {
    private final String name;
    private final int score;

    public HighScoreRow(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public static final Comparator<HighScoreRow> comparator = (hsr1, hsr2) -> Integer.compare(hsr2.score, hsr1.score);

}
