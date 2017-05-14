package com.example.tjr.onsite.controllers;

import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.json.Message;
import com.example.tjr.onsite.ui.common.MessageActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TJR on 3/23/2017.
 */

public class MessageController {
    public MessageActivity messageActivity;

    public MessageController(MessageActivity messageActivity) {
        this.messageActivity = messageActivity;
    }

    public void sendMessge(Map <String,String> map) {

        JSONObject jsonObject = new JSONObject(map);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Const.URL_PREFIX + "message/send",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(messageActivity, "Message sent..", Toast.LENGTH_SHORT);
                        Intent in = new Intent(messageActivity,MessageActivity.class);
                        in.putExtra("receiver",messageActivity.getIntent().getStringExtra("receiver"));
                        messageActivity.startActivity(in);
                        messageActivity.finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        MyVolley.getRequestQueue().add(request);


    }

    public void retrieveMessages(int senderId, String receiverUsername){
        Map<String, String> map = new HashMap<>();
        map.put("senderId", senderId+"");
        map.put("recieverUsername", receiverUsername);


        JSONObject jsonObject = new JSONObject(map);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                Const.URL_PREFIX + "message/send/"+senderId+"/"+receiverUsername,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Message> msgs = new ArrayList<>();
                        int length = response.length();
                        for(int i = 0; i< length; i++){
                            Gson gson = new GsonBuilder().create();
                            try {
                                Message msg = gson.fromJson(response.getJSONObject(i).toString(),Message.class);
                                msgs.add(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        messageActivity.messageAdapter.messages = msgs;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        MyVolley.getRequestQueue().add(request);
    }
}
