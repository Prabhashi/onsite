package com.example.tjr.onsite.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.controllers.SignUpController;
import com.example.tjr.onsite.model.User;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class SignUpActivity extends AppCompatActivity {
    private TextView nameText, passwordText, emailText, phoneText, userNameText;
    private Spinner spinner;
    private Button signUpButton, cancelButton;
    String server_url = Const.URL_PREFIX + "user/register";
    AlertDialog.Builder builder;
    private String role;
    SignUpController signUpController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpController = new SignUpController(this);

        nameText = (TextView) findViewById(R.id.nameText);
        passwordText = (TextView) findViewById(R.id.passwordText);
        emailText = (TextView) findViewById(R.id.emailText);
        phoneText = (TextView) findViewById(R.id.phoneText);
        userNameText = (TextView) findViewById(R.id.userNameText);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        spinner = (Spinner) findViewById(R.id.spinner);


        final AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);
        mAwesomeValidation.addValidation(SignUpActivity.this, R.id.nameText, "^[\\p{L} . '-]+$", R.string.err_name);
        mAwesomeValidation.addValidation(SignUpActivity.this, R.id.password, "[a-zA-Z\\\\s]+", R.string.password_word);
        mAwesomeValidation.addValidation(SignUpActivity.this, R.id.emailText, android.util.Patterns.EMAIL_ADDRESS, R.string.error_email_does_not_exist);
        mAwesomeValidation.addValidation(SignUpActivity.this, R.id.phoneText, "[0-9]{10}$", R.string.common_open_on_phone);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean valid= true;//mAwesomeValidation.validate();
                if(valid){
                    User user = new User();
                    user.fullName=nameText.getText().toString();
                    user.password=passwordText.getText().toString();
                    user.phone=phoneText.getText().toString();
                    user.username=userNameText.getText().toString();
                    user.email=emailText.getText().toString();
                    user.role= spinner.getSelectedItem().toString();
                    user.deviceId=FirebaseInstanceId.getInstance().getToken();
                    try {
                        JSONObject userJson = new JSONObject(new Gson().toJson(user));
                        signUpController.signUp(userJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });


    }

}
