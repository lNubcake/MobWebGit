package com.example.szapa.shoppinglist;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.szapa.shoppinglist.adapter.ShoppingListAdapter;
import com.example.szapa.shoppinglist.data.Lists.ShoppingList;
import com.example.szapa.shoppinglist.data.ShoppingDatabase;
import com.example.szapa.shoppinglist.fragments.NewShoppingListDialogFragment;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.List;

public class ListsActivity extends AppCompatActivity
    implements ShoppingListAdapter.ShoppingListClickListener,
    NewShoppingListDialogFragment.NewShoppingListDialogListener{

    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemLists;
    private ResideMenuItem itemItems;
    private ResideMenuItem itemBack;

    private RecyclerView recyclerView;
    private ShoppingListAdapter adapter;

    public static ShoppingDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        getSupportActionBar().hide();

        setUpResideMenu();

        FloatingActionButton fab = findViewById(R.id.list_fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new NewShoppingListDialogFragment().show(getSupportFragmentManager(), NewShoppingListDialogFragment.TAG);
            }
        });

        db = Room.databaseBuilder(getApplicationContext(),ShoppingDatabase.class, "shopping-lists").build();
        initRecyclerView();
    }

    public void onShoppingListCreated(final ShoppingList newList){
        new AsyncTask<Void,Void, ShoppingList>(){
            @Override
            protected ShoppingList doInBackground(Void... voids) {
                newList.listid = db.shoppingListDao().insert(newList);
                return newList;
            }

            @Override
            protected void onPostExecute(ShoppingList shoppingItem) {
                adapter.addList(shoppingItem);
            }
        }.execute();
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.MainRecyclerView);
        adapter = new ShoppingListAdapter(this);
        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadItemsInBackground(){
        new AsyncTask<Void, Void, List<ShoppingList>>() {

            @Override
            protected List<ShoppingList> doInBackground(Void... voids) {
                return db.shoppingListDao().getAll();
            }

            @Override
            protected void onPostExecute(List<ShoppingList> shoppingItems) {
                adapter.update(shoppingItems);
            }
        }.execute();
    }

    public void onListChanged(final ShoppingList list){
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                db.shoppingListDao().update(list);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("MainActivity", "ShoppingItem update was successful");
            }
        }.execute();
    }

    public void onListDeleted(final ShoppingList list){
        new AsyncTask<Void, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(Void... voids){
                db.shoppingListDao().delete(list);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful){
                Log.d("Activity","ShoppingItem delete was succesful");
            }
        }.execute();
        loadItemsInBackground();
    }

    protected void setUpResideMenu(){
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);

        itemHome = new ResideMenuItem(this,R.drawable.icon_home, "Home");
        itemHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        resideMenu.addMenuItem(itemHome,ResideMenu.DIRECTION_LEFT);

        itemLists = new ResideMenuItem(this,R.drawable.icon_lists, "All Lists");
        itemLists.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                resideMenu.closeMenu();
            }
        });
        resideMenu.addMenuItem(itemLists,ResideMenu.DIRECTION_LEFT);

        itemItems = new ResideMenuItem(this,R.drawable.icon_items, "All Items");
        itemItems.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent getItemsIntent= new Intent(ListsActivity.this, ItemsActivity.class);
                resideMenu.closeMenu();
                startActivity(getItemsIntent);
                finish();
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
    public boolean dispatchTouchEvent(MotionEvent ev){
        return resideMenu.dispatchTouchEvent(ev);
    }
}
