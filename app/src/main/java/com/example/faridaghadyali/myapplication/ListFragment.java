//Zoher Ghadyali
//Mobile Proto
//Lab 1

package com.example.faridaghadyali.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

//created this fragment which now has all the functionality required for Lab 1 because I wanted to
//add functionality that matches iOS notes app where the user can open the app and find all of their
//"notes" which are really the lists they create and create a new list. That work will be done by
//NoteFragment.java
public class ListFragment extends Fragment {
    public Button add_Item;
    public ArrayList<String> items = new ArrayList<String>();   //used to hold the strings the user
    //supplies when adding, editing, or deleting items
    public String user_item;
    public ArrayAdapter<String> itemsAdapter;   //use the array adapter to update the view of
    // ArrayList
    public ListView listView; //displays items in the ArrayList

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //different because this is a fragment so I needed to create an onCreateView function that
        //will update and modify the view
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //puts the view for this fragment into the container
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);


        listView = (ListView) rootView.findViewById(R.id.list_text);
        itemsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.text_view, items);
        listView.setAdapter(itemsAdapter);  //sets up the listView so that the ArrayAdapter can
        //update the view when the user adds, edits, or deletes an item

        add_Item = (Button) rootView.findViewById(R.id.add_item);
        add_Item.setOnClickListener(new View.OnClickListener() {
            //when the user clicks the Add Item button, calls the createAddItemDialog() which
            //creates an alert dialog for the user to add an item to the list
            @Override
            public void onClick(View v) {
                createAddItemDialog();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //when the user long clicks an item in the list, calls the createDeleteItemDialog() with
            //the position of the item the user long-clicked. This function creates an alert dialog
            //for the user to delete an item from the list
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                createDeleteItemDialog(position);
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //when the user clicks an item in the list, calls the createEditItemDialog() with the
            // position of the item the user clicked. This function creates an alert dialog for the
            // user to edit an item in the list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                createEditItemDialog(position);
            }
        });
        return rootView;
    }


    public void createAddItemDialog(){
        //doesn't take any inputs and creates an alert dialog when called. The user can input text
        //into the dialog and then click yes to add the inputted item to the list or no to cancel
        //adding the item to the list
        AlertDialog.Builder addItemDialog = new AlertDialog.Builder(getActivity());
        addItemDialog.setTitle("Add item to list");
        final EditText input = new EditText(getActivity());
        addItemDialog.setView(input);
        addItemDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //adds user input to the ArrayList
                user_item = input.getText().toString().trim();
                if (user_item.length() != 0) {
                    items.add(user_item);
                    itemsAdapter.notifyDataSetChanged();    //uses the ArrayAdapter to update the
                    //view to reflect the new item added to the list
                }
            }
        });
        addItemDialog.setNegativeButton("no", null);
        addItemDialog.show();
    }

    public void createDeleteItemDialog(final int position){
        //takes as input the position of the item that the user has long-clicked and creates an
        //alert dialog when called. The user can click yes in the alert dialog to delete the item
        //from the list or no to cancel deleting the item from the list
        final String delete_item = (String) (listView.getItemAtPosition(position));
        AlertDialog.Builder deleteItemDialog = new AlertDialog.Builder(getActivity());
        deleteItemDialog.setTitle("Delete item from list");
        deleteItemDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //removes the selected item from the ArrayList
                if (user_item.length() != 0) {
                    items.remove(delete_item);
                    itemsAdapter.notifyDataSetChanged(); //uses the ArrayAdapter to update the
                    //view to reflect the item has been deleted from the list
                }
            }});
        deleteItemDialog.setNegativeButton("no", null);
        deleteItemDialog.show();
    }

    public void createEditItemDialog(final int position){
        //takes as input the position of the item that the user has clicked and creates an alert
        // dialog when called. The alert dialog has an input text field that contains the item the
        //user clicked and the user can edit the item and click yes to apply the edit or no to
        //cancel changing the item in the list
        final String edit_item = (String) (listView.getItemAtPosition(position));
        AlertDialog.Builder editItemDialog = new AlertDialog.Builder(getActivity());
        editItemDialog.setTitle("Edit item");
        final EditText input = new EditText(getActivity());
        input.setText(edit_item);
        editItemDialog.setView(input);
        editItemDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //sets the edited item to the position it previously occupied in the list
                user_item = input.getText().toString().trim();
                if (user_item.length() != 0) {
                    items.set(position, user_item);
                    itemsAdapter.notifyDataSetChanged(); //uses the ArrayAdapter to update the
                    //view to reflect the item has been deleted from the list
                }
            }
        });
        editItemDialog.setNegativeButton("no", null);
        editItemDialog.show();
    }


}
