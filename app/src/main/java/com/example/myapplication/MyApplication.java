package com.example.myapplication;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private List<Task> taskList = new ArrayList<>();

    public List<Task> getTaskList() {
        return taskList;
    }
}
