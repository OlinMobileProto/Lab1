package com.mtruehle.grocerylist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> itemsToBuy;
    private ArrayAdapter<String> itemsAdapter; // referred to by class methods.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // starts up the main activity, defines necessary parameters like itemsToBuy, starts up the ListView, and adds Lucky Charms to make testing stuff out slightly quicker.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemsToBuy = new ArrayList<String>();
        itemsToBuy.add("Lucky Charms"); // they're magically delicious.
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemsToBuy);
        ListView itemListView = (ListView) findViewById(R.id.groceryList);
        itemListView.setAdapter(itemsAdapter);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                handleListClick(view, position); // separating the dialog and handling for clicking, mainly for ease of reading and debugging.
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                AlertDialog.Builder areYouSure = new AlertDialog.Builder(this);
                areYouSure.setTitle(R.string.are_you_sure);
                areYouSure.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemsToBuy.clear();
                        itemsAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, R.string.cleared, Toast.LENGTH_SHORT).show();
                    }
                });
                areYouSure.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // toast here? or is that too much feedback?
                    }
                });
                AlertDialog aD = areYouSure.create();
                aD.show();
                return true;
            default:
                return true;
        }
    }



    public void handleListClick(View view, int position) {
        // When an item on a list is clicked, its position is passed here and an AlertDialog is used to ask whether it's being edited, deleted, etc.
        AlertDialog.Builder editOrDelete = new AlertDialog.Builder(this);
        final int pos = position;
        editOrDelete.setTitle(R.string.edit_or_delete);
        editOrDelete.setMessage(itemsToBuy.get(position));
        editOrDelete.setPositiveButton(R.string.delete_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemsToBuy.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, R.string.deleted, Toast.LENGTH_SHORT).show();
            }
        });
        editOrDelete.setNegativeButton(R.string.edit_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder editor = new AlertDialog.Builder(MainActivity.this);
                editor.setTitle(R.string.editing);
                final EditText entryToEdit = new EditText(MainActivity.this);
                entryToEdit.setText(itemsToBuy.get(pos));
                entryToEdit.requestFocus();
                editor.setView(entryToEdit);
                editor.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemsToBuy.remove(pos);
                        itemsToBuy.add(pos, entryToEdit.getText().toString());
                        itemsAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, R.string.edited, Toast.LENGTH_SHORT).show();
                    }
                });
                editor.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Maybe put a toast here? Might be an annoying amount of feedback, though.
                    }
                });
                AlertDialog editing = editor.create();
                editing.show();
            }
        });
        AlertDialog aD = editOrDelete.create();
        aD.show();
    }

    public void addItem(View view) {
        // Uses an AlertDialog and an EditText to write a new entry, then adds it to the ArrayList<string>.
        AlertDialog.Builder addItemDialog = new AlertDialog.Builder(MainActivity.this);
        addItemDialog.setTitle(R.string.add_an_item);
        final EditText editText = new EditText(this); // should it be MainActivity.this?
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        addItemDialog.setView(editText);
        addItemDialog.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String textString = editText.getText().toString();
                itemsToBuy.add(textString);
                Toast.makeText(MainActivity.this, R.string.added, Toast.LENGTH_SHORT).show();
            }
        });
        addItemDialog.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Not sure whether a "cancelled" popup/toast is helpful, or annoying. For now, I'll leave it here but comment it out.
                // Toast.makeText(MainActivity.this, R.string.cancelled, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog aD = addItemDialog.create();
        aD.show();
    }
}
