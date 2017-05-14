package com.example.tjr.onsite.ui.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tjr.onsite.R;

public class ResetPasswordActivity extends AppCompatActivity {
    public EditText resetPasswordText;
    public Button sendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        resetPasswordText = (EditText) findViewById(R.id.txt_reset_pwd);
        sendButton = (Button)findViewById(R.id.btn_reset_pwd);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

    }

    //TODO set the url
    public void sendEmail(){

        if(resetPasswordText.getText().toString().isEmpty()){
            resetPasswordText.setError("Please enter your valid email");
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "url", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!(response ==null)){
                    Toast.makeText(getBaseContext(),"New Login details has been sent to your email please check your email",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),"Unexpected Error!!!",Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(ResetPasswordActivity.this).add(stringRequest);
    }
}
