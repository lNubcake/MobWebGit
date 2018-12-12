package com.example.szapa.shoppinglist.data.Lists;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ShoppingListDao {
    @Query("SELECT * FROM shoppinglist")
    List<ShoppingList> getAll();

    @Insert
    long insert(ShoppingList list);

    @Update
    void update(ShoppingList list);

    @Delete
    void delete(ShoppingList list);

    @Query("SELECT name FROM shoppinglist")
    List<String> getAllName();

    @Query("SELECT listid FROM shoppingList WHERE name = :nameSelected LIMIT 1")
    Long getIdByName(String nameSelected);
}
