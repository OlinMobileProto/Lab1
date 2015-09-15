package com.frantonlin.grocerylist;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends ListFragment {

    private GroceryAdapter adapter;
    private ArrayList<GroceryItem> items;

    public MainActivityFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        items = new ArrayList<>();
        items.add(new GroceryItem("Cheese"));
        items.add(new GroceryItem("Animal Crackers"));
        adapter = new GroceryAdapter(getActivity(), items);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.edit_item);
        final EditText editText = new EditText(getActivity());
        editText.setText(items.get(position).getName());
        editText.setSelection(editText.getText().length());
        builder.setView(editText)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String editedItem = editText.getText().toString();
                        editItem(editedItem, position);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // cancel
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertDialog.show();
    }

    public void addItem(String newItemName) {
        adapter.add(new GroceryItem(newItemName));
    }

    public void editItem(String editedItemName, int pos) {
        items.get(pos).setName(editedItemName);
        adapter.notifyDataSetChanged();
    }
}
