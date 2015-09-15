package com.mobileproto.david.grocerylist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemLongClickListener, View.OnClickListener
{
    static final String STATE_LIST_KEY = "list";
    private static String logTag = "GroceryList";  // For debugging using Log.d(). Currently unused
    private ArrayList<String> groceryItems;
    private ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If there is a savedInstanceState, load the groceryItems from the previous state.
        if (savedInstanceState != null)
            this.groceryItems = savedInstanceState.getStringArrayList(STATE_LIST_KEY);
        // Otherwise, initialize the list.
        else
            this.groceryItems = new ArrayList<>();

        // assign the groceryList to the itemsAdapter, and then assign the itemsAdapter to the list
        // view.
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, groceryItems);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(itemsAdapter);

        // If the user long-clicks a list item, open up the edit dialog.
        listView.setOnItemLongClickListener(this);

        Button newItemButton = (Button) findViewById(R.id.button_new_item);
        newItemButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                promptNewItem();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the current grocery list
        savedInstanceState.putStringArrayList(STATE_LIST_KEY, groceryItems);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    private void promptNewItem()
    {
        AlertDialog.Builder builder = new NewItemBuilder(this);
        builder.show();
    }

    private void editItem(final int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.edit_item_dialog_title);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(groceryItems.get(position));
        builder.setView(input);

        builder.setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String text = input.getText().toString();
                groceryItems.set(position, text);
                itemsAdapter.notifyDataSetChanged();
            }
        });
        builder.setNeutralButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        builder.setNegativeButton(R.string.dialog_delete, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                groceryItems.remove(position);
                itemsAdapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }

    public void addItem(String item)
    {
        groceryItems.add(item);
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
    {
        editItem(position);
        return true;
    }

    @Override
    public void onClick(View v)
    {

    }
}
