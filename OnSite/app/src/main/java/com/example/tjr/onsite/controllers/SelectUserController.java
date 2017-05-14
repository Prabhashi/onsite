package com.example.tjr.onsite.controllers;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.tjr.onsite.adapter.CustomAdapterList;
import com.example.tjr.onsite.adapter.SelectUserAdapter;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.json.User;
import com.example.tjr.onsite.ui.common.SelectUserActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TJR on 3/15/2017.
 */

public class SelectUserController {

    SelectUserActivity context;
    SelectUserAdapter adapter;

    public SelectUserController(SelectUserAdapter adapter, SelectUserActivity context) {
        this.adapter = adapter;
        this.context = context;
    }

    public void fillUsers(String role){
        MyVolley.getRequestQueue().cancelAll("TAG");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, Const.URL_PREFIX+"/user/all/"+role, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<User> list = new ArrayList<>();
                            int lim = response.length();

                            for (int j = 0;j < lim; j++) {
                                JSONObject object = response.getJSONObject(j);
                                Gson gson = new GsonBuilder().create();
                                User u = gson.fromJson(object.toString(),User.class);
                                list.add(u);
                            }
                            adapter.users.clear();
                            adapter.users.addAll(list);
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
