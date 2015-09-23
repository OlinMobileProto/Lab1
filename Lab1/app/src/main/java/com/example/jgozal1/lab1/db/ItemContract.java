package com.example.jgozal1.lab1.db;
import android.provider.BaseColumns;

/**
 * Created by jgozal1 on 9/18/2015.
 */
public class ItemContract {
    public static final String DB_NAME = "com.example.jgozal1.lab1.db.items";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "items";

    public class Columns {
        public static final String ITEM = "item";
        public static final String _ID = BaseColumns._ID;
    }
}

