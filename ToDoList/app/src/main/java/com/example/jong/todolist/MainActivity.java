package com.example.jong.todolist;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import com.example.jong.todolist.R;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> arraylist = new ArrayList();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.todolist1);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.todolist_text, arraylist));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* short click to edit*/
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos=position;
                String entry = arraylist.get(position);
                AlertDialog.Builder edittaskalert = new AlertDialog.Builder(MainActivity.this);
                edittaskalert.setTitle("Edit the Task");
                edittaskalert.setMessage("Need to do:");
                final EditText textinput = new EditText(MainActivity.this);
                textinput.setText(entry);
                edittaskalert.setView(textinput);
                edittaskalert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String entry1 = textinput.getText().toString();
                        Log.d("MainActivity", entry1);
                        arraylist.set(pos, entry1);
                        ArrayAdapter<String> todolist_db = (ArrayAdapter<String>) listView.getAdapter();
                        todolist_db.notifyDataSetChanged();
                    }
                });
                edittaskalert.setNegativeButton("Cancel",null);
                edittaskalert.create().show();
            }
        });

            /* Long Click to delete*/
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos=position;
                String entry = arraylist.get(position);
                AlertDialog.Builder deltaskalert = new AlertDialog.Builder(MainActivity.this);
                deltaskalert.setTitle("Delete the task");
                deltaskalert.setMessage("Do you really want to delete this task?");
                deltaskalert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Log.d("MainActivity", "Deleting task");
                        arraylist.remove(pos);
                        ArrayAdapter<String> todolist_db = (ArrayAdapter<String>) listView.getAdapter();
                        todolist_db.notifyDataSetChanged();
                    }

                });
                deltaskalert.setNegativeButton("Cancel",null);
                deltaskalert.create().show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void AlertDialog_edit(String entry, int position) {
        AlertDialog.Builder addtaskalert = new AlertDialog.Builder(this);
        addtaskalert.setTitle("Edit this task");
        addtaskalert.setMessage("Need to do:");
        final EditText textinput = new EditText(this);
        addtaskalert.setView(textinput);
        DialogInterface.OnClickListener Addbuttonlistener = new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                String task = textinput.getText().toString();
                Log.d("MainActivity", task);
                arraylist.add(task);
                ArrayAdapter<String> todolist_db = (ArrayAdapter<String>) listView.getAdapter();
                todolist_db.notifyDataSetChanged();
            }
        };
        addtaskalert.setPositiveButton("Add", Addbuttonlistener);
        addtaskalert.setNegativeButton("Cancel", null);
        addtaskalert.create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
         if (id == R.id.action_add_task) {

             AlertDialog.Builder addtaskalert = new AlertDialog.Builder(this);
             addtaskalert.setTitle("Add a Task");
             addtaskalert.setMessage("Need to do:");
             final EditText textinput = new EditText(this);
             addtaskalert.setView(textinput);
             DialogInterface.OnClickListener Addbuttonlistener = new DialogInterface.OnClickListener() {

                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     String task = textinput.getText().toString();
                     Log.d("MainActivity", task);
                     arraylist.add(task);
                     ArrayAdapter<String> todolist_db = (ArrayAdapter<String>) listView.getAdapter();
                     todolist_db.notifyDataSetChanged();
                 }
             };
             addtaskalert.setPositiveButton("Add", Addbuttonlistener);
             addtaskalert.setNegativeButton("Cancel", null);
             addtaskalert.create().show();
             return true;
         }
         else {
                return false;
         }
    }
}

