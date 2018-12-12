package com.example.szapa.shoppinglist.data.Lists;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "shoppinglist")
public class ShoppingList {

    @ColumnInfo(name = "listid")
    @PrimaryKey(autoGenerate = true)
    public Long listid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "sumprice")
    public int sumPrice;

    @ColumnInfo(name = "items")
    public int items;
}
