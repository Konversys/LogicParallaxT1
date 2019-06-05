package com.example.myapplication.model.models.realm;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Wagon extends RealmObject {
    @PrimaryKey
    String id;
    private int number;
    private int factory;
    RealmList<Coupe> coupes;

    public Wagon() {
    }

    public Wagon(String id, int number, int factory, RealmList<Coupe> coupes) {
        this.id = id;
        this.number = number;
        this.factory = factory;
        this.coupes = coupes;
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

    public int getFactory() {
        return factory;
    }

    public void setFactory(int factory) {
        this.factory = factory;
    }

    public RealmList<Coupe> getCoupes() {
        return coupes;
    }

    public void setCoupes(RealmList<Coupe> coupes) {
        this.coupes = coupes;
    }

    public void addCoupes(Coupe coupe){
        this.coupes.add(coupe);
    }
}
