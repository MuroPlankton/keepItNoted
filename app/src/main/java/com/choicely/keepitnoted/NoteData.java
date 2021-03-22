package com.choicely.keepitnoted;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NoteData extends RealmObject {
    @PrimaryKey
    private String id;
    private String title, text;
    private int color;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
