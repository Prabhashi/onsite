package com.example.tjr.onsite.ui.common;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.controllers.UserHomeController;
import com.example.tjr.onsite.model.json.User;
import com.example.tjr.onsite.ui.manager.PlanUploadActivity;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserHomeActivity extends AppCompatActivity {

    ImageView editFullNameImage;
    EditText fullNameEditText;
    EditText usernameEditText;
    EditText emailEditText;
    EditText phoneEditText;
    Button updateButton;
    ImageView img_userhome_profile;
    UserHomeController userHomeController;
    private static final int RESULT_LOAD_IMAGE = 1;
    Bitmap bmp;
    private boolean imageSelected = true;
    String picturePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        editFullNameImage = (ImageView) findViewById(R.id.img_userhome_edit);
        fullNameEditText = (EditText) findViewById(R.id.edit_userhome_fullname);
        usernameEditText = (EditText) findViewById(R.id.edit_userhome_username);
        emailEditText = (EditText) findViewById(R.id.edit_userhome_email);
        phoneEditText = (EditText) findViewById(R.id.edit_userhome_phone);
        img_userhome_profile = (ImageView)findViewById(R.id.img_userhome_profile);
        updateButton = (Button)findViewById(R.id.btn_userhome_update);

        updateButton.setVisibility(View.GONE);
        fullNameEditText.setEnabled(false);
        usernameEditText.setEnabled(false);
        emailEditText.setEnabled(false);
        phoneEditText.setEnabled(false);
       // img_userhome_profile.setVisibility(View.INVISIBLE);

        //setinfooncreate
        userHomeController = new UserHomeController(this);
        userHomeController.setUserInfoOnCreate(Globals.userId);

        editFullNameImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               enableTextEditable();
            }
        });

        img_userhome_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserInfo();
            }
        });

        img_userhome_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserInfo();
                Toast.makeText(UserHomeActivity.this, "Updated User info", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void enableTextEditable() {
        fullNameEditText.setEnabled(true);
        usernameEditText.setEnabled(true);
        emailEditText.setEnabled(true);
        phoneEditText.setEnabled(true);
        img_userhome_profile.setVisibility(View.VISIBLE);
        updateButton.setVisibility(View.VISIBLE);
    }

    public void setTextFields(User user){
        fullNameEditText.setText(user.getFullName());
        usernameEditText.setText(user.getUsername());
        emailEditText.setText(user.getEmail());
        phoneEditText.setText(user.getPhoneNumber());
        ImageLoader imageLoader = MyVolley.getImageLoader();
        imageLoader.get(user.getProfilePicUrl().replace("localhost",Const.BASE_IP), ImageLoader.getImageListener(img_userhome_profile,0,0));
        bmp = img_userhome_profile.getDrawingCache();
//        img_userhome_profile.setImageResource(user.imageUrl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE) {
                imageSelected = false;
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();


                bmp = BitmapFactory.decodeFile(picturePath);
                img_userhome_profile.setImageBitmap(bmp);
                imageSelected = true;

            }

        }
    }

    private void updateUserInfo(){
        ///compress and convert image to a string
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //compress
        String outputString = null;
        if(imageSelected) {
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            //make a string
            outputString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        User user = new User();
        user.setId(Globals.userId);
        user.setEmail(emailEditText.getText().toString());
        user.setProfilePicUrl(outputString) ;
        user.setPhoneNumber(phoneEditText.getText().toString());
        user.setUsername(usernameEditText.getText().toString());

        //convert java obj to jsonobj
        String jsonString = new GsonBuilder().create().toJson(user);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Const.URL_PREFIX + "/user/edit",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(UserHomeActivity.this, "Successfully Updated !!!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UserHomeActivity.this,DashBoardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        MyVolley.getRequestQueue().add(request);
    }
}
