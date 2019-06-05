package com.example.myapplication.model.models.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Inventory extends RealmObject {
    @PrimaryKey
    String title;

    public Inventory() {
    }

    public Inventory(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
