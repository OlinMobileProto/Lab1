package com.example.cwallace.grocerylist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.cwallace.grocerylist.clickListeners.AddToListListener;
import com.example.cwallace.grocerylist.clickListeners.DeleteEntryListener;
import com.example.cwallace.grocerylist.clickListeners.EditEntryListener;
import com.example.cwallace.grocerylist.services.DatabaseService;

import java.util.ArrayList;


public class GroceryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        DatabaseService dbService = new DatabaseService(this);
        setContentView(R.layout.activity_grocery);
        ArrayList<String> dataStore = dbService.readGroceriesFromDb();
        //ArrayList<String> dataStore = new ArrayList<String>();
        Button button = (Button)findViewById(R.id.add_button);
        ListView activeList = (ListView)findViewById(R.id.grocery_list);
        activeList.setAdapter(new ArrayAdapter<String>(this, R.layout.grocery_layout, dataStore));
        activeList.setOnItemClickListener(new DeleteEntryListener(this, dataStore));
        activeList.setOnItemLongClickListener(new EditEntryListener(this, dataStore));
        button.setOnClickListener(new AddToListListener(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grocery, menu);

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
