package com.example.tjr.onsite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.model.Task;

import java.util.List;



/**
 * Created by Raviyaa on 2017-03-17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder>{
    public List<Task> tasks;
    private LayoutInflater inflater;
    private Context context;
    private TaskAdapter taskAdapter;
    public TaskAdapter(List<Task> tasks, Context context) {
        this.tasks = tasks;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        taskAdapter = this;
    }
    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_task, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskName.setText(task.getTaskName());
        holder.description.setText(task.getDescription());
        holder.startDate.setText(task.getStartDate());
        holder.estimatedEndDate.setText(task.getEstimatedEndDate());
        holder.estimatedCost.setText(task.getEstimatedCost());

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    //help us assign data to the appropriate places
    class TaskHolder extends RecyclerView.ViewHolder{
        private TextView taskName,description,startDate,estimatedEndDate,estimatedCost;
        private View container;

        public TaskHolder(View itemView) {
            super(itemView);

            taskName =(TextView) itemView.findViewById(R.id.taskName);
            description =(TextView) itemView.findViewById(R.id.description);
            startDate =(TextView) itemView.findViewById(R.id.startDate);
            estimatedEndDate =(TextView) itemView.findViewById(R.id.endDate);
            estimatedCost =(TextView) itemView.findViewById(R.id.estimatedCost);
            //root of the element list
            container = itemView.findViewById(R.id.root);


        }
    }
}
