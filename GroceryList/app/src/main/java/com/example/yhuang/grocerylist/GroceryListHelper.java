package com.example.yhuang.grocerylist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yhuang on 9/14/2015.
 */
public class GroceryListHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "grocery_list.db";

    public GroceryListHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_GROCERY_LIST_TABLE = "CREATE TABLE " + GroceryListContract.GroceryListEntry.TABLE_NAME + " (" +
                GroceryListContract.GroceryListEntry._ID + " INTEGER PRIMARY KEY," +
                GroceryListContract.GroceryListEntry.COLUMN_NAME_ITEM + " TEXT NOT NULL, " +
                " );";

        db.execSQL(SQL_CREATE_GROCERY_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GroceryListContract.GroceryListEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
