package com.example.meuni.tp7.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "bottle")
public class Bottle implements Serializable{

    @PrimaryKey
    @NonNull
    private String name;
    private float price;

    public Bottle() {
    }

    @Ignore
    public Bottle(float price, String name){
        this.price = price;
        this.name =name;
    }

    @Override
    public String toString() {
        return name + '\'' + price + "€";
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }


}
