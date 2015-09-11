package com.android.phuston.noted;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by patrick on 9/5/15.
 */
public class NoteFragment extends Fragment {

    @Bind(R.id.listview_notes) ListView listview;
    @Bind(R.id.fab) FloatingActionButton fab;

    private NoteAdapter mNotesAdapter;
    private ArrayList<String> mNotes;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mNotesAdapter = new NoteAdapter(mNotes, getActivity());

        View rootView = inflater.inflate(R.layout.fragment_note, container, false);
        ButterKnife.bind(this, rootView);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());

                alertBuilder.setTitle("Edit Note");
                alertBuilder.setMessage("Type your edited note below.");

                //Set EditText view to get user input
                final EditText mInput = new EditText(getActivity());
                mInput.setText((String) listview.getItemAtPosition(pos));
                alertBuilder.setView(mInput);

                alertBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mNotes.set(pos, mInput.getText().toString());
                    }
                });

                alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                AlertDialog alertToShow = alertBuilder.create();

                alertToShow.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                alertToShow.show();
            }
        });

        listview.setAdapter(mNotesAdapter);

        fab.attachToListView(listview);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());

                alertBuilder.setTitle("New Note");
                alertBuilder.setMessage("Type your new note below.");

                // Set an EditText view to get user input
                final EditText mInput = new EditText(getActivity());
                alertBuilder.setView(mInput);

                alertBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mNotes.add(mInput.getText().toString());
                        mNotesAdapter.notifyDataSetChanged();
                    }
                });

                alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                AlertDialog alertToShow = alertBuilder.create();

                alertToShow.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                alertToShow.show();
            }
        });

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setNotesArray(ArrayList<String> notes) {
        mNotes = notes;
    }

    public ArrayList<String> getNotesArray() {
        return mNotes;
    }
}
