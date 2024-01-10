package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.List;
import java.util.ArrayList;
import android.content.ContentValues;


public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    // SQL statement to create the "tasks" table
    private static final String SQL_CREATE_TASKS_TABLE =
            "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " (" +
                    TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TaskContract.TaskEntry.COLUMN_NAME + " TEXT NOT NULL," +
                    TaskContract.TaskEntry.COLUMN_DUE_DATE + " TEXT," +
                    TaskContract.TaskEntry.COLUMN_PRIORITY + " TEXT," +
                    TaskContract.TaskEntry.COLUMN_CATEGORY + " TEXT," +
                    TaskContract.TaskEntry.COLUMN_STATUS + " TEXT)";

    // SQL statement to drop the "tasks" table if it exists
    private static final String SQL_DROP_TASKS_TABLE =
            "DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME;

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the "tasks" table
        db.execSQL(SQL_CREATE_TASKS_TABLE);
    }
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
                String taskName = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME));
                String dueDate = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_DUE_DATE));
                String priority = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_PRIORITY));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_CATEGORY));
                String taskStatus = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_STATUS));
                Task task = new Task(taskName, dueDate, priority, category, taskStatus);
                taskList.add(task);
        }

        cursor.close();
        db.close();

        return taskList;
    }
    public long addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME, task.getTaskName());
        values.put(TaskContract.TaskEntry.COLUMN_DUE_DATE, task.getDueDate());
        values.put(TaskContract.TaskEntry.COLUMN_PRIORITY, task.getPriority());
        values.put(TaskContract.TaskEntry.COLUMN_CATEGORY, task.getCategory());
        values.put(TaskContract.TaskEntry.COLUMN_STATUS, task.getTaskStatus());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade the "tasks" table if the database version changes
        db.execSQL(SQL_DROP_TASKS_TABLE);
        onCreate(db);
    }
}
