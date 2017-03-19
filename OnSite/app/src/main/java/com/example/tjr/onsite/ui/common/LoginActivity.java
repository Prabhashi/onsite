package com.example.tjr.onsite.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText userNameText;
    private EditText passwordText;
    private TextView signUpText;
    private Button loginButton;
    String server_url = Const.URL_PREFIX + "user/login";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameText = (EditText) findViewById(R.id.userNameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        signUpText = (TextView) findViewById(R.id.signUpText);
        loginButton = (Button) findViewById(R.id.loginButton);
        builder = new AlertDialog.Builder(LoginActivity.this);

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(k);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName, password;
                userName = userNameText.getText().toString();
                password = passwordText.getText().toString();
                    //fields checking
                if (userName.isEmpty() || password.isEmpty()) {
                    if (userName.isEmpty()) {
                        userNameText.setError("Please enter your user name");
                    }
                    if (password.isEmpty()) {
                        passwordText.setError("Please enter your password");
                    }
                    return;
                }
                final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //response is received as a jason object
                            JSONObject obj = new JSONObject(response);
                            String state = obj.getString("state");
                            if ("failed".equals(state)) {
                                Toast.makeText(getApplicationContext(), "Check username & password again!!!", Toast.LENGTH_LONG).show();
                            } else {

                                Globals.userId = obj.getInt("state");
                                // TODO read role and redirect
                                //Intent intent = new Intent(LoginActivity.this, ViewProjectActivity.class);
                                //startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        builder.setTitle("Server Response");
                        builder.setMessage("Error" + error);
                        Toast.makeText(getApplicationContext(), "Check username & password again!!!", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("username", userName);
                        params.put("password", password);

                        return params;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });


    }

    //TODO: add forgot password

}
