package com.example.tjr.onsite.controllers;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.json.User;
import com.example.tjr.onsite.ui.common.UserHomeActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

/**
 * Created by Raviyaa on 2017-03-24.
 */

public class UserHomeController {
    UserHomeActivity userHomeActivity;

    public UserHomeController(UserHomeActivity userHomeActivity) {
        this.userHomeActivity = userHomeActivity;
    }
    public void setUserInfoOnCreate(int userId){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Const.URL_PREFIX + "/user/id/" + userId,
                null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson= new GsonBuilder().create();
                User user = gson.fromJson(response.toString(), User.class);
                userHomeActivity.setTextFields(user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyVolley.getRequestQueue().add(jsonObjectRequest);
    }

    public void updateInfo(User user){

    }
}
