package hieunguyen.com.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hieunguyen on 9/11/15.
 */
public class MyAdapter extends ArrayAdapter<ToDoItem> {

    private final Context context;
    private ArrayList<ToDoItem> itemList;

    public MyAdapter(Context context, ArrayList<ToDoItem> itemList) {
        super(context, R.layout.target_item, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView;
        if (!itemList.get(position).getIsHeader()) {
            rowView = inflater.inflate(R.layout.target_item, parent, false);

            // 3. Get and set content/functionality for text and checkbox
            TextView text = (TextView) rowView.findViewById(R.id.toDoItem);
            text.setText(itemList.get(position).getText());

            final CheckBox checkbox = (CheckBox) rowView.findViewById(R.id.checkbox);
            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkbox.isChecked()) {
                        itemList.get(position).setCompleted(true);
                    } else {
                        itemList.get(position).setCompleted(false);
                    }
                }
            });

        } else {
            rowView = inflater.inflate(R.layout.group_header_item, parent, false);
            TextView title = (TextView) rowView.findViewById(R.id.header);
            String count = (itemList.size() == 1 ? "Nothing" : String.valueOf(itemList.size() - 1));
            title.setText(count + ' ' + itemList.get(position).getText());
        }

        return rowView;

    }

    public ArrayList<ToDoItem> getList() {
        return itemList;
    }

    public void setList(ArrayList<ToDoItem> items) {
        this.itemList = items;
    }

}



