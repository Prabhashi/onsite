package com.example.tjr.onsite.model;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tjr.onsite.adapter.CustomAdapterList;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.json.Task;
import com.example.tjr.onsite.ui.common.IssueHomeActivity;
import com.example.tjr.onsite.ui.common.IssuesActivity;
import com.example.tjr.onsite.ui.common.UserHomeActivity;
import com.example.tjr.onsite.ui.common.ViewProjectActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TJR on 2/3/2017.
 */

public class DataSource {
    public static final String[] names = {"Madness is like gravity", "All it takes", "Is a little push"};
    public static final int[] icons = {android.R.drawable.ic_popup_reminder};

    //change this to connect to server and populate the list with real data
    public static List<Project> getData() {
        List<Project> l = new ArrayList<Project>();
        for (int i = 0; i < 10; i++) {
            l.add(new Project(names[i % names.length], "" + i, "http://wallpaper-gallery.net/images/image/image-13.jpg"));
        }
        System.out.println(l);
        return l;
    }

    public static List<Project> getDataFromServer() {
        return null;
    }


    public static void retrieveIssueData(final IssuesActivity context, int projectId) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, Const.URL_PREFIX+"/issues/get/all/"+projectId, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<Issue> list = new ArrayList<>();
                            int lim = response.length();

                            for (int j = 0;j < lim; j++) {
                                JSONObject object = response.getJSONObject(j);
                                Issue i = new Issue();
                                i.issueId = object.getInt("issueId");
                                i.name = object.getString("issueTitle");
                                i.severity = object.getString("status");
                                i.comments = 0;// object.getInt("comments");
                                i.severity = object.getString("severity").toLowerCase();
                                switch (i.severity){
                                    case "high": i.severityInt = 3;break;
                                    case "medium": i.severityInt = 2; break;
                                    case "law" : i.severityInt = 1;break;
                                }
                                i.resolved = object.getString("status");
                                String dateStr = object.getString("reportedDate");
                                String [] tokens = dateStr.split("-");
                              /*  Date d = new Date(Integer.parseInt(tokens[0]),
                                        Integer.parseInt(tokens[1]),
                                        Integer.parseInt(tokens[2]));

                                i.dateReported = d;*/
                                i.type = object.getString("issueType");
                                JSONArray arr = object.getJSONArray("imageUrls");

                                if(arr.length() >0) {
                                    String imageUrl = arr.getString(0);
                                    i.imageUrl = imageUrl.replace("localhost", Const.BASE_IP);

                                }
                                list.add(i);
                            }
                            context.updateIssueList(list);
                        } catch (JSONException e) {
                            System.out.println(e);
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









}
