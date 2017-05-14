package com.example.tjr.onsite.controllers;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.Comment;
import com.example.tjr.onsite.model.json.Issue;
import com.example.tjr.onsite.ui.common.IssueHomeActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TJR on 3/14/2017.
 */

public class IssueHomeController {

    public void markAsResolved(int issueId) {
        Map<String, String> map = new HashMap<>();
        map.put("state", "resolved");

        updateDb(issueId, map);
    }

    public void updateDb(final int issueId, final Map<String, String> params) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_PREFIX.concat("issues/edit/" + issueId),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("success");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

        };

        MyVolley.getRequestQueue().add(stringRequest);
    }

    public void makeComment(int issueId, final String commentBody) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_PREFIX.concat("issues/edit/" + issueId),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("success");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", "" + Globals.userId);
                params.put("commentBody", commentBody);
                return params;
            }

        };

        MyVolley.getRequestQueue().add(stringRequest);
    }

    public void loadComments(int issueId) {

    }

    public void retrieveIssueData(final IssueHomeActivity context, final int issueId) {
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
                (Request.Method.GET, Const.URL_PREFIX + "/issues/get/" + issueId, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Issue issue = new Issue();


                        Gson gson = new GsonBuilder().create();
                        issue = gson.fromJson(response.toString(), Issue.class);
                        ArrayList<String> imgUrls = new ArrayList<>();
                        for(String s:issue.getImageUrls())
                            imgUrls.add(s.replace("localhost",Const.BASE_IP));
                        issue.getImageUrls().clear();
                        issue.getImageUrls().addAll(imgUrls);

                        context.updateIssueData(issue);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        MyVolley.getRequestQueue().add(jsonArrayRequest);
    }
}
