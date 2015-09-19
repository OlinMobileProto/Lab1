package com.example.cwallace.grocerylist.clickListeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.cwallace.grocerylist.R;
import com.example.cwallace.grocerylist.services.DatabaseService;

import java.util.logging.Logger;

/**
 * Created by cwallace on 9/7/2015.
 */
public class AddToListListener implements View.OnClickListener {

    private Context context;
    private DatabaseService dbService;

    public AddToListListener(Context context) {
        this.context = context;
        dbService = new DatabaseService(context);

    }

    @Override
    public void onClick(View v) {

        View rootView = v.getRootView();
        EditText textToAdd = (EditText)rootView.findViewById(R.id.text_to_add);
        ListView groceryList = (ListView)rootView.findViewById(R.id.grocery_list);
        ArrayAdapter<String> groceryAdapter = (ArrayAdapter<String>)groceryList.getAdapter();

        String groceryItem = textToAdd.getText().toString();
        if (groceryItem.length() != 0 ) {
            groceryAdapter.add(groceryItem);
            dbService.writeToDatabase(groceryItem, groceryAdapter.getCount());
            groceryAdapter.notifyDataSetChanged();
            textToAdd.setText("");
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            alertDialogBuilder
                    .setTitle("Groceries must not be blank")
                    .setCancelable(false)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }
                    );
            ;

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();
        }

    }
}
