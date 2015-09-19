package com.example.jong.todolist.db;

import android.provider.BaseColumns;

/**
 * Created by jong on 9/13/15.
 */


public class TaskContract {
    public static final String DB_NAME = "com.example.jong.todolist.db.tasks";
    public static final int DB_VERSION = 1;
    public static final String TABLE= "tasks";

    public class Columns {
        public static final String TASK = "tasks";
        public static final String _ID = BaseColumns._ID;
    }


}
