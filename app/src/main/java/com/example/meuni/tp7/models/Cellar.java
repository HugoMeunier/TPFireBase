package com.example.meuni.tp7.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Cellar {
    private static final float EQUIVALENCE_DOLLARD_EUROS = (float) 0.85;

    @SerializedName("listBottle")
    @Expose
    private List<Bottle> listBottle ;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;


    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Cellar(){
        listBottle = new ArrayList<>();
        ownerName = "";
        }

    public void addBottle(Bottle bottle){
        listBottle.add(bottle);
    }

    Bottle getBottle(String name){
        for (Bottle s : listBottle) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        Bottle bottleFind = new Bottle(-1, "n'existe pas");
        return  bottleFind;
    }

    float getTotalPriceInEuros(){
        float amount =0;
        if (listBottle.isEmpty()){
            return 0;
        }
        else {
            for (Bottle s : listBottle){
                amount = amount + s.getPrice();
            }
        }
        return amount;
    }

    float getTotalPriceInDollars(){
        float amount =0;
        if (listBottle.isEmpty()){
            return 0;
        }
        else {
            for (Bottle s : listBottle){
                amount = amount + s.getPrice() *EQUIVALENCE_DOLLARD_EUROS;
            }
        }
        return amount;
    }

    public List<Bottle> getListBottle() {
        return listBottle;
    }

    public void setListBottle(List<Bottle> listBottle) {
        this.listBottle = listBottle;
    }

    int getNumberOfBottles(){
        return listBottle.size();
    }

    @Override
    public String toString() {
        return "Cellar{" +
                "listBottle=" + listBottle +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }
}