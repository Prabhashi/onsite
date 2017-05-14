package com.example.tjr.onsite.controllers;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.util.MarkEnforcingInputStream;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.json.Task;
import com.example.tjr.onsite.ui.common.LoginActivity;
import com.example.tjr.onsite.ui.common.ViewTasksActivity;

import com.example.tjr.onsite.ui.manager.AddTaskActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TJR on 3/22/2017.
 */

public class TaskController {
    ViewTasksActivity viewTasksActivity;
    AddTaskActivity addTaskActivity;

    public TaskController(ViewTasksActivity viewTasksActivity) {
        this.viewTasksActivity = viewTasksActivity;
    }

    public TaskController(AddTaskActivity addTaskActivity) {
        this.addTaskActivity = addTaskActivity;
    }

    public ArrayList<Task> list = new ArrayList<>();

    public void getAllTask() {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, Const.URL_PREFIX + "/task/all/" + Globals.projectId + "/" + Globals.userId, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            int lim = response.length();
                            for (int i = 0; i < lim; i++) {
                                Gson gson = new GsonBuilder().create();

                                JSONObject obj = response.getJSONObject(i);


                                Task task = gson.fromJson(obj.toString(), Task.class);

                                list.add(task);
                            }
                            viewTasksActivity.taskAdapter.tasks.addAll(list);
                            viewTasksActivity.taskAdapter.notifyDataSetChanged();

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

    public void markAsCompleted(int taskId) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_PREFIX + "/task/markascompleted/" + taskId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Intent intent = new Intent(viewTasksActivity, ViewTasksActivity.class);
                viewTasksActivity.startActivity(intent);
                viewTasksActivity.finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        MyVolley.getRequestQueue().add(stringRequest);
    }

    public void editTask(int taskId) {
        //TODO

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Const.URL_PREFIX + "/task/get/"+taskId,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson=new GsonBuilder().create();
                        Task task=gson.fromJson(response.toString(),Task.class);
                        addTaskActivity.updateUI(task);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                error.printStackTrace();
            }
        });

        MyVolley.getRequestQueue().add(jsonObjectRequest);

    }


    public void updateTask(final Task task) {

        StringRequest jsonArrayRequest = new StringRequest
                (Request.Method.GET,
                        Const.URL_PREFIX + "/task/edit" ,
                        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("success")){
                            Toast.makeText(addTaskActivity,"Successfully updated task details!",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       error.printStackTrace();

                    }
                } ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("taskId", task.getTaskId().toString());
                params.put("assigneeId", task.getAssignee().toString());
                params.put("taskName", task.getTaskName().toString());
                params.put("startDate", task.getStartDate().toString());
                params.put("estimatedEndDate", task.getEstimatedEndDate().toString());
                params.put("cost", task.getCost().toString());
                params.put("description", task.getDescription().toString());
                return params;
            }
        };
        MyVolley.getRequestQueue().add(jsonArrayRequest);

    }

    public void deleteTask(int taskId) {
        AlertDialog.Builder builder ;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
                (Request.Method.DELETE,
                        Const.URL_PREFIX + "/task/delete/"+taskId ,
                        null,
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.toString().contains("success")){
                            Toast.makeText(addTaskActivity,"Successfully deleted the task!",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                });
        MyVolley.getRequestQueue().add(jsonArrayRequest);

    }
}
