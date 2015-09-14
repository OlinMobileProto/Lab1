package com.frantonlin.grocerylist;

import android.app.ListFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends ListFragment {

    private GroceryAdapter adapter;
    private ArrayList<String> items;

    public MainActivityFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        items = new ArrayList<String>();
        items.add("Cheese");
        items.add("Animal Crackers");
        adapter = new GroceryAdapter(getActivity(), items);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO implement some logic
    }

    public void addItem(String newItem) {
        adapter.add(newItem);
    }
}
