package hieunguyen.com.todoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MyAdapter itemsAdapter;
    ListView listView;
    MainService dbService = new MainService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemsAdapter = new MyAdapter(this, dbService);

        updateListHeader();

        listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(itemsAdapter);

        // On tap, allow user to edit task
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                createEditDialog(position).show();
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

        // Remove completed tasks
        clearCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbService.clearCompleted();
                itemsAdapter.setList(dbService.getDataFromDB());
                updateListHeader();
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

    /*
     * Updates the "X To Do" header
     */
    public void updateListHeader() {
        TextView header = (TextView) findViewById(R.id.header);
        String count = itemsAdapter.getCount() == 0 ? "Nothing" : String.valueOf(itemsAdapter.getCount());
        header.setText(count + " To Do");
    }

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
                ToDoItem task = itemsAdapter.getList().get(position);
                task.setText(editText.getText().toString());
                dbService.edit(task);
                itemsAdapter.setList(dbService.getDataFromDB());
                updateListHeader();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
                dbService.delete(itemsAdapter.getList().get(position));
                itemsAdapter.setList(dbService.getDataFromDB());
                updateListHeader();
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
                dbService.addToDB(new ToDoItem(editText.getText().toString()));
                itemsAdapter.setList(dbService.getDataFromDB());
                updateListHeader();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setMessage(R.string.addHeader).setView(editText);

        return addDialogBuilder.create();
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
