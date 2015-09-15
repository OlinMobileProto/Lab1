package com.mobileproto.jovanduy.lab1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jordan on 9/13/15.
 */
public class NewAdapter extends ArrayAdapter<Item> {


    public NewAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the item from this position
        final Item item = getItem(position);
        // check if a view is being used, else inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        // Lookup the view to populate items
        TextView tvText = (TextView) convertView.findViewById(R.id.tvText);
        // Populate the data into the template view using the data object
        tvText.setText(item.text);

        // Return completed view to render on screen
        return convertView;
    }

}
