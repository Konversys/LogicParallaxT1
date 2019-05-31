package com.example.myapplication.model;

import com.example.myapplication.model.logic.YandexApiDataConverter;
import com.example.myapplication.model.models.plx_link_api.Direction;
import com.example.myapplication.model.models.plx_link_api.Product;
import com.example.myapplication.model.models.realm.Checksum;
import com.example.myapplication.model.models.realm.Flight;
import com.example.myapplication.model.models.realm.Station;
import com.example.myapplication.model.models.yandex_api.station.StationList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmQuery;

public class RealmHandler {
    public static void SaveDirections(List<Direction> directions) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(directions);
        realm.commitTransaction();
        realm.close();
    }

    public static void CleanDirections() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Direction.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    public static void CleanSaveDirections(List<Direction> directions) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Direction.class).findAll().deleteAllFromRealm();
        realm.copyToRealm(directions);
        realm.commitTransaction();
        realm.close();
    }

    public static void CleanSaveChecksum(Checksum checksum) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Checksum.class).findAll().deleteAllFromRealm();
        realm.copyToRealm(checksum);
        realm.commitTransaction();
        realm.close();
    }

    public static void SaveChecksum(Checksum checksum) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(checksum);
        realm.commitTransaction();
        realm.close();
    }

    public static void SaveProducts(List<Product> products) {
        AtomicInteger id = new AtomicInteger(1);
        products.forEach(x -> x.setId(id.getAndIncrement()));
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(products);
        realm.commitTransaction();
        realm.close();
    }

    public static void CleanSaveProducts(List<Product> products) {
        AtomicInteger id = new AtomicInteger(1);
        products.forEach(x -> x.setId(id.getAndIncrement()));
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Product.class).findAll().deleteAllFromRealm();
        realm.copyToRealm(products);
        realm.commitTransaction();
        realm.close();
    }

    public static void AddProduct(Product product) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(product);
        realm.commitTransaction();
        realm.close();
    }

    public static ArrayList<Product> GetProducts() {
        ArrayList<Product> products;
        Realm realm = Realm.getDefaultInstance();
        products = new ArrayList<>(realm.copyFromRealm(realm.where(Product.class).findAll()));
        realm.close();
        return products;
    }

    public static void CleanProducts() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Product.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    public static Checksum GetLastChecksum() {
        Realm realm = Realm.getDefaultInstance();
        Checksum checksum = realm.where(Checksum.class).findAll().last();
        realm.close();
        return checksum;
    }

    public static ArrayList<Checksum> GetChecksums() {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Checksum> checksums = new ArrayList<>(realm.where(Checksum.class).findAll());
        realm.close();
        return checksums;
    }

    public static ArrayList<Direction> GetDirectionsByName(String search) {
        return GetDirectionsByName(search, 0);
    }

    public static ArrayList<Direction> GetDirections(Integer count) {
        ArrayList<Direction> directions;
        Realm realm = Realm.getDefaultInstance();
        if (count <= 0) {
            directions = new ArrayList<>(realm.copyFromRealm(realm.where(Direction.class).findAll()));
        } else {
            directions = new ArrayList<>(realm.copyFromRealm(realm.where(Direction.class).findAll().subList(0, count)));
        }
        realm.close();
        return directions;
    }

    public static ArrayList<Station> GetStations() {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Station> stations = new ArrayList<Station>(realm.copyFromRealm(realm.where(Station.class).findAll()));
        realm.close();
        return stations;
    }

    public static void SaveFlight(Flight flight, StationList stationList) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.where(Flight.class).findAll().deleteAllFromRealm();
            realm.where(Station.class).findAll().deleteAllFromRealm();
            realm.copyToRealm(flight);
            realm.copyToRealm(YandexApiDataConverter.StationYandexToStationRealm(stationList));
            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            if (realm.isInTransaction()) {
                realm.cancelTransaction();
            }
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public static Direction GetDirection(String value) {
        ArrayList<Direction> directions;
        Realm realm = Realm.getDefaultInstance();
        Direction query = realm.where(Direction.class).equalTo("value", value).findFirst();
        Direction direction = realm.copyFromRealm(query);
        realm.close();
        return direction;
    }

    public static ArrayList<Direction> GetDirectionsByName(String search, Integer count) {
        ArrayList<Direction> directions;
        String[] values = search.split(" ");
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Direction> query = realm.where(Direction.class);
        for (String line : values) {
            query = query.contains("name", line).and();
        }
        if (count <= 0) {
            directions = new ArrayList<>(realm.copyFromRealm(query.findAll()));
        } else {
            directions = new ArrayList<>(realm.copyFromRealm(query.findAll()));
            if (directions.size() > count) {
                directions = new ArrayList<>(directions.subList(0, count));
            }
        }
        realm.close();
        return directions;
    }
}