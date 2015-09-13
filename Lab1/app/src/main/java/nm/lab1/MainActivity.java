package nm.lab1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //define parts of interface
        Button button = (Button) findViewById(R.id.button);
        final EditText textbox = (EditText) findViewById(R.id.editText);
        ListView list = (ListView) findViewById(R.id.list);

        final ArrayList<String> arrayList = new ArrayList<String>();
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arrayList);

        list.setAdapter(listAdapter);

        //click button listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = textbox.getText().toString();
                if(null != input && input.length() > 0){
                    arrayList.add(input);
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
        setupListViewListener(list, arrayList, listAdapter);
    }

    private void setupListViewListener(ListView list, final ArrayList<String> arrayList, final ArrayAdapter<String> listAdapter){
    //long click listener
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //remove item in listarray at position
                doAlertDialog(arrayList, position, listAdapter); //brings up alert dialog
                return true;
            }
        });
    //short click listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //arrayList.set(position, "INPUT");
                doInputAlertDialog(arrayList, position, listAdapter);
            }
        });
    }


    private void doInputAlertDialog(final ArrayList<String> arrayList, final int position, final ArrayAdapter<String> listAdapter) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Edit item: ");
        final EditText input = new EditText(this);
        alertDialogBuilder.setView(input);

        //alert buttons
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString();
                arrayList.set(position, text);
                listAdapter.notifyDataSetChanged();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.show();
    }

    private void doAlertDialog(final ArrayList<String> arrayList, final int position, final ArrayAdapter<String> listAdapter){
        //alert dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Delete this item?");

        //alert buttons
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do delete item. for now, placeholder toast
                Toast.makeText(MainActivity.this, "Item removed", Toast.LENGTH_LONG).show();
                arrayList.remove(position); //remove choice
                listAdapter.notifyDataSetChanged();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog2, int which2) {
                //do nothing
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
