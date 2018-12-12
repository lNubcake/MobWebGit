package com.example.szapa.shoppinglist.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.szapa.shoppinglist.R;
import com.example.szapa.shoppinglist.data.Lists.ShoppingList;

public class NewShoppingListDialogFragment extends DialogFragment {

    public static final String TAG = "NewShoppingListDialogFragment";

    public interface NewShoppingListDialogListener{
        void onShoppingListCreated(ShoppingList newList);
    }

    private NewShoppingListDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if(activity instanceof NewShoppingListDialogListener){
            listener = (NewShoppingListDialogListener) activity;
        } else{
            throw new RuntimeException("Activity must implement the NewShoppingListDialogListener interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new AlertDialog.Builder(requireContext())
                .setTitle("New List")
                .setView(getContentView())
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        if(isValid()){
                            listener.onShoppingListCreated(getShoppingList());
                        }
                    }
                })
                .setNegativeButton("Cancel",null)
                .create();
    }

    private boolean isValid(){return nameEditText.getText().length()>0;}

    private ShoppingList getShoppingList(){
       ShoppingList shoppingList = new ShoppingList();
       shoppingList.name = nameEditText.getText().toString();

       shoppingList.items = 0;
       shoppingList.sumPrice = 0;
       // TODO figure out the rest
       return shoppingList;
    }

    private EditText nameEditText;

    private View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_shopping_list, null);
        nameEditText = contentView.findViewById(R.id.ShoppingListNameEditText);
        // TODO figure out the rest
        return contentView;
    }
}
