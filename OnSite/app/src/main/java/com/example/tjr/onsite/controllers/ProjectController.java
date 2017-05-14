package com.example.tjr.onsite.controllers;

import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.json.Project;
import com.example.tjr.onsite.ui.common.ViewProjectActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Raviyaa on 2017-03-20.
 */

public class ProjectController {

    public static ArrayList<Integer> ids = new ArrayList<>();
    public ViewProjectActivity viewProjectActivity;

    public ProjectController(ViewProjectActivity viewProjectActivity) {
        this.viewProjectActivity = viewProjectActivity;
    }

    public void getAllProjects() {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, Const.URL_PREFIX + "project/involved/" + Globals.userId, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //make an array list from recieved jsonarray
                            ArrayList<Project> list = new ArrayList<>();
                            int lim = response.length();
                            for (int i = 0; i < lim; i++) {
                                JSONObject obj = response.getJSONObject(i);
                                Gson gson = new GsonBuilder().create();
                                Project p = gson.fromJson(obj.toString(),Project.class);
                                list.add(p);
                            }
                            viewProjectActivity.viewProjectAdapter.projects.addAll(list);
                            viewProjectActivity.viewProjectAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);

                    }
                });
        MyVolley.getRequestQueue().add(jsonArrayRequest);

    }

    public void acceptProject(final int projectId, final boolean accept){
        Map<String,String> map = new HashMap<>();
        map.put("projectId",String.valueOf(projectId));
        map.put("userId",String.valueOf(Globals.userId));
        map.put("response",accept?"accepted":"rejected");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Const.URL_PREFIX + "/project/accept",
                new JSONObject(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(accept)
                            Toast.makeText(viewProjectActivity,"You have accepted the project",Toast.LENGTH_LONG);
                        else
                            Toast.makeText(viewProjectActivity,"You have rejected the project",Toast.LENGTH_LONG);

                        Intent intent= new Intent(viewProjectActivity,ViewProjectActivity.class);
                        viewProjectActivity.startActivity(intent);
                        viewProjectActivity.finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public byte[] getBody() {
                return super.getBody();
            }
        };

        MyVolley.getRequestQueue().add(jsonObjectRequest);
    }
}
