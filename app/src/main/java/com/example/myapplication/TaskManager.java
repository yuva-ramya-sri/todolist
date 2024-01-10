package com.example.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private TaskDbHelper dbHelper;

    public TaskManager(Context context) {
        dbHelper = new TaskDbHelper(context);
    }

    public void addTask(Task task) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME, task.getTaskName());
        values.put(TaskContract.TaskEntry.COLUMN_DUE_DATE, task.getDueDate());
        values.put(TaskContract.TaskEntry.COLUMN_PRIORITY, task.getPriority());
        values.put(TaskContract.TaskEntry.COLUMN_CATEGORY, task.getCategory());
        values.put(TaskContract.TaskEntry.COLUMN_STATUS, task.getTaskStatus());

        try {
            db.insertOrThrow(TaskContract.TaskEntry.TABLE_NAME, null, values);
        } catch (SQLException e) {
            // Handle insert error
        } finally {
            db.close();
        }
    }

    public List<Task> getTaskList() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_NAME,
                TaskContract.TaskEntry.COLUMN_DUE_DATE,
                TaskContract.TaskEntry.COLUMN_PRIORITY,
                TaskContract.TaskEntry.COLUMN_CATEGORY,
                TaskContract.TaskEntry.COLUMN_STATUS
        };

        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME));
                String dueDate = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_DUE_DATE));
                String priority = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_PRIORITY));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_CATEGORY));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_STATUS));

                Task task = new Task(name, dueDate, priority, category, status);
                taskList.add(task);
            }
        } finally {
            cursor.close();
            db.close();
        }

        return taskList;
    }
}
