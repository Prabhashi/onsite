package com.example.tjr.onsite.ui.manager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.adapter.TaskAdapter;
import com.example.tjr.onsite.model.DataSource;
import com.example.tjr.onsite.model.Task;

import java.util.ArrayList;


public class ManagerViewTaskActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public TaskAdapter taskAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_manager);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        taskAdapter = new TaskAdapter(new ArrayList<Task>(), this);
        recyclerView.setAdapter(taskAdapter);
        DataSource.getAllTask(this);


    }

  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        return true;
    }
}
