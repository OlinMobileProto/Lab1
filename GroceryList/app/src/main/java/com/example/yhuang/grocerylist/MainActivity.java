package com.example.yhuang.grocerylist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button myButton;
    EditText myText;
    ListView listView;
    List<String> items;
    ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(listClicker);

        myButton = (Button) findViewById(R.id.button);
        myText = (EditText)findViewById(R.id.textbox);

        myButton.setOnClickListener(buttonClicker);
    }

    public void open(View view,final int position){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.alert_message);

        alertDialogBuilder.setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                myText.setText(items.get(position));
                myButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        items.remove(position);
                        items.add(position,myText.getText().toString());
                        itemsAdapter.notifyDataSetChanged();
                        myText.setText("");
                    }
                });
            }
        });

        alertDialogBuilder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    AdapterView.OnItemClickListener listClicker = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            open(view, position);
            itemsAdapter.notifyDataSetChanged();
        }
    };

    View.OnClickListener buttonClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            items.add(myText.getText().toString());
            itemsAdapter.notifyDataSetChanged ();
            myText.setText("");
        }
    };
}



