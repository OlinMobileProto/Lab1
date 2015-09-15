package com.mobileproto.jovanduy.lab1;

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

public class MainActivity extends AppCompatActivity {

    public NewAdapter itemsAdapter;
    public ArrayList<Item> items;
    public ListView listView;
    public Button addButton;

    /**
     * Returns AlertDialog used for editing items
     * @param position position of item to be edited
     * @return AlertDialog
     */
    public AlertDialog openEditor(final int position) {
        // item at position
        String item = itemsAdapter.getItem(position).getText();
        // build the AlertDialog
        AlertDialog.Builder editBuilder = new AlertDialog.Builder(this);
        editBuilder.setTitle("Grocery Item");
        editBuilder.setMessage("Edit item:");

        // have editable text field, default is item's current text
        final EditText editText = new EditText(this);
        editText.setText(item);
        editText.setSelection(item.length());
        editBuilder.setView(editText);

        // change the text of item when positive button is clicked
        editBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemsAdapter.getItem(position).setText(editText.getText().toString());
                itemsAdapter.notifyDataSetChanged();
            }
        });

        // do nothing when negative button is clicked
        editBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        return editBuilder.create();
    }

    /**
     * Returns AlertDialog used for deleting items
     * @param position position of item to be deleted
     * @return AlertDialog
     */
    public AlertDialog openDeleter(final int position) {
        // item at position
        Item item = itemsAdapter.getItem(position);
        // build the AlertDialog
        AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(this);
        deleteBuilder.setTitle("Delete this entry?");
        deleteBuilder.setMessage(item.getText());

        // delete item when positive button is clicked
        deleteBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
            }
        });

        // do nothing when negative button is clicked
        deleteBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        return deleteBuilder.create();
    }

    /**
     * Returns AlertDialog to add an item
     * @return AlertDialog
     */
    public AlertDialog openAdder() {
        // build the AlertDialog
        AlertDialog.Builder addBuilder = new AlertDialog.Builder(this);
        addBuilder.setTitle("Add item");
        addBuilder.setMessage("Name of new item:");

        // have editable text field to specify new item
        final EditText editText = new EditText(this);
        addBuilder.setView(editText);

        // add item when positive button is clicked
        addBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                items.add(new Item(editText.getText().toString(), false));
                itemsAdapter.notifyDataSetChanged();
            }
        });

        // do nothing when negative button is clicked
        addBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        return addBuilder.create();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<Item>();
        itemsAdapter = new NewAdapter(this, items);

        listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(itemsAdapter);
        // edit item when clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openEditor(position).show();
            }
        });
        // delete item when long clicked
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                openDeleter(position).show();
                return true;
            }
        });

        // button for adding new items
        addButton = (Button) findViewById(R.id.addButton);
        // open AlertDialog to add item when button is clicked
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdder().show();
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
