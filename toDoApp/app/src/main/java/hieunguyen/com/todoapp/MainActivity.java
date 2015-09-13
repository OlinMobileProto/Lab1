package hieunguyen.com.todoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<ToDoItem> items = new ArrayList<>();
    MyAdapter itemsAdapter;
    ListView listView;

    /*
     * Creates dialog for editing a task
     */
    public AlertDialog createEditDialog(final int position) {
        String currentItem = itemsAdapter.getList().get(position).getText();

        AlertDialog.Builder editDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        final EditText editText = new EditText(MainActivity.this);
        editText.setText(currentItem);
        editText.setSelection(currentItem.length());

        editDialogBuilder.setPositiveButton(R.string.confirmEdit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemsAdapter.getList().get(position).setText(editText.getText().toString());
                itemsAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        }).setMessage(R.string.editHeader).setView(editText);

        return editDialogBuilder.create();
    }

    /*
     * Creates dialog for deleting a task
     */
    public AlertDialog createDeleteDialog(final int position) {
        AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        deleteDialogBuilder.setPositiveButton(R.string.confirmDelete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemsAdapter.getList().remove(position);
                itemsAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setMessage("Delete \"" + itemsAdapter.getList().get(position).getText() + "\"?");
        return deleteDialogBuilder.create();
    }

    /*
     * Creates dialog for adding a task
     */
    public AlertDialog createAddDialog() {
        final EditText editText = new EditText(MainActivity.this);

        AlertDialog.Builder addDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        addDialogBuilder.setPositiveButton(R.string.confirmAdd, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemsAdapter.getList().add(new ToDoItem(editText.getText().toString(), false));
                itemsAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        }).setMessage(R.string.addHeader).setView(editText);

        return addDialogBuilder.create();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting the header
        items.add(new ToDoItem("To Do", true));

        // Adding items beforehand
        items.add(new ToDoItem("Buy apples", false));
        items.add(new ToDoItem("Buy chips", false));
        items.add(new ToDoItem("Buy beer", false));
        items.add(new ToDoItem("Party hard", false));

        itemsAdapter = new MyAdapter(this, items);

        listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(itemsAdapter);

        // On tap, allow user to edit task
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!itemsAdapter.getList().get(position).getIsHeader()) {
                    createEditDialog(position).show();
                }
            }
        });

        // On long hold, allow user to delete task
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                createDeleteDialog(position).show();
                return true;
            }
        });

        Button clearCompleted = (Button) findViewById(R.id.clearCompleted);
        Button addTask = (Button) findViewById(R.id.addNew);

        // Remove checkboxed (completed) tasks
        clearCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Iterator<ToDoItem> iterator = itemsAdapter.getList().iterator(); iterator.hasNext();) {
                    ToDoItem item = iterator.next();
                    if (item.getCompleted()) {
                        iterator.remove();
                    }
                }
                itemsAdapter.notifyDataSetChanged();
            }
        });

        // On tap, allow user to add task
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAddDialog().show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
