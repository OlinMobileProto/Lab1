package com.keenanzucker.grocerylist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    final Context context = this;
    private String inputText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lvItems = (ListView) findViewById(R.id.lvItems);

        items = new ArrayList<>();

        //Use ArrayAdapter to create scrollable list
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        //Example note to display
        items.add("Example Note");
        setupListViewListener();

    }

    //Edit or remove notes by listening to the user clicks
    private void setupListViewListener() {

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){

                    //Single click on list item to edit
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                                View item, int pos, long id)
                    {
                        //Edit text through an Alert Dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Edit Note");

                        final EditText input = new EditText(context);
                        final int itemPos = pos;

                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);

                        //Positive button on dialog box -- "change"
                        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                inputText = input.getText().toString();

                                // Remove the item within array at position
                                items.remove(itemPos);
                                // Then insert item at same location -- aka 'editing' the text
                                itemsAdapter.insert(inputText, itemPos);
                                // Refresh the adapter
                                itemsAdapter.notifyDataSetChanged();

                            }
                        });

                        //Negative button on dialog box -- "cancel"
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }

                        });

                        //Show the dialog box!
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
        );

        // Long click list items to delete them
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        items.remove(pos);
                        // Refresh the adapter
                        itemsAdapter.notifyDataSetChanged();
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }
                }
        );
    }

    // Add the items when the button is clicked!
    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
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
