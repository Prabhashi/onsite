package com.example.tjr.onsite.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.controllers.TaskController;
import com.example.tjr.onsite.model.json.Task;
import com.example.tjr.onsite.ui.common.ViewTasksActivity;
import com.example.tjr.onsite.ui.manager.AddTaskActivity;

import java.util.List;



/**
 * Created by Raviyaa on 2017-03-17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder>{
    public List<Task> tasks;
    private LayoutInflater inflater;
    private Context context;
    private TaskAdapter taskAdapter;
    TaskController taskController;
    public TaskAdapter(List<Task> tasks, Context context,TaskController taskController) {
        this.tasks = tasks;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.taskController=taskController;
        taskAdapter = this;
    }
    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskHolder holder, final int position) {
        final Task task = tasks.get(position);
        holder.taskName.setText(task.getTaskName());
        holder.description.setText(task.getDescription());
        holder.startDate.setText(task.getStartDate());
        holder.estimatedEndDate.setText(task.getEstimatedEndDate());
        holder.estimatedCost.setText(task.getCost()+"");

        if (task.getCompleted()!= null && task.getCompleted()){
            holder.status.setBackgroundColor(Color.GREEN);
            holder.status.setText("Completed");

        }


//only manager can edit task properties
        if(Globals.role.equals("manager")){
            holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(context, holder.buttonViewOption);
                    popup.inflate(R.menu.option_menu);

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.menu_edit:
                                    //Toast.makeText(context,"edit menu clicked "+position,Toast.LENGTH_SHORT).show();
                                    Task t= tasks.get(position);
                                    int id=t.getTaskId();
                                    Intent intent = new Intent(context, AddTaskActivity.class);
                                    intent.putExtra("taskId",id);
                                    context.startActivity(intent);

                                    break;
                                case R.id.menu_delete:

                                    taskController.deleteTask(tasks.get(position).getTaskId());
                                    // Toast.makeText(context,"delete menu clicked "+position,Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context,ViewTasksActivity.class));
                                    break;
                                case R.id.menu_markascompleted:
                                    Toast.makeText(context,"markascompleted menu clicked "+position,Toast.LENGTH_SHORT).show();
                                    taskController.markAsCompleted(task.getTaskId());
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();
                }
            });


        }else{holder.buttonViewOption.setVisibility(View.INVISIBLE);}


    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    //help us assign data to the appropriate places
    class TaskHolder extends RecyclerView.ViewHolder{
        private TextView taskName,description,startDate,estimatedEndDate,estimatedCost,status;
        public TextView buttonViewOption;
        private View container;

        public TaskHolder(View itemView) {
            super(itemView);

            taskName =(TextView) itemView.findViewById(R.id.taskName);
            description =(TextView) itemView.findViewById(R.id.description);
            startDate =(TextView) itemView.findViewById(R.id.startDate);
            estimatedEndDate =(TextView) itemView.findViewById(R.id.endDate);
            estimatedCost =(TextView) itemView.findViewById(R.id.estimatedCost);
            //option menu
            buttonViewOption = (TextView) itemView.findViewById(R.id.txt_tasklistitem_optionmenu);
            status = (TextView) itemView.findViewById(R.id.txt_listitem_completed);

            container = itemView.findViewById(R.id.root);


        }
    }
}
