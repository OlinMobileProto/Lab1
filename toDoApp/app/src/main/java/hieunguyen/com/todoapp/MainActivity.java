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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items = new ArrayList<>();
    ArrayAdapter<String> itemsAdapter;
    ListView listView;

    public AlertDialog createEditDialog(String message, int positiveButton, int negativeButton, final int position) {
        AlertDialog.Builder editDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        final EditText editText = new EditText(MainActivity.this);
        String currentItem = items.get(position);
        editText.setText(currentItem);
        editText.setSelection(currentItem.length());
        editDialogBuilder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                items.set(position, editText.getText().toString());
                Log.d("Changed", items.toString());
                itemsAdapter.notifyDataSetChanged();
            }

        }).setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Cancel", "cancel");
            }

        }).setMessage(message).setView(editText);
        return editDialogBuilder.create();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items.add("Buy apples");
        items.add("Yell at cashier");

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("item", itemsAdapter.getItem(position));
                createEditDialog("Edit your note", R.string.confirmEdit, R.string.cancelEdit, position).show();
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
