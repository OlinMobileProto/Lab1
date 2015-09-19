package com.mobileproto.david.grocerylist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

/**
 * This class is a AlertDialog.Builder that builds a dialog for the user to edit an existing item
 * in the grocery list. There is a text field to change its text, a delete button, a cancel button,
 * and a confirm button.
 */
public class EditItemBuilder extends AlertDialog.Builder
{

    private MainActivity parentActivity;
    private EditText input;
    private int position;

    // Define the listeners for each of the three buttons

    // If the user clicks confirm, change the value in the ArrayList and update the ArrayAdapter
    DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            String text = input.getText().toString();
            parentActivity.getGroceryItems().set(position, text);
            parentActivity.getItemsAdapter().notifyDataSetChanged();
        }
    };

    // If the user presses cancel, just end the dialog and don't do anything
    DialogInterface.OnClickListener neutralListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.cancel();
        }
    };

    // If they hit delete, then remove the item from the ArrayList and update the ItemsAdapter
    DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            parentActivity.getGroceryItems().remove(position);
            parentActivity.getItemsAdapter().notifyDataSetChanged();
        }
    };



    public EditItemBuilder(MainActivity activity, int position)
    {
        super(activity);
        parentActivity = activity;
        this.position = position;
        setTitle(R.string.edit_item_dialog_title);

        // create the input text box
        input = new EditText(activity);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(activity.getGroceryItems().get(position));
        setView(input);

        setPositiveButton(R.string.dialog_confirm, positiveListener);
        setNeutralButton(R.string.dialog_cancel, neutralListener);
        setNegativeButton(R.string.dialog_delete, negativeListener);
    }
}
