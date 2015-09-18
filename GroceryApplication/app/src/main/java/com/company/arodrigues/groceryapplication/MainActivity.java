package com.company.arodrigues.groceryapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    public ArrayList<String> groceryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        final EditText groceryItem = (EditText) findViewById(R.id.edittext);
        Button myButton = (Button) findViewById(R.id.button);
        final ListView listview = (ListView) findViewById(R.id.listview);
        final ArrayAdapter<String> itemsAdapter = new ArrayAdapter(MainActivity.this,R.layout.grocery_list_layout, groceryList);
        listview.setAdapter(itemsAdapter);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groceryItem.getText() != null) {
                    groceryList.add(groceryItem.getText().toString());
                    itemsAdapter.notifyDataSetChanged();
                    groceryItem.setText("");
                } else {
                }
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                final String grocItem = (String) parent.getItemAtPosition(position);
                final EditText changeText = new EditText(MainActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.message);
                builder.setView(changeText);
                builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
                    }
                });
                builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        groceryList.remove(grocItem);
                        itemsAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNeutralButton(R.string.edit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String changedText = changeText.getText().toString();
                        if (changedText.equals("")) {
                        } else {
                            groceryList.set(position, changedText);
                            itemsAdapter.notifyDataSetChanged();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
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
