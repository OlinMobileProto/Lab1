package com.example.cwallace.grocerylist.clickListeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.List;

/**
 * Created by cwallace on 9/8/2015.
 */
public class EditEntryListener implements AdapterView.OnItemLongClickListener {

    Context context;
    List<String> groceryList;

    public EditEntryListener(Context context, List<String> groceryList) {
        this.context = context;
        this.groceryList = groceryList;
    }


    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        final EditText inputText = new EditText(context);

        alertDialogBuilder
                .setTitle("Edit Item")
                .setCancelable(true)
                .setPositiveButton("Accept",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                groceryList.remove(position);
                                groceryList.add(position,inputText.getText().toString());
                                ArrayAdapter groceryAdapter = (ArrayAdapter)parent.getAdapter();
                                groceryAdapter.notifyDataSetChanged();

                            }
                        }
                )
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                })
                .setView(inputText);


        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
        return true;

    }
}

