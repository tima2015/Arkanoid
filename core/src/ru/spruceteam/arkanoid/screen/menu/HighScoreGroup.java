package ru.spruceteam.arkanoid.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Align;
import ru.spruceteam.arkanoid.Core;
import ru.spruceteam.arkanoid.screen.etc.HighScoreRow;

import java.util.Arrays;

public class HighScoreGroup extends ScrollPane {
    private final Label names;
    private final Label score;
    private final Group group;

    public HighScoreGroup(){
        super(new Group(), Core.getCore().getSkin());
        group = (Group) getActor();
        Skin skin = Core.getCore().getSkin();
        names = new Label("",skin);
        score = new Label("",skin);
        group.addActor(names);
        group.addActor(score);

        names.setAlignment(Align.topLeft);
        score.setAlignment(Align.topRight);

        names.setPosition(8,0);
        score.setY(0);

    }

    private HighScoreRow[] readHighScore(){
        FileHandle file = Gdx.files.local("hs.txt");
        String[] hs = file.exists() ? file.readString().split("\n") : new String[0];
        HighScoreRow[] rows = new HighScoreRow[hs.length];
        for (int i = 0; i < hs.length; i++) {
            String[] split = hs[i].split("--");
            rows[i] = new HighScoreRow(split[0], Integer.parseInt(split[1].trim()));
        }
        Arrays.sort(rows, HighScoreRow.comparator);
        return rows;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            HighScoreRow[] rows = readHighScore();
            StringBuilder namesBuilder = new StringBuilder();
            StringBuilder scoreBuilder = new StringBuilder();

            for (HighScoreRow row : rows) {
                namesBuilder.append(row.getName()).append("\n");
                scoreBuilder.append(row.getScore()).append("\n");
            }

            names.setText(namesBuilder.toString());
            score.setText(scoreBuilder.toString());
            group.setSize(getWidth(),names.getPrefHeight()*2*rows.length);
            names.setSize(getWidth()*0.5f - 8, group.getHeight());
            score.setSize(names.getWidth(), group.getHeight());
            score.setX(getWidth() - score.getWidth() - 8);
        }
    }

}
