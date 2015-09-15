package com.example.yhuang.grocerylist;

import android.provider.BaseColumns;

/**
 * Created by yhuang on 9/14/2015.
 */
public final class GroceryListContract {
    public GroceryListContract() {};

    public static abstract class GroceryListEntry implements BaseColumns {
        public static final String TABLE_NAME = "GrocerylistDatabase";
        public static final String COLUMN_NAME_ITEM = "Item";
    }
}

