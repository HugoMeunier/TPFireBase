package com.example.meuni.tp7;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.meuni.tp7.models.Bottle;

import java.util.List;


@Dao
public interface BottleDao {

    @Query("SELECT * FROM bottle")
    List<Bottle> getAll();

    @Insert
    void insert(Bottle bottle);

    @Delete
    void delete(Bottle bottle);

    @Query("SELECT * FROM bottle WHERE name LIKE :queryName")
    Bottle findByName(String queryName);
}