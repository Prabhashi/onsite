package com.example.tjr.onsite.ui.common;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.adapter.ViewProjectAdapter;
import com.example.tjr.onsite.controllers.ProjectController;
import com.example.tjr.onsite.model.json.Project;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewProjectActivity extends AppCompatActivity {
    private String TAG = ViewProjectActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private String projectID;
    ListAdapter adapter;
    private FloatingActionButton floatingActionButton;
    //String server_url = Const.URL_PREFIX+"project/invloved/"+ Globals.userId;
    ArrayList<HashMap<String, String>> projectList;
    private RecyclerView recyclerView;
    public ViewProjectAdapter viewProjectAdapter;
    ProjectController projectController;
    public TextView projectsNoprojects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project_client);
        projectController = new ProjectController(this);

        projectList = new ArrayList<>();

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        projectsNoprojects = (TextView) findViewById(R.id.txt_projects_noprojects);
        recyclerView = (RecyclerView) findViewById(R.id.view_project_rec_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //fab init
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProjectActivity.this, CreateProjectActivity.class);
                startActivity(intent);
            }
        });


        viewProjectAdapter = new ViewProjectAdapter(new ArrayList<Project>(), this, projectController);
        recyclerView.setAdapter(viewProjectAdapter);
        projectController.getAllProjects();

        if(!viewProjectAdapter.projects.isEmpty()){
            projectsNoprojects.setVisibility(View.INVISIBLE);
        }

    }

}
