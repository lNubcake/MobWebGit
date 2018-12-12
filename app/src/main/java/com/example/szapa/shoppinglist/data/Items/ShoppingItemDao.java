package com.example.szapa.shoppinglist.data.Items;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ShoppingItemDao {
    @Query("SELECT * FROM shoppingitem")
    List<ShoppingItem> getAll();

    @Insert
    long insert(ShoppingItem shoppingItems);

    @Update
    void update(ShoppingItem shoppingItem);

    @Delete
    void delete(ShoppingItem shoppingItem);

}