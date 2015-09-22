package com.android.phuston.noted;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {

    private String mArrayKey = "notes";
    private ArrayHelper mArrayHelper;
    private NoteFragment mNoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArrayHelper = new ArrayHelper(this);

        ArrayList<String> notes = mArrayHelper.getArray(mArrayKey);

        mNoteFragment = new NoteFragment();
        mNoteFragment.setNotesArray(notes);

        setContentView(R.layout.activity_note);
        if(savedInstanceState == null){
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mNoteFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        mArrayHelper.saveArray(mArrayKey, mNoteFragment.getNotesArray());
    }

}
