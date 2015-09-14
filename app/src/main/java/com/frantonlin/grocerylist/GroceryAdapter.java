package com.frantonlin.grocerylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by franton on 9/13/15.
 */
public class GroceryAdapter extends ArrayAdapter<String> {
    private ArrayList<String> items;

    public GroceryAdapter(Context context, ArrayList<String> items) {
        super(context, 0, items);
        this.items = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grocery_item, parent, false);
        }
        // Lookup view for data population
        TextView list_item = (TextView) convertView.findViewById(R.id.list_item);
        ImageView delete_icon = (ImageView) convertView.findViewById(R.id.delete_icon);
        // Populate the data into the template view using the data object
        list_item.setText(item);
        delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
