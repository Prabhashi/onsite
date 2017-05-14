package com.example.tjr.onsite.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.adapter.DashboardAdapter;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.ui.common.IssuesActivity;

import com.example.tjr.onsite.ui.manager.PlanUploadActivity;

import com.example.tjr.onsite.ui.common.UserHomeActivity;

public class DashBoardActivity extends AppCompatActivity {
    private TextView emailText;
    private ImageButton siteNavigationButton;
    private ImageButton issueReportButton;
    private ImageButton chatButton;
    private ImageButton settingsButton;
    private ImageButton issueTrackingButton;
    private ImageButton projectSettingButton;


    //TODO: create a suitable dashboard for manager
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_client);




        GridView gridView = (GridView) findViewById(R.id.grid_projecthome_container);

        String[] options = new String[1];
        int[] imageIds = new int[1];

        // Globals.role = "client";
        switch (Globals.role.toLowerCase()) {
            case "client":
                options = new String[]{"Report An Issue", "Reported Issues", "Progress", "Profile Settings", "Document Generation"};
                imageIds = new int[]{R.drawable.issue, R.drawable.fixit, R.drawable.progress, R.drawable.esetting, R.drawable.pdf};
                break;
            case "manager":
                options = new String[]{"Report An Issue", "Reported Issues", "Progress",  "Upload Plans", "Profile Settings", "Document Generation"};
                imageIds = new int[]{R.drawable.issue, R.drawable.fixit, R.drawable.progress, R.drawable.preview_plan, R.drawable.esetting, R.drawable.pdf};

                break;
            case "contractor":
                options = new String[]{"View Issues", "View Tasks"};
                imageIds = new int[]{R.drawable.fixit, R.drawable.progress};
                break;

        }

        gridView.setAdapter(new DashboardAdapter(this, options, imageIds));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        ((TextView) v.findViewById(R.id.txt_griditem_optionname))
                                .getText(), Toast.LENGTH_SHORT).show();

                switch ( ((TextView) v.findViewById(R.id.txt_griditem_optionname)).getText().toString()){
                    case "Report An Issue":
                        startActivity(new Intent(DashBoardActivity.this, ClientIssueReport.class));
                        break;
                    case "Reported Issues":
                    case "View Issues":
                        startActivity(new Intent(DashBoardActivity.this, IssuesActivity.class));
                        break;
                    case "Progress":
                    case "View Tasks":
                        startActivity(new Intent(DashBoardActivity.this, ViewTasksActivity.class));
                        break;
                    case "Message Manager":
                        Intent intent = new Intent(DashBoardActivity.this, MessageActivity.class);
                        if(Globals.project.getManager()!=null)
                        intent.putExtra("username",Globals.project.getManager().getUsername());
                        startActivity(intent);
                        break;
                    case "Profile Settings":
                        startActivity(new Intent(DashBoardActivity.this, UserHomeActivity.class));
                        break;
                    case "Document Generation":
                        startActivity(new Intent(DashBoardActivity.this, GenerateReportActivity.class));
                        break;
                    case "Upload Plans":
                        startActivity(new Intent(DashBoardActivity.this, PlanUploadActivity.class));
                        break;
                }

            }
        });



    }

}

