package com.example.jong.todolist;

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
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    
    ArrayList<String> arraylist = new ArrayList();
    ListView listview;
               
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.todolist1);
        listview.setAdapter(new ArrayAdapter<String>(this, R.layout.task_view, arraylist));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                String entry = arraylist.get(position);
                AlertDialog.Builder edittaskalert = new AlertDialog.Builder(MainActivity.this);
                edittaskalert.setTitle("Edit Task");
                edittaskalert.setMessage("Need to do:");
                final EditText textinput = new EditText(MainActivity.this);
                textinput.setText(entry);
                edittaskalert.setView(textinput);
                DialogInterface.OnClickListener Editbuttonlistener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task = textinput.getText().toString();
                        Log.d("MainActivity", task);
                        arraylist.set(pos, task);
                        ArrayAdapter<String> todolist_adapter = (ArrayAdapter<String>) listview.getAdapter();
                        todolist_adapter.notifyDataSetChanged();
                    }
                };
                edittaskalert.setPositiveButton("Edit", Editbuttonlistener);

                edittaskalert.setNegativeButton("Cancel", null);
                edittaskalert.create().show();


            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                String entry = arraylist.get(position);
                AlertDialog.Builder deltaskalert = new AlertDialog.Builder(MainActivity.this);
                deltaskalert.setTitle("Delete Task");
                deltaskalert.setMessage("Are you sure?");
                DialogInterface.OnClickListener delbuttonlistener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("MainActivity", "Deleting Task");
                        arraylist.remove(pos);
                        ArrayAdapter<String> todolist_adapter = (ArrayAdapter<String>) listview.getAdapter();
                        todolist_adapter.notifyDataSetChanged();
                    }
                };
                deltaskalert.setPositiveButton("Yes", delbuttonlistener);

                deltaskalert.setNegativeButton("Cancel", null);
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
            DialogInterface.OnClickListener Addbuttonlistener = new DialogInterface.OnClickListener(){

                @Override
            public void onClick(DialogInterface dialogInterface, int i){
                    String task = textinput.getText().toString();
                    Log.d("MainActivity", task);
                    arraylist.add(task);
                    ArrayAdapter<String> todolist_adapter = (ArrayAdapter<String>) listview.getAdapter();
                    todolist_adapter.notifyDataSetChanged(); 
                }
            };
            addtaskalert.setPositiveButton("Add", Addbuttonlistener);

            addtaskalert.setNegativeButton("Cancel", null);
            addtaskalert.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
