package com.example.cwallace.grocerylist.clickListeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cwallace.grocerylist.R;
import com.example.cwallace.grocerylist.services.DatabaseService;

import java.util.List;

/**
 * Created by cwallace on 9/7/2015.
 */
public class DeleteEntryListener implements AdapterView.OnItemClickListener {

    Context context;
    List<String> groceryList;
    DatabaseService databaseService;

    public DeleteEntryListener(Context context, List<String> groceryList) {
        this.context = context;
        this.groceryList = groceryList;
        databaseService = new DatabaseService(context);
    }


    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder
                .setTitle("Delete from list")
                .setCancelable(true)
                .setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                groceryList.remove(position);
                                databaseService.deleteGroceries(position);
                                Log.i("DELETE ENTRY", String.valueOf(groceryList.size()));
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
                });
        ;

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }
}
