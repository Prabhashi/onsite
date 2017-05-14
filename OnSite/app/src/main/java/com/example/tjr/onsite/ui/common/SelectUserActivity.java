package com.example.tjr.onsite.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.adapter.SelectUserAdapter;
import com.example.tjr.onsite.controllers.SelectUserController;
import com.example.tjr.onsite.model.json.User;

import java.util.ArrayList;

public class SelectUserActivity extends Activity {

    RecyclerView recyclerView;
    SelectUserAdapter adapter;
    SearchView searchView;
    SelectUserController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user2);

        Intent i = getIntent();
        recyclerView = (RecyclerView)findViewById(R.id.rec_usersearch_content);


        searchView = (SearchView) findViewById(R.id.search_usersearch_search);
        recyclerView = (RecyclerView)findViewById(R.id.rec_usersearch_content);

        //set recycler view properties
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new SelectUserAdapter(this,new ArrayList<User>());
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        controller = new SelectUserController(adapter,this);
        controller.fillUsers(i.getStringExtra("type"));
    }
}
