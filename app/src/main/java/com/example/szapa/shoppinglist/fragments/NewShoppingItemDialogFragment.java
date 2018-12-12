package com.example.szapa.shoppinglist.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.szapa.shoppinglist.ItemsActivity;
import com.example.szapa.shoppinglist.ListsActivity;
import com.example.szapa.shoppinglist.R;
import com.example.szapa.shoppinglist.data.Items.ShoppingItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.szapa.shoppinglist.data.ShoppingDatabase.getShoppingDatabase;

public class NewShoppingItemDialogFragment extends DialogFragment {

    public static final String TAG = "NewShoppingItemDialogFragment";

    public interface NewShoppingItemDialogListener {
        void onShoppingItemCreated(ShoppingItem newItem);
    }

    private NewShoppingItemDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof NewShoppingItemDialogListener) {
            listener = (NewShoppingItemDialogListener) activity;
        } else {
            throw new RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!");
        }
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH)+1;
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        minute = Calendar.getInstance().get(Calendar.MINUTE);
        new AsyncTask<Void,Void, List<String>>(){
            @Override
            protected List<String> doInBackground(Void... voids) {
                return getShoppingDatabase(getContext()).shoppingListDao().getAllName();
            }

            @Override
            protected void onPostExecute(List<String> ss) {
                Log.d("EZTKERESD",ss.get(0));
                listnames = ss;
            }
        }.execute();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.new_shopping_item)
                .setView(getContentView())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isValid()) {
                            listener.onShoppingItemCreated(getShoppingItem());
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private boolean isValid() {
        return nameEditText.getText().length() > 0;
    }

    private ShoppingItem getShoppingItem() {
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.name = nameEditText.getText().toString();
        try {
            shoppingItem.estimatedPrice = Integer.parseInt(estimatedPriceEditText.getText().toString());
        } catch (NumberFormatException e) {
            shoppingItem.estimatedPrice = 0;
        }
        shoppingItem.category = ShoppingItem.Category.getByOrdinal(categorySpinner.getSelectedItemPosition());
        shoppingItem.isBought = alreadyPurchasedCheckBox.isChecked();
        shoppingItem.priority = prioritySeekBar.getProgress();
        shoppingItem.deadline = df.format(calendar.getTime());
        shoppingItem.mylist = ListsActivity.db.shoppingListDao().getIdByName(listChooseSpinner.getSelectedItem().toString());
        return shoppingItem;
    }

    private EditText nameEditText;
    private Spinner categorySpinner;
    private EditText estimatedPriceEditText;
    private CheckBox alreadyPurchasedCheckBox;
    private SeekBar prioritySeekBar;
    private TextView deadlineTextView;
    private Button changeDeadline;
    private Spinner listChooseSpinner;
    private int year,month,day,hour,minute;
    final Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd   HH:mm");
    private List<String> listnames = new ArrayList<String>();

    private View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_shopping_item, null);
        nameEditText = contentView.findViewById(R.id.ShoppingItemNameEditText);
        categorySpinner = contentView.findViewById(R.id.ShoppingItemCategorySpinner);
        categorySpinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.category_items)));
        estimatedPriceEditText = contentView.findViewById(R.id.ShoppingItemEstimatedPriceEditText);
        alreadyPurchasedCheckBox = contentView.findViewById(R.id.ShoppingItemIsPurchasedCheckBox);
        prioritySeekBar = contentView.findViewById(R.id.PrioritySeekBar);
        prioritySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        deadlineTextView = contentView.findViewById(R.id.ShoppingItemDeadlineTextView);
        deadlineTextView.setText(df.format(calendar.getTime()));
        changeDeadline = contentView.findViewById(R.id.ChangeDateButton);
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int h, int m){
                calendar.set(Calendar.HOUR_OF_DAY, h);
                calendar.set(Calendar.MINUTE, m);
                updateTime();
                updateDeadline();
            }
        };

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int y, int m,
                                  int d) {
                calendar.set(Calendar.YEAR, y);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.DAY_OF_MONTH, d);
                updateDate();
                new TimePickerDialog(getContext(), time,
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                        true).show();
            }
        };

        changeDeadline.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new DatePickerDialog(getContext(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Log.d("Spinner element count:",Integer.toString(listnames.size()));
        listChooseSpinner = contentView.findViewById(R.id.ListChooseSpinner);
        listChooseSpinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listnames));

        return contentView;
    }

    private void updateDate(){
        year = calendar.getTime().getYear();
        month = calendar.getTime().getMonth();
        day = calendar.getTime().getDay();
    }
    private void updateTime(){
        hour = calendar.getTime().getHours();
        minute = calendar.getTime().getMinutes();
    }
    private void updateDeadline(){
        deadlineTextView.setText(df.format(calendar.getTime()));
    }
}