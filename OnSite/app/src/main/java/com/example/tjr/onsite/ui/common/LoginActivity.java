package com.example.tjr.onsite.ui.common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.controllers.LoginController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText userNameText;
    private EditText passwordText;
    private TextView signUpText, forgotPasswordText;
    private Button loginButton;
    String server_url = Const.URL_PREFIX + "user/login";
    AlertDialog.Builder builder;
    private LoginController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        controller = new LoginController(this);

        userNameText = (EditText) findViewById(R.id.userNameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        signUpText = (TextView) findViewById(R.id.signUpText);
        forgotPasswordText = (TextView) findViewById(R.id.txt_forgot_pwd);
        loginButton = (Button) findViewById(R.id.loginButton);
        builder = new AlertDialog.Builder(LoginActivity.this);

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(k);
            }
        });
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(LoginActivity.this, ResetPasswordActivity.class);
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
                if (userName.isEmpty()) {
                    userNameText.setError("Please enter your user name");
                    return;
                }
                if (password.isEmpty()) {
                    passwordText.setError("Please enter your password");
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("username", userName);
                map.put("password", password);
                controller.login(new JSONObject(map));
            }


        });



    }

    //TODO: add forgot password

}
