package hieunguyen.com.todoapp;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by hieunguyen on 9/15/15.
 * Utility class to improve performance of ListView by storing a used view using the ViewHolder
 * pattern
 */
public class ListViewHolder {

    public TextView taskText;
    public CheckBox checkbox;

    public ListViewHolder(View base) {
        checkbox = (CheckBox) base.findViewById(R.id.checkbox);
        taskText = (TextView) base.findViewById(R.id.toDoItem);
    }
}
