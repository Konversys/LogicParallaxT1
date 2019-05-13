package com.example.myapplication.model;

import com.example.myapplication.model.models.plx_link_api.Direction;
import com.example.myapplication.model.models.realm.Checksum;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;

public class RealmHandler {
    static void SaveDirections(ArrayList<Direction> directions)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(directions);
        realm.commitTransaction();
        realm.close();
    }

    static void CleanSaveDirections(ArrayList<Direction> directions)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Direction.class).findAll().deleteAllFromRealm();
        realm.copyToRealm(directions);
        realm.commitTransaction();
        realm.close();
    }

    static void CleanSaveChecksum(Checksum checksum)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Checksum.class).findAll().deleteAllFromRealm();
        realm.copyToRealm(checksum);
        realm.commitTransaction();
        realm.close();
    }

    static void SaveChecksum(Checksum checksum)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(checksum);
        realm.commitTransaction();
        realm.close();
    }

    static Checksum GetLastChecksum()
    {
        Realm realm = Realm.getDefaultInstance();
        Checksum checksum = realm.where(Checksum.class).findAll().last();
        realm.close();
        return checksum;
    }

    static ArrayList<Checksum> GetChecksums()
    {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Checksum> checksums = new ArrayList<>(realm.where(Checksum.class).findAll());
        realm.close();
        return checksums;
    }

    static ArrayList<Direction> GetDirectionsByName(String search)
    {
        String[] values = search.split(" ");
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Direction> query = realm.where(Direction.class);
        for (String line: values) {
            query = query.contains("name", line).and();
        }
        ArrayList<Direction> directions = new ArrayList<>(query.findAll());
        realm.close();
        return directions;
    }
}