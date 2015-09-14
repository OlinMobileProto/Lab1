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
{
    static final String STATE_LIST_KEY = "list";
    private static String logTag = "GroceryList";
    private ArrayList<String> strings;
    private ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
        {
            this.strings = savedInstanceState.getStringArrayList(STATE_LIST_KEY);
        } else
        {
            this.strings = new ArrayList<>();
        }

        this.itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strings);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                editItem(position);
                return true;
            }
        });

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
        savedInstanceState.putStringArrayList(STATE_LIST_KEY, strings);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    private void promptNewItem()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.new_item_dialog_title);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String text = input.getText().toString();
                addItem(text);
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void editItem(final int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.edit_item_dialog_title);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(strings.get(position));
        builder.setView(input);

        builder.setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String text = input.getText().toString();
                strings.set(position, text);
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
                strings.remove(position);
                itemsAdapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }

    private void addItem(String item)
    {
        strings.add(item);
        itemsAdapter.notifyDataSetChanged();
    }

}
