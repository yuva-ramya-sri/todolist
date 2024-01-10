package com.example.myapplication;
import java.io.Serializable;
// Task.java

// Task.java


public class Task  implements Serializable{
    private String taskName;
    private String dueDate;
    private String priority; // Use String for priority
    private String category;
    private String taskStatus; // Use String for task status

    public Task(String taskName, String dueDate, String priority, String category, String taskStatus) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.priority = priority;
        this.category = category;
        this.taskStatus = taskStatus;
    }

    // Getter methods for each field

    public String getTaskName() {
        return taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getCategory() {
        return category;
    }

    public String getTaskStatus() {
        return taskStatus;
    }
}


