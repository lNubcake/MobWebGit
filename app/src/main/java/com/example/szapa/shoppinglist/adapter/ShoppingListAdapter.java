package com.example.szapa.shoppinglist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.szapa.shoppinglist.R;
import com.example.szapa.shoppinglist.data.Lists.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter
    extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingViewHolder> {

    private final List<ShoppingList> lists;

    private ShoppingListClickListener listener;

    public ShoppingListAdapter(ShoppingListClickListener listener){
        this.listener = listener;
        lists = new ArrayList<>();
    }

    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View listView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.all_lists, parent, false);
        return new ShoppingViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position){
        ShoppingList list = lists.get(position);
        holder.nameTextView.setText(list.name);
        holder.sumPriceTextView.setText(Integer.toString(list.sumPrice));
        holder.numOfItemsTextView.setText(Integer.toString(list.items));
    }

    public void addList(ShoppingList list){
        lists.add(list);
        notifyItemInserted(lists.size()-1);
    }

    public void update(List<ShoppingList> shoppingLists){
        lists.clear();
        lists.addAll(shoppingLists);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){return lists.size();}

    public interface ShoppingListClickListener{
        void onListChanged(ShoppingList list);
        void onListDeleted(ShoppingList list);
    }
    class ShoppingViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView sumPriceTextView;
        TextView numOfItemsTextView;

        ShoppingList list;

        ShoppingViewHolder(View listView){
            super(listView);
            nameTextView = listView.findViewById(R.id.ShoppingListNameTextView);
            sumPriceTextView = listView.findViewById(R.id.ShoppingListSumPriceTextView);
            numOfItemsTextView = listView.findViewById(R.id.ShoppingListNumOfItemsTextView);
        }
    }
}
