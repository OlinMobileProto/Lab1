package com.example.cwallace.grocerylist.schemas;

import android.provider.BaseColumns;

/**
 * Created by cwallace on 9/8/2015.
 */
public class GrocerySchema implements BaseColumns {
    public static final String TABLE_NAME = "GROCERIES";
    public static final String GROCERY_ITEM_COLUMN = "GROCERY_NAME";
    public static final String POSITION_COLUMN = "POSITION";
    public static final String TEXT_TYPE = " TEXT,";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    GROCERY_ITEM_COLUMN + TEXT_TYPE +
                    POSITION_COLUMN + INTEGER_TYPE +
            " )";
    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


}
