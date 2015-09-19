package hieunguyen.com.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hieunguyen on 9/14/15.
 */
public class MyDatabase extends SQLiteOpenHelper {

    public static final int dbVersion = 1;
    static final String dbName = "ToDoDatabase.db";
    public static final String tableName = "Tasks";

    public static final String colID = "Task_ID";
    public static final String colTaskDesc = "Task_Description";
    public static final String colTaskDone = "Task_Completed";

    public MyDatabase(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + tableName + "("
                + colID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + colTaskDesc + " TEXT, "
                + colTaskDone + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

}
