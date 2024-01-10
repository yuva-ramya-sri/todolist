package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;
    private TaskDbHelper dbHelper; // Added TaskDbHelper
    private static final int ADD_TASK_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TaskDbHelper(this); // Initialize TaskDbHelper

        recyclerView = findViewById(R.id.recyclerView);

        // Load tasks from the database
        taskAdapter = new TaskAdapter(dbHelper.getAllTasks(), this);

        // Set up layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(taskAdapter);

        Button addButton = findViewById(R.id.btnAddTask);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(addTaskIntent, ADD_TASK_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            // Refresh the adapter when a new task is added
            taskAdapter.setTasks(dbHelper.getAllTasks());
        }
    }

    @Override
    public void onTaskClick(Task task) {
        // Handle the task click event here
        // You can open a new activity or dialog to display detailed information
        // You can also use Toast or any other UI component to show details temporarily
        String details = "Category: " + task.getCategory() +
                "\nPriority: " + task.getPriority() +
                "\nStatus: " + task.getTaskStatus() +
                "\nDue Date: " + task.getDueDate();

        // Show details temporarily
        // You can replace this with your desired implementation
        showToast(details);
    }

    // Helper method to show a toast message
    private void showToast(String message) {
        // Show a Toast message
    }
}
