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

    /**
     * Adds a Database entry
     * @param task the entry to be added
     */
    public void addToDB(ToDoItem task) {
        db = taskDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDatabase.colTaskDesc, task.getText());
        values.put(MyDatabase.colTaskDone, task.getCompleted());
        try {
            db.insertOrThrow(MyDatabase.tableName, MyDatabase.colTaskDesc, values);
        } catch (SQLException e) {
            // Whatever
        } finally {
            db.close();
        }
    }

    /**
     * Retrieves all data from Database
     * @return ArrayList of DTOs created from query
     */
    public ArrayList<ToDoItem> getDataFromDB() {
        db = taskDB.getReadableDatabase();
        ArrayList<ToDoItem> taskList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + MyDatabase.tableName, null);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            int result_id = c.getInt(0);
            String result_desc = c.getString(1);
            Boolean result_done = c.getInt(2) > 0;
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

    /**
     * Edits a Database entry
     * @param task the entry to be updated
     */
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

    /**
     * Deletes a Database entry
     * @param task the entry to be deleted
     */
    public void delete(ToDoItem task) {
        db = taskDB.getWritableDatabase();
        String selection = MyDatabase.colID + " = ?";
        String[] selectionArgs = { (String.valueOf(task.getId())) };
        db.delete(MyDatabase.tableName, selection, selectionArgs);
        db.close();
    }

    /**
     * Clears all entries whose "Task_Done" field is "1"
     */
    public void clearCompleted() {
        db = taskDB.getWritableDatabase();
        String selection = MyDatabase.colTaskDone + " = ?";
        String[] selectionArgs = { (String.valueOf(1)) };
        db.delete(MyDatabase.tableName, selection, selectionArgs);
        db.close();
    }

}
