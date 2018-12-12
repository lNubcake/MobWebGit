package com.example.szapa.shoppinglist.data.Items;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

import com.example.szapa.shoppinglist.data.Lists.ShoppingList;

@Entity(tableName = "shoppingitem",
    foreignKeys = @ForeignKey(entity = ShoppingList.class,
            parentColumns = "listid",
            childColumns = "mylist",
            onDelete = ForeignKey.CASCADE))
public class ShoppingItem {
    public enum Category {
        ENTERTAINMENT,FOOD,TRANSPORT,BILL,CAR,SPORT,HEALTH,TOILETRY,HOUSE,GIFT;

        @TypeConverter
        public static Category getByOrdinal(int ordinal) {
            Category ret = null;
            for (Category cat : Category.values()) {
                if (cat.ordinal() == ordinal) {
                    ret = cat;
                    break;
                }
            }
            return ret;
        }

        @TypeConverter
        public static int toInt(Category category) {
            return category.ordinal();
        }
    }

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "mylist")
    public Long mylist;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "category")
    public Category category;

    @ColumnInfo(name="estimated_price")
    public int estimatedPrice;

    @ColumnInfo(name = "is_bought")
    public boolean isBought;

    @ColumnInfo(name = "priority")
    public int priority;

    @ColumnInfo(name = "deadline")
    public String deadline;
}