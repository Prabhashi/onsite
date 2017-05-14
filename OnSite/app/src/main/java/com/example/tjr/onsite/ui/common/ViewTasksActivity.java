package com.example.tjr.onsite.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.adapter.TaskAdapter;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.controllers.TaskController;
import com.example.tjr.onsite.model.json.Task;
import com.example.tjr.onsite.ui.manager.AddTaskActivity;

import java.util.ArrayList;


public class ViewTasksActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public TaskAdapter taskAdapter;
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    TaskController taskController;
    public TextView tasksNotasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        tasksNotasks = (TextView) findViewById(R.id.txt_tasks_notasks);
        taskController = new TaskController(this);



        taskAdapter = new TaskAdapter(new ArrayList<Task>(), this,taskController);
        recyclerView.setAdapter(taskAdapter);

        taskController.getAllTask();

        if(Globals.role.equals("client")){
            floatingActionButton.setVisibility(View.INVISIBLE);
        }
        //fab init
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTasksActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        if(!taskAdapter.tasks.isEmpty()){
            tasksNotasks.setVisibility(View.INVISIBLE);
            tasksNotasks.setText(null);
        }
        //
        if(Globals.role.equals("contractor")){
            floatingActionButton.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.barchart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(ViewTasksActivity.this, BarChartActivity.class));
        finish();
        return true;
    }
}
