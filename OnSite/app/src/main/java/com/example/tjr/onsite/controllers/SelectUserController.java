package com.example.tjr.onsite.controllers;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.tjr.onsite.adapter.CustomAdapterList;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TJR on 3/15/2017.
 */

public class SelectUserController {
    CustomAdapterList adapter ;

    public void getSuggestions(CustomAdapterList ad){
        this.adapter = ad;
        MyVolley.getRequestQueue().cancelAll("TAG");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, Const.URL_PREFIX+"/user/all", null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<User> list = new ArrayList<>();
                            int lim = response.length();

                            for (int j = 0;j < lim; j++) {
                                JSONObject object = response.getJSONObject(j);
                                User u = new User();
                                u.fullName = object.getString("fullName");
                                u.username = object.getString("username");
                                u.imageUrl = object.getString("profilePicUrl");

                                list.add(u);
                            }
                            adapter.Users.clear();
                            adapter.Users.addAll(list);
                            adapter.notifyDataSetChanged();
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
        jsonArrayRequest.setTag("TAG");
        MyVolley.getRequestQueue().add(jsonArrayRequest);
    }
}
