package com.example.tjr.onsite.ui.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.ui.common.IssuesActivity;
import com.example.tjr.onsite.ui.common.UserHomeActivity;

public class ManagerDashBoardActivity extends AppCompatActivity {
    private TextView emailText;
    private ImageButton siteNavigationButton;
    private ImageButton issueReportButton;
    private ImageButton chatButton;
    private ImageButton settingsButton;
    private ImageButton issueTrackingButton;
    private ImageButton projectSettingButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_manager);


        siteNavigationButton = (ImageButton) findViewById(R.id.siteNavigationButton);
        issueReportButton = (ImageButton) findViewById(R.id.issueReportButton);
        chatButton = (ImageButton) findViewById(R.id.chatButton);
        settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        issueTrackingButton = (ImageButton)findViewById(R.id.issueTrackingButton);
        projectSettingButton = (ImageButton)findViewById(R.id.projectSettingButton);

        siteNavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: view tasks with suitable privilages
                //Intent intent = new Intent(ManagerDashBoardActivity.this, ViewTaskActivity.class);
                //startActivity(intent);
            }
        });
//TODO: pubnub
        /*chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(ManagerDashBoardActivity.this, ChatActivity.class));
            }
        });*/
        issueReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(ManagerDashBoardActivity.this, ReportIssueActivity.class));
            }
        });
        issueTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagerDashBoardActivity.this, IssuesActivity.class));
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagerDashBoardActivity.this, UserHomeActivity.class));
            }
        });
        projectSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagerDashBoardActivity.this, PlanUploadActivity.class));
            }
        });


    }


}
