package com.example.tjr.onsite.controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.model.json.IssueReport;
import com.example.tjr.onsite.ui.common.ClientIssueReport;
import com.example.tjr.onsite.ui.common.IssuesActivity;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by TJR on 3/23/2017.
 */

public class IssueReportController {
    ClientIssueReport context;

    public IssueReportController(ClientIssueReport context) {
        this.context = context;
    }


    public void reportIssue(IssueReport issueReport){
        //user gson library to convert issue report object to java object
        String jsonString = new GsonBuilder().create().toJson(issueReport);
        JSONObject jsonObject = null;
        try {
             jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Const.URL_PREFIX + "/issues/create/json",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Successfully Reported Issue", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context,IssuesActivity.class);
                        context.startActivity(intent);
                        context.finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(context).add(request);
    }
}
