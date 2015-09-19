package com.example.cynchen.shoppinglist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button mybutton; //the Add to List button
    public ListView listview; //the list view of all of the shopping list items
    public EditText mEdit; //the enter text box where grocery items will be typed in, and then entered by the mybutton
    public ArrayList<String> values; //list of grocery list item strings
    private ArrayAdapter<String> adapter; //will display the values using array adapter
    private FeedReaderDbHelper readDB; //utilizes the functions in FeedReaderDbHelper, which creates a database where data will be read and stored

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mybutton = (Button) findViewById(R.id.list_button); //mybutton is connected to #list_button of xml
        listview = (ListView) findViewById(R.id.grocerylist); //listview is connected to #grocerylist of xml
        mEdit   = (EditText)findViewById(R.id.inputtext); //mEdit is connected to #inputtext of xml
        readDB = new FeedReaderDbHelper(this); //readDB is connected to FeedReaderDbHelper in this function
        values = new ArrayList<String>(readDB.readData()); //all values are extracted from the database using readDB


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values); //sets the values to array adapter

        listview.setAdapter(adapter); //setting adapter will make the listview display the values

        //WHEN Add to List BUTTON IS CLICKED: Item is stored into database and displayed on array adapter listview
        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mEdit.getText().toString(); //grabs the particular item name string
                readDB.writeData(str); //the string is written into the database
                if (str.length() != 0) {
                    values.add(str); //adds the string into arraylist of item names
                    mEdit.setText(""); // adds text to arraylist and make edittext blank again, so another item can be entered
                    adapter.notifyDataSetChanged(); //notifies adapter to change because data in arraylist "values" has been altered
                }
            }
        });

        //WHEN LIST VIEW ITEM IS CLICKED: Alert dialog pops up and allows user to edit the item name
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position,
                                    long id) {

                String item = (String) (listview.getItemAtPosition(position)); //Gets the string name based off of the position that is clicked

                AlertDialog.Builder edititem = new AlertDialog.Builder(
                        MainActivity.this); //AlertDialog is built and called edititem
                edititem.setTitle("Edit List Item"); //AlertDialog title is set
                final EditText input = new EditText(MainActivity.this); //an edittext option is created
                input.setText(item); //the input first has the item name that is being edited displayed
                edititem.setView(input); //the edittext input is set in the dialog

                //When the ok button is pressed: the text that is put in the edittext is set in the values arraylist and then updated in the adapter
                edititem.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        values.set(position, input.getText().toString());
                        adapter.notifyDataSetChanged();  // update the adapter that you set in the listView.setAdapter();
                    }
                });

                //When the cancel button is pressed: nothing is done
                edititem.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                edititem.show(); //sets the edititem alertdialog to show
            }
        });

        //WHEN LIST VIEW ITEM IS LONGCLICKED: Alert dialog pops up and allows user to choose whether or not they want to delete item
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String item = (String) (listview.getItemAtPosition(position)); //Gets the string name based off of the position that is clicked

                AlertDialog.Builder deleteitem = new AlertDialog.Builder(
                        MainActivity.this); //AlertDialog of deleting the item is put in
                deleteitem.setTitle("Do You Want to Permanently Delete This Item"); //the title is set
                deleteitem.setMessage("Item you are deleting: " + item); //message shows the item name that they are deleting

                //When Ok is pressed: item is removed from list of values and adapter is updated
                deleteitem.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        values.remove(item);
                        adapter.notifyDataSetChanged();
                    }
                });

                //When Cancel is pressed: nothing is done and alertdialog is closed
                deleteitem.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                deleteitem.show(); //sets the deleteitem alertdialog to show
                return true;
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
