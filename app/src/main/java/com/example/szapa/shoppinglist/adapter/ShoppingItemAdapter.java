package com.example.szapa.shoppinglist.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.szapa.shoppinglist.R;
import com.example.szapa.shoppinglist.data.Items.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingItemAdapter
        extends RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder> {

    private final List<ShoppingItem> items;

    private ShoppingItemClickListener listener;

    public ShoppingItemAdapter(ShoppingItemClickListener listener) {
        this.listener = listener;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.all_items, parent, false);
        return new ShoppingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position) {
        ShoppingItem item = items.get(position);
        holder.nameTextView.setText(item.name);
        holder.deadlineTextView.setText(item.deadline);
        holder.categoryTextView.setText(item.category.name());
        holder.priceTextView.setText(item.estimatedPrice + " Ft");
        holder.iconImageView.setImageResource(getImageResource(item.category));
        holder.isBoughtCheckBox.setChecked(item.isBought);

        holder.item = item;
    }

    private @DrawableRes
    int getImageResource(ShoppingItem.Category category) {
        @DrawableRes int ret;
        switch (category) {
            case ENTERTAINMENT:
                ret = R.drawable.entertainment;
                break;
            case FOOD:
                ret = R.drawable.food;
                break;
            case TRANSPORT:
                ret = R.drawable.transport;
                break;
            case BILL:
                ret = R.drawable.bill;
                break;
            case CAR:
                ret = R.drawable.car;
                break;
            case SPORT:
                ret = R.drawable.sport;
                break;
            case HEALTH:
                ret = R.drawable.health;
                break;
            case TOILETRY:
                ret = R.drawable.toiletry;
                break;
            case HOUSE:
                ret = R.drawable.house;
                break;
            case GIFT:
                ret = R.drawable.gift;
                break;
            default:
                ret = 0;
        }
        return ret;
    }

    public void addItem(ShoppingItem item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void update(List<ShoppingItem> shoppingItems) {
        items.clear();
        items.addAll(shoppingItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface ShoppingItemClickListener{
        void onItemChanged(ShoppingItem item);
        void onItemDeleted(ShoppingItem item);
    }

    class ShoppingViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImageView;
        TextView nameTextView;
        TextView categoryTextView;
        TextView priceTextView;
        TextView deadlineTextView;
        CheckBox isBoughtCheckBox;
        ImageButton removeButton;

        ShoppingItem item;

        ShoppingViewHolder(View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.ShoppingItemIconImageView);
            nameTextView = itemView.findViewById(R.id.ShoppingItemNameTextView);
            categoryTextView = itemView.findViewById(R.id.ShoppingItemCategoryTextView);
            priceTextView = itemView.findViewById(R.id.ShoppingItemPriceTextView);
            deadlineTextView = itemView.findViewById(R.id.ShoppingItemDeadlineTextView);
            isBoughtCheckBox = itemView.findViewById(R.id.ShoppingItemIsBoughtCheckBox);
            removeButton = itemView.findViewById(R.id.ShoppingItemRemoveButton);

            isBoughtCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                    if (item != null) {
                        item.isBought = isChecked;
                        listener.onItemChanged(item);
                    }
                }
            });

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemDeleted(item);
                }
            });
        }
    }
}