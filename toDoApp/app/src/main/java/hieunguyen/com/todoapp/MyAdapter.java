package hieunguyen.com.todoapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by hieunguyen on 9/11/15.
 */
public class MyAdapter extends ArrayAdapter<ToDoItem> {

    private final Context context;
    private ArrayList<ToDoItem> itemList;
    private MainService dbService;
    private LayoutInflater myInflater = null;

    public MyAdapter(Context context, MainService service) {
        super(context, R.layout.target_item, service.getDataFromDB());
        this.context = context;
        this.itemList = service.getDataFromDB();
        this.dbService = service;
        this.myInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final ListViewHolder viewHolder;

        if (v == null) {
            // If convertView is null, then the view is new and it is inflated and held by a new
            // viewHolder
            v = myInflater.inflate(R.layout.target_item, null);
            viewHolder = new ListViewHolder(v);
            v.setTag(viewHolder);
        } else {
            // Else just retrieve the view-holder
            viewHolder = (ListViewHolder) v.getTag();
        }

        final ToDoItem task = itemList.get(position);

        // Setting attributes of the elements of the view
        viewHolder.taskText.setText(task.getText());

        viewHolder.checkbox.setChecked(task.getCompleted());
        viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("completeUpdate", task.getText() + ": "
//                        + String.valueOf(viewHolder.checkbox.isChecked()));
                task.setCompleted(viewHolder.checkbox.isChecked());
                dbService.edit(task);
            }
        });

        return v;

    }

    public ArrayList<ToDoItem> getList() {
        return itemList;
    }

    public void setList(ArrayList<ToDoItem> newList) {
        this.itemList = newList;
        this.notifyDataSetChanged();
        //Log.d("newList", this.itemList.toString());
    }

}



