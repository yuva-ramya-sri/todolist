package com.example.myapplication;
import android.provider.BaseColumns;

public final class TaskContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private TaskContract() {}

    /* Inner class that defines the table contents */
    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DUE_DATE = "due_date";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_STATUS = "status";
    }
}

