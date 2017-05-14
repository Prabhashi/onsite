package com.example.tjr.onsite.controllers;

import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.User;
import com.example.tjr.onsite.ui.common.LoginActivity;
import com.example.tjr.onsite.ui.common.SignUpActivity;
import com.example.tjr.onsite.ui.common.ViewProjectActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.utils.URLEncodedUtils;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by TJR on 3/21/2017.
 */

public class SignUpController {
    SignUpActivity signUpActivity;

    public SignUpController(SignUpActivity signUpActivity) {
        this.signUpActivity = signUpActivity;
    }

    public void signUp(final JSONObject user) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Const.URL_PREFIX + "user/register",
                user,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO handle server response
                        try {
                            String state = response.getString("state");
                            switch (state) {
                                case "username_exists":
                                    Toast.makeText(signUpActivity, "Username not available please select another !", Toast.LENGTH_LONG).show();
                                    return;
                                case "email_exists":
                                    Toast.makeText(signUpActivity, "There's an account already registered with this email!", Toast.LENGTH_LONG).show();
                                    return;
                            }
                            Globals.userId = response.getInt("state");
                            Intent intent = new Intent(signUpActivity, LoginActivity.class);
                            signUpActivity.startActivity(intent);
                            signUpActivity.finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                error.printStackTrace();
            }
        });

        Volley.newRequestQueue(signUpActivity).add(jsonObjectRequest);
    }
}
