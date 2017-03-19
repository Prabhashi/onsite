package com.example.tjr.onsite.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class SignUpActivity extends AppCompatActivity {
    private TextView nameText, passwordText, emailText, phoneText, userNameText;
    private Button signUpButton, cancelButton;
    String server_url = Const.URL_PREFIX + "user/register";
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameText = (TextView)findViewById(R.id.nameText);
        passwordText = (TextView)findViewById(R.id.passwordText);
        emailText = (TextView)findViewById(R.id.emailText);
        phoneText = (TextView)findViewById(R.id.phoneText);
        userNameText = (TextView)findViewById(R.id.userNameText);
        signUpButton = (Button)findViewById(R.id.signUpButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);



        final AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);
        mAwesomeValidation.addValidation(SignUpActivity.this,R.id.nameText,"^[\\p{L} . '-]+$",R.string.err_name);
        mAwesomeValidation.addValidation(SignUpActivity.this,R.id.password,"[a-zA-Z\\\\s]+",R.string.password_word);
        mAwesomeValidation.addValidation(SignUpActivity.this,R.id.emailText,android.util.Patterns.EMAIL_ADDRESS,R.string.error_email_does_not_exist);
        mAwesomeValidation.addValidation(SignUpActivity.this,R.id.phoneText,"[0-9]{10}$",R.string.common_open_on_phone);



       signUpButton.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {
              mAwesomeValidation.validate();
              phoneText.setInputType(InputType.TYPE_CLASS_PHONE);
                final RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     /* //validation
                        if(nameText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty() ||emailText.getText().toString().isEmpty() ||phoneText.getText().toString().isEmpty() || userNameText.getText().toString().isEmpty() || !phoneText.getText().toString().equals(" \"^[0-9]{10}$\"")){
                            if (nameText.getText().toString().isEmpty()) nameText.setError("Valid Name Here!!!");
                            if (passwordText.getText().toString().isEmpty()) passwordText.setError("Valid Password Here!!!");
                            if (emailText.getText().toString().isEmpty()) emailText.setError("Valid Email Here!!!");
                            //if (phoneText.getText().toString().isEmpty() ||!phoneText.getText().toString().equals(" \"^[0-9]{10}$\"")) phoneText.setError("Valid Phone Number Here!!!");
                            if (userNameText.getText().toString().isEmpty()) userNameText.setError("Valid User Name Here!!!");
                            return;
                        }
*/
                       try{
                           JSONObject jsonObject = new JSONObject(response);
                           String state = jsonObject.getString("state");
                           if("username_exists".equals(state)){
                               Toast.makeText(getApplicationContext(), "User Exists!!!", Toast.LENGTH_LONG).show();
                           }else{

                               Globals.userId = jsonObject.getInt("state");
                               Intent intent = new Intent(SignUpActivity.this, com.example.tjr.onsite.ui.common.LoginActivity.class);
                               startActivity(intent);
                               finish();
                           }
                       }catch(Exception e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "SignUp Error!!!", Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("fullName", nameText.getText().toString());
                        params.put("password", passwordText.getText().toString());
                        params.put("username", userNameText.getText().toString());
                        params.put("phoneNumber", phoneText.getText().toString());
                        params.put("email", emailText.getText().toString());
                        params.put("deviceId", FirebaseInstanceId.getInstance().getToken());


                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });


    }
    //TODO: add role spinner
}
