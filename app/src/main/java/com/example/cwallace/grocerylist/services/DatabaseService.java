package com.example.cwallace.grocerylist.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cwallace.grocerylist.schemas.GrocerySchema;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by cwallace on 9/8/2015.
 */
public class DatabaseService extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Grocery.db";
    private SQLiteDatabase writableGroceryDb;
    private SQLiteDatabase readableGroceryDb;

    public DatabaseService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        writableGroceryDb = getWritableDatabase();
        readableGroceryDb = getReadableDatabase();
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GrocerySchema.SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(GrocerySchema.SQL_DELETE_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean writeToDatabase(String groceryItem, int position) {
        ContentValues insertValues = new ContentValues();
        insertValues.put(GrocerySchema.GROCERY_ITEM_COLUMN, groceryItem);
        insertValues.put(GrocerySchema.POSITION_COLUMN, position);
        try {
            writableGroceryDb.insertOrThrow(GrocerySchema.TABLE_NAME, null, insertValues);
            return true;
        } catch (Exception ex) {
            Log.e("Database Service", "Error occurred inserting into database", ex);
            return false;
        }
    }


    public ArrayList<String> readGroceriesFromDb() {
            String[] columnsToQuery = {
                    GrocerySchema.GROCERY_ITEM_COLUMN,
                    GrocerySchema._ID
            };
            Cursor groceryCursor = readableGroceryDb.query(GrocerySchema.TABLE_NAME, columnsToQuery , null, null, null, null ,null);
            ArrayList<String> groceries = new ArrayList<String>();
            if (groceryCursor.moveToFirst()) {
                groceries.add(groceryCursor.getString(0));
                Log.i("PRIMARY KEY", String.valueOf(groceryCursor.getInt(1)));
                while (groceryCursor.moveToNext()) {
                    groceries.add(groceryCursor.getString(0));
                    Log.i("PRIMARY KEY", String.valueOf(groceryCursor.getInt(1)));
                }
            }
            return groceries;
        }

    public boolean deleteGroceries(int position) {
        String selection = GrocerySchema.POSITION_COLUMN + "= ? ";
        String[] selectionValue = {
            String.valueOf(position + 1)
        };
        int deleteRows = readableGroceryDb.delete(GrocerySchema.TABLE_NAME, selection, selectionValue);
        Log.i("ROWS DELETE", "POSITION " + String.valueOf(position) + String.valueOf(deleteRows));
        return true;
    }
    }

