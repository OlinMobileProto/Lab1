package com.example.cynchen.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by cynchen on 9/13/15.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {


        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "FeedReader.db";

        public FeedReaderDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(FeedEntry.SQL_CREATE_ENTRIES);
        }
        public void writeData(String item) {
        // Gets the data repository in write mode
                    SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    values.put(FeedEntry.ITEM_NAME, item);

        // Insert the new row, returning the primary key value of the new row
                    long newRowId;
                    newRowId = db.insert(
                            FeedEntry.TABLE_NAME,
                            null,
                            values);
        }
        public ArrayList<String> readData(){
            SQLiteDatabase db = getReadableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
                        String[] projection = {
                                FeedEntry._ID,
                                FeedEntry.ITEM_NAME};


                        Cursor c = db.query(
                                FeedEntry.TABLE_NAME,  // The table to query
                                projection,                               // The columns to return
                                null,                                // The columns for the WHERE clause
                                null,                            // The values for the WHERE clause
                                null,                                     // don't group the rows
                                null,                                     // don't filter by row groups
                                null                                 // The sort order
                        );
            ArrayList<String> results = new ArrayList<String>(); //arraylist where db entities will be stored is created
            String array[] = new String[c.getCount()];
            int i = 0;

            c.moveToFirst();
            while (!c.isAfterLast()) {
                array[i] = c.getString(0);
                results.add(c.getString(1)); //arraylist of results has the db entities added
                i++;
                c.moveToNext();
            }
            return results; //returns the db into arraylist that will be printed by the main activity on create
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(FeedEntry.SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

