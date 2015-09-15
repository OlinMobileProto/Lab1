package com.mobileproto.david.grocerylist;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemLongClickListener, View.OnClickListener
{
    static final String STATE_LIST_KEY = "list";
    private static String logTag = "GroceryList";  // For debugging using Log.d(). Currently unused

    private ArrayList<String> groceryItems;
    private ArrayAdapter<String> itemsAdapter;

    public ArrayAdapter<String> getItemsAdapter()
    {
        return itemsAdapter;
    }

    public ArrayList<String> getGroceryItems()
    {
        return groceryItems;
    }

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

        // Add the long click listener to the listview
        listView.setOnItemLongClickListener(this);

        // Add the click listener to the button
        Button newItemButton = (Button) findViewById(R.id.button_new_item);
        newItemButton.setOnClickListener(this);
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

    /**
     * Opens a dialog that allows the user to enter a new grocery item. The Builder object will add
     * the new item to the ArrayList and update the ArrayAdapter.
     */
    private void promptNewItem()
    {
        AlertDialog.Builder builder = new NewItemBuilder(this);
        builder.show();
    }

    /**
     * Edits the nth value in the List by creating a dialog that allows the user to edit the text
     * or delete the value. The Builder will update the ArrayList and ArrayAdapter.
     */
    private void editItem(final int n)
    {
        AlertDialog.Builder builder = new EditItemBuilder(this, n);
        builder.show();
    }

    /**
     * Adds a string to the groceryItems ArrayList and updates the ArrayAdapter
     */
    public void addItem(String item)
    {
        groceryItems.add(item);
        itemsAdapter.notifyDataSetChanged();
    }


    /**
     * Currently, there is only one element that has a long click listener: The ListView. If we long
     * click on the list view, then allow the user to edit the item they clicked on.
     *
     * Note: If you add other elements with a long click listener, then you will have to check the
     * origin of the click using a switch or if-else chain.
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
    {
        // If the user long-clicks a list item, open up the edit dialog.
        editItem(position);
        return true;
    }

    /**
     * Currently, there is only one element that has a click listener: The new item button. If we
     * click on it, allow the user to add a new element.
     *
     * Note: If you add other elements with a lick listener, then you will have to check the
     * origin of the click using a switch or if-else chain.
     */
    @Override
    public void onClick(View v)
    {
        promptNewItem();
    }
}
