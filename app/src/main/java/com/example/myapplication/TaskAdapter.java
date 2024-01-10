package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnTaskClickListener onTaskClickListener;

    public TaskAdapter(List<Task> taskList, OnTaskClickListener onTaskClickListener) {
        this.taskList = taskList;
        this.onTaskClickListener = onTaskClickListener;
    }

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    public void setOnTaskClickListener(OnTaskClickListener listener) {
        this.onTaskClickListener = listener;
    }
    public void setTasks(List<Task> tasks) {
        taskList = tasks;
        notifyDataSetChanged();
    }


    // ViewHolder class for holding references to the views
    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskNameTextView;
        TextView taskpriority;
        TextView taskStatus;
        TextView taskduedate;
        TextView category;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.taskNameTextView);
            taskpriority = itemView.findViewById(R.id.taskpriority);
            taskStatus = itemView.findViewById(R.id.taskStatus);
            taskduedate = itemView.findViewById(R.id.taskduedate);
            category = itemView.findViewById(R.id.category);

            // Set click listener for the task name
            taskNameTextView.setOnClickListener(v -> {
                if (onTaskClickListener != null) {
                    onTaskClickListener.onTaskClick(taskList.get(getAdapterPosition()));
                }
            });
        }

        public void bind(Task task) {
            taskNameTextView.setText(task.getTaskName());
            taskpriority.setText(task.getPriority());
            taskStatus.setText(task.getTaskStatus());
            taskduedate.setText(task.getDueDate());
            category.setText(task.getCategory());
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout and create the ViewHolder
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        // Bind data to the views in each ViewHolder
        Task task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
