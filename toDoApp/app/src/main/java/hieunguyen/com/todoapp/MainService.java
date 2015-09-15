package hieunguyen.com.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by hieunguyen on 9/14/15.
 * Class for servicing the MainActivity in its interaction with the database, with all the
 * necessary functions: add, delete, update, clearCompleted and getData.
 */
public class MainService {

    public MyDatabase taskDB;
    public SQLiteDatabase db;

    public MainService(Context context) {
        taskDB = new MyDatabase(context);
    }

    public void addToDB(ToDoItem task) {
        //Log.d("save", "saving data");
        db = taskDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDatabase.colTaskDesc, task.getText());
        values.put(MyDatabase.colTaskDone, task.getCompleted());
        try {
            db.insertOrThrow(MyDatabase.tableName, MyDatabase.colTaskDesc, values);
            //Log.d("entry", task.toString());
        } catch (SQLException e) {
            //Log.d("caught", "got it");
        } catch (Exception e) {
            // In case we missed it
        } finally {
            db.close();
        }
    }

    public ArrayList<ToDoItem> getDataFromDB() {
        //Log.d("get", "getting data");
        db = taskDB.getReadableDatabase();
        ArrayList<ToDoItem> taskList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + MyDatabase.tableName, null);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            int result_id = c.getInt(0);
            String result_desc = c.getString(1);
            Boolean result_done = c.getInt(2) > 0;
            //Log.d("entry", String.valueOf(result_id) + ", " + result_desc + ", " + String.valueOf(result_done) );
            ToDoItem task = new ToDoItem(result_desc);
            task.setId(result_id);
            task.setCompleted(result_done);
            taskList.add(task);
            c.moveToNext();
        }
        c.close();

        db.close();
        return taskList;
    }

    public void edit(ToDoItem task) {
        db = taskDB.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MyDatabase.colID, task.getId());
        cv.put(MyDatabase.colTaskDesc, task.getText());
        cv.put(MyDatabase.colTaskDone, task.getCompleted());
        db.update(MyDatabase.tableName, cv,
                MyDatabase.colID + "=?", new String[]{String.valueOf(task.getId())});
        db.close();
    }

    public void delete(ToDoItem task) {
        db = taskDB.getWritableDatabase();
        String selection = MyDatabase.colID + " = ?";
        String[] selectionArgs = { (String.valueOf(task.getId())) };
        db.delete(MyDatabase.tableName, selection, selectionArgs);
        db.close();
    }

    public void clearCompleted() {
        //Log.d("clear", "clearCompleted called");
        db = taskDB.getWritableDatabase();
        String selection = MyDatabase.colTaskDone + " = ?";
        String[] selectionArgs = { (String.valueOf(1)) };
        db.delete(MyDatabase.tableName, selection, selectionArgs);
        db.close();
    }

}
