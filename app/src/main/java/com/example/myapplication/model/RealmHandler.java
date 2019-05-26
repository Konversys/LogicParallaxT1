package com.example.myapplication.model;

import com.example.myapplication.model.models.plx_link_api.Direction;
import com.example.myapplication.model.models.realm.Checksum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHandler {
    public static void SaveDirections(List<Direction> directions)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(directions);
        realm.commitTransaction();
        realm.close();
    }

    public static void CleanSaveDirections(List<Direction> directions)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Direction.class).findAll().deleteAllFromRealm();
        realm.copyToRealm(directions);
        realm.commitTransaction();
        realm.close();
    }

    public static void CleanSaveChecksum(Checksum checksum)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Checksum.class).findAll().deleteAllFromRealm();
        realm.copyToRealm(checksum);
        realm.commitTransaction();
        realm.close();
    }

    public static void SaveChecksum(Checksum checksum)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(checksum);
        realm.commitTransaction();
        realm.close();
    }

    public static Checksum GetLastChecksum()
    {
        Realm realm = Realm.getDefaultInstance();
        Checksum checksum = realm.where(Checksum.class).findAll().last();
        realm.close();
        return checksum;
    }

    public static ArrayList<Checksum> GetChecksums()
    {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Checksum> checksums = new ArrayList<>(realm.where(Checksum.class).findAll());
        realm.close();
        return checksums;
    }

    public static ArrayList<Direction> GetDirectionsByName(String search)
    {
        return  GetDirectionsByName(search, 0);
    }

    public static ArrayList<Direction> GetDirections(Integer count)
    {
        ArrayList<Direction> directions;
        Realm realm = Realm.getDefaultInstance();
        if(count <= 0){
            directions = new ArrayList<>(realm.copyFromRealm(realm.where(Direction.class).findAll()));
        }
        else {
            directions = new ArrayList<>(realm.copyFromRealm(realm.where(Direction.class).findAll().subList(0, count)));
        }
        realm.close();
        return directions;
    }

    public static Direction GetDirection(String value)
    {
        ArrayList<Direction> directions;
        Realm realm = Realm.getDefaultInstance();
        Direction query = realm.where(Direction.class).equalTo("value", value).findFirst();
        Direction direction = realm.copyFromRealm(query);
        realm.close();
        return direction;
    }

    public static ArrayList<Direction> GetDirectionsByName(String search, Integer count)
    {
        ArrayList<Direction> directions;
        String[] values = search.split(" ");
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Direction> query = realm.where(Direction.class);
        for (String line: values) {
            query = query.contains("name", line).and();
        }
        if(count <= 0){
            directions = new ArrayList<>(realm.copyFromRealm(query.findAll()));
        }
        else {
            directions = new ArrayList<>(realm.copyFromRealm(query.findAll()));
            if (directions.size() > count){
                directions =  new ArrayList<>(directions.subList(0, count));
            }
        }
        realm.close();
        return directions;
    }
}