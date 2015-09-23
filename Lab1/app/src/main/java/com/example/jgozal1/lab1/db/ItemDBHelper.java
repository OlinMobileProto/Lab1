package com.example.jgozal1.lab1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jgozal1 on 9/18/2015.
 */
public class ItemDBHelper  extends SQLiteOpenHelper {

    public ItemDBHelper(Context context) {
        super(context, ItemContract.DB_NAME, null, ItemContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuery =
                String.format("CREATE TABLE %s (" +
                                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "%s TEXT)", ItemContract.TABLE,
                        ItemContract.Columns.ITEM);

        Log.d("ItemDBHelper","Query to form table: "+sqlQuery);
        sqlDB.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
        sqlDB.execSQL("DROP TABLE IF EXISTS "+ItemContract.TABLE);
        onCreate(sqlDB);
    }
}
