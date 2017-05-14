package com.example.tjr.onsite.controllers;

import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.ui.common.DashBoardActivity;
import com.example.tjr.onsite.ui.common.LoginActivity;
import com.example.tjr.onsite.ui.common.ViewProjectActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TJR on 3/21/2017.
 */

public class LoginController {
    private LoginActivity context;

    public LoginController(LoginActivity context) {
        this.context = context;
    }

    public void login(JSONObject details){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Const.URL_PREFIX + "user/login",
                details,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO handle server response
                        try {
                            String state = response.getString("state");
                            if(state.equalsIgnoreCase("failed")){
                                Toast.makeText(context,"Username or password incorrect",Toast.LENGTH_LONG).show();
                                return;
                            }
                            Globals.userId = response.getInt("state");
                            Globals.role = response.getString("role");

                            Intent intent = new Intent(context, ViewProjectActivity.class);
                            if(Globals.role.equals("contractor"))
                                intent = new Intent(context, DashBoardActivity.class);
                            context.startActivity(intent);
                            context.finish();
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
        }) {
        };

        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
}
