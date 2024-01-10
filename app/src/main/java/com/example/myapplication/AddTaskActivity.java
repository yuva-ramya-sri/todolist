package com.example.myapplication;
// AddTaskActivity.java
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;


public class AddTaskActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {
    private EditText taskNameEditText,editTextDueDate, editTextCategory;
    private Spinner spinnerPriority, spinnerTaskStatus;
    private TaskAdapter taskAdapter;
    private TextInputEditText editTextTaskName;
    private TaskManager taskManager;
    private  TaskDbHelper dbHelper;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskManager = new TaskManager(this);
        dbHelper=new TaskDbHelper(this);
        taskNameEditText = findViewById(R.id.taskNameEditText);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        editTextCategory = findViewById(R.id.editTextCategory);
        spinnerTaskStatus = findViewById(R.id.spinnerTaskStatus);
        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(
                this, R.array.priority_levels, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(priorityAdapter);

        ArrayAdapter<CharSequence> taskStatusAdapter = ArrayAdapter.createFromResource(
                this, R.array.task_status_values, android.R.layout.simple_spinner_item);
        taskStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskStatus.setAdapter(taskStatusAdapter);
        taskAdapter = new TaskAdapter(getTaskList(),this);
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the entered task name
                String taskName = taskNameEditText.getText().toString();
                String dueDate = editTextDueDate.getText().toString();
                String priority = spinnerPriority.getSelectedItem().toString();
                String category = editTextCategory.getText().toString();
                String taskStatus = spinnerTaskStatus.getSelectedItem().toString();
                // Add the new task to the list (for demonstration)
                Task newTask = new Task(taskName, dueDate, priority, category, taskStatus);
                // Add the new task to the list
                MyApplication myApplication = (MyApplication) getApplication();
                myApplication.getTaskList().add(newTask);
                // Example in AddTaskActivity.java after adding a task
                dbHelper.addTask(newTask);
                setResult(RESULT_OK);


                // Finish the activity
                finish();
            }
        });
    }
    private void initializeRecyclerView() {
        // Get task list from MyApplication
        MyApplication myApplication = (MyApplication) getApplication();
        taskAdapter = new TaskAdapter(myApplication.getTaskList(),this);
        taskAdapter.setOnTaskClickListener(this);

        // Set up layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(taskAdapter);
    }
    public void onTaskClick(Task task) {
        // Handle the task click event here
        // You can open a new activity or dialog to display detailed information
        // You can also use Toast or any other UI component to show details temporarily
        String details = "Category: " + task.getCategory() +
                "\nPriority: " + task.getPriority() +
                "\nStatus: " + task.getTaskStatus() +
                "\nDue Date: " + task.getDueDate();

        Toast.makeText(this, details, Toast.LENGTH_LONG).show();
    }
    private List<Task> getTaskList() {
        MyApplication myApplication = (MyApplication) getApplication();
        return myApplication.getTaskList();
    }
}
