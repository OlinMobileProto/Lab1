package com.mobileproto.david.grocerylist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

/**
 * This class is a AlertDialog.Builder that builds a dialog for the user to create a new item in the
 * grocery list. There is a cancel and confirm button.
 */
public class NewItemBuilder extends AlertDialog.Builder
{

    private MainActivity parentActivity;
    private EditText input;

    // Define the listeners for each of the two buttons

    // If the user hits confirm, add the new item to the grocery list
    DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            String text = input.getText().toString();
            parentActivity.addItem(text);
        }
    };

    // If they hit cancel, close the dialog and do nothing.
    DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.cancel();
        }
    };


    public NewItemBuilder(final MainActivity activity)
    {
        super(activity);
        parentActivity = activity;
        setTitle(R.string.new_item_dialog_title);

        // create the input text box
        input = new EditText(activity);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        setView(input);

        setPositiveButton(R.string.dialog_confirm, positiveListener);
        setNegativeButton(R.string.dialog_cancel, negativeListener);
    }
}
