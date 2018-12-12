package com.example.szapa.shoppinglist.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.szapa.shoppinglist.data.Items.ShoppingItem;
import com.example.szapa.shoppinglist.data.Items.ShoppingItemDao;
import com.example.szapa.shoppinglist.data.Lists.ShoppingList;
import com.example.szapa.shoppinglist.data.Lists.ShoppingListDao;

@Database(
        entities = {ShoppingItem.class, ShoppingList.class},
        version = 1
)
@TypeConverters(value = {ShoppingItem.Category.class})
public abstract class ShoppingDatabase extends RoomDatabase {

    private static ShoppingDatabase INSTANCE;

    public abstract ShoppingItemDao shoppingItemDao();
    public abstract ShoppingListDao shoppingListDao();

    public static ShoppingDatabase getShoppingDatabase(Context context){
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ShoppingDatabase.class,
                    "user-database").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}