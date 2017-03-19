package com.example.tjr.onsite.ui.common;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.adapter.IssueAdapter;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.model.DataSource;
import com.example.tjr.onsite.model.Issue;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class IssuesActivity extends AppCompatActivity {

    private android.support.v7.widget.RecyclerView recView;
    private IssueAdapter issueAdapter;
    private Spinner orderBySpinner, orderSpinner;
    private ProgressDialog progressDialog;
    private boolean ascending = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);

        getSupportActionBar().setTitle("Reported Issues");

        String s = FirebaseInstanceId.getInstance().getToken();
        System.out.println(s);
        Intent intent = getIntent();
        int projectId = intent.getIntExtra("projectId", 1);

        orderSpinner = (Spinner) findViewById(R.id.spn_issues_order);
        orderBySpinner = (Spinner) findViewById(R.id.spn_issues_order_by);
        recView = (RecyclerView) findViewById(R.id.rec_issues);
        recView.setLayoutManager(new LinearLayoutManager(this));

        issueAdapter = new IssueAdapter(new ArrayList<Issue>(), this);
        recView.setAdapter(issueAdapter);


        orderBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                issueAdapter.sortListBy(selectedItem, ascending);
                issueAdapter.notifyDataSetChanged();
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equalsIgnoreCase("ascending"))
                    ascending = true;
                else
                    ascending = false;
                issueAdapter.sortListBy(orderBySpinner.getSelectedItem().toString(), ascending);
                issueAdapter.notifyDataSetChanged();
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        DataSource.retrieveIssueData(this, projectId);

    }

    public void updateIssueList(List<Issue> issues) {
        issueAdapter.setIssues(issues);
        System.out.println(issues.size());
        issueAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        DataSource.retrieveIssueData(this, getIntent().getIntExtra("projectId", Globals.projectId));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.report_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(IssuesActivity.this, GenerateReportActivity.class));
        finish();
        return true;
    }
}
