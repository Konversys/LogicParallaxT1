package com.example.myapplication.model.models.realm;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Coupe extends RealmObject {
    @PrimaryKey
    String id;
    int number;
    Place placeLT;
    Place placeRT;
    Place placeLB;
    Place placeRB;

    public Coupe() {
    }

    public Coupe(String id, int number, Place placeLT, Place placeRT, Place placeLB, Place placeRB) {
        this.id = id;
        this.number = number;
        this.placeLT = placeLT;
        this.placeRT = placeRT;
        this.placeLB = placeLB;
        this.placeRB = placeRB;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Place getPlaceLT() {
        return placeLT;
    }

    public void setPlaceLT(Place placeLT) {
        this.placeLT = placeLT;
    }

    public Place getPlaceRT() {
        return placeRT;
    }

    public void setPlaceRT(Place placeRT) {
        this.placeRT = placeRT;
    }

    public Place getPlaceLB() {
        return placeLB;
    }

    public void setPlaceLB(Place placeLB) {
        this.placeLB = placeLB;
    }

    public Place getPlaceRB() {
        return placeRB;
    }

    public void setPlaceRB(Place placeRB) {
        this.placeRB = placeRB;
    }
}
