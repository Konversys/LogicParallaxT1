package com.example.myapplication.model.models.realm;

import com.example.myapplication.model.models.plx_link_api.Product;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SellProduct extends RealmObject {
    @PrimaryKey
    int id;
    String title;
    String category;
    String count;
    String about;
    double price;
    int total;
    int sold;

    public SellProduct(int id, String title, String category, String count, String about, double price, int total, int sold) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.count = count;
        this.about = about;
        this.price = price;
        this.total = total;
        this.sold = sold;
    }

    public SellProduct() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
}
