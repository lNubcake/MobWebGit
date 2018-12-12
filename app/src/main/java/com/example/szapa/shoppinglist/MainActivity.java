package com.example.szapa.shoppinglist;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.szapa.shoppinglist.adapter.ShoppingItemAdapter;
import com.example.szapa.shoppinglist.data.Items.ShoppingItem;
import com.example.szapa.shoppinglist.data.ShoppingDatabase;
import com.example.szapa.shoppinglist.fragments.NewShoppingItemDialogFragment;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity
    /*implements ShoppingItemAdapter.ShoppingItemClickListener,
               NewShoppingItemDialogFragment.NewShoppingItemDialogListener*/{

    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemLists;
    private ResideMenuItem itemItems;
    private ResideMenuItem itemBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        setUpResideMenu();

        //itemdb = Room.databaseBuilder(getApplicationContext(),ShoppingItemDatabase.class,"shopping-items").build();
        //initRecyclerView();
    }

    /*@Override
    public void onShoppingItemCreated(final ShoppingItem newItem) {
        new AsyncTask<Void, Void, ShoppingItem>() {

            @Override
            protected ShoppingItem doInBackground(Void... voids) {
                newItem.id = itemdb.shoppingItemDao().insert(newItem);
                return newItem;
            }

            @Override
            protected void onPostExecute(ShoppingItem shoppingItem) {
                adapter.addItem(shoppingItem);
            }
        }.execute();
    }*/
/*
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.MainRecyclerView);
        adapter = new ShoppingItemAdapter(this);
        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
*//*
    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<ShoppingItem>>() {

            @Override
            protected List<ShoppingItem> doInBackground(Void... voids) {
                return itemdb.shoppingItemDao().getAll();
            }

            @Override
            protected void onPostExecute(List<ShoppingItem> shoppingItems) {
                adapter.update(shoppingItems);
            }
        }.execute();
    }
*//*
    @Override
    public void onItemChanged(final ShoppingItem item) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                itemdb.shoppingItemDao().update(item);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("MainActivity", "ShoppingItem update was successful");
            }
        }.execute();
    }
*//*
    @Override
    public void onItemDeleted(final ShoppingItem item){
        new AsyncTask<Void, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(Void... voids){
                itemdb.shoppingItemDao().delete(item);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful){
                Log.d("Activity","ShoppingItem delete was succesful");
            }
        }.execute();
        loadItemsInBackground();
    }
*/
    protected void setUpResideMenu(){
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);

        itemHome = new ResideMenuItem(this,R.drawable.icon_home, "Home");
        itemHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                   resideMenu.closeMenu();
            }
        });
        resideMenu.addMenuItem(itemHome,ResideMenu.DIRECTION_LEFT);

        itemLists = new ResideMenuItem(this,R.drawable.icon_lists, "All Lists");
        itemLists.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent getListsIntent = new Intent();
                getListsIntent.setClass(MainActivity.this, ListsActivity.class);
                startActivity(getListsIntent);
                resideMenu.closeMenu();
            }
        });
        resideMenu.addMenuItem(itemLists,ResideMenu.DIRECTION_LEFT);

        itemItems = new ResideMenuItem(this,R.drawable.icon_items, "All Items");
        itemItems.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent getItemsIntent = new Intent();
                getItemsIntent.setClass(MainActivity.this,ItemsActivity.class);
                startActivity(getItemsIntent);
                resideMenu.closeMenu();
            }
        });
        resideMenu.addMenuItem(itemItems,ResideMenu.DIRECTION_LEFT);

        itemBack = new ResideMenuItem(this,R.drawable.icon_back, "Back");
        itemBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                resideMenu.closeMenu();
            }
        });
        resideMenu.addMenuItem(itemBack,ResideMenu.DIRECTION_LEFT);

        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

}
