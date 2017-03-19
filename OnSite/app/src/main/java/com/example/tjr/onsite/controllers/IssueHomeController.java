package com.example.tjr.onsite.controllers;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.MyVolley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TJR on 3/14/2017.
 */

public class IssueHomeController {

    public void markAsResolved(int issueId){
        Map<String, String> map = new HashMap<>();
        map.put("state","resolved");

        updateDb(issueId,map);
    }
    public void updateDb(final int issueId ,final Map<String,String> params){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_PREFIX.concat("issues/edit/"+issueId),
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
                }){
            @Override
            protected Map<String,String> getParams(){
                return params;
            }

        };

        MyVolley.getRequestQueue().add(stringRequest);
    }
}
