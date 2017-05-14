package com.example.tjr.onsite.ui.manager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.model.json.IssueReport;
import com.example.tjr.onsite.ui.common.DashBoardActivity;
import com.example.tjr.onsite.ui.common.IssuesActivity;
import com.google.gson.GsonBuilder;
import com.kosalgeek.android.photoutil.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class PlanUploadActivity extends AppCompatActivity {
    private EditText planNameText;
    private EditText planDescriptionText;
    private ImageView uploadSelectedPlanButton;
    private ImageView previewImageView;
    private ImageView selectFromGallery;
    private static final int RESULT_LOAD_IMAGE = 1;
    String picturePath = "";
    int imageSelectType = 0;
    private final int SELECTED_IMAGE = 3;
    Button uploadButton;
    Bitmap bmp;
    private boolean imageSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_upload);

        planNameText = (EditText) findViewById(R.id.txt_planupload_name);
        planDescriptionText = (EditText) findViewById(R.id.txt_planupload_description);
        uploadButton = (Button) findViewById(R.id.btn_planupload_upload);
        previewImageView =(ImageView) findViewById(R.id.img_planupload_preview);
        selectFromGallery = (ImageView) findViewById(R.id.img_planupload_select);
        selectFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

       uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();


                bmp = BitmapFactory.decodeFile(picturePath);
                previewImageView.setImageBitmap(bmp);
                imageSelected = true;

            }

        }
    }


    private void uploadImage(){

        if(!imageSelected){
            Toast.makeText(this,"Please choose a floor plan image",Toast.LENGTH_LONG);
        }
        ///compress and convert image to a string
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //compress
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        //make a string
        String outputString = Base64.encodeToString(byteArray,Base64.DEFAULT);
        //will be uploaded as a string
        //done image compression

        /***/
        Map<String,String> map = new HashMap<>();
        map.put("projectId",Globals.projectId+"");
        map.put("planName",planNameText.getText().toString());
        map.put("description",planDescriptionText.getText().toString());
        map.put("image",outputString);

        JSONObject jsonObject = new JSONObject(map);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Const.URL_PREFIX + "/plan/add/json",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(PlanUploadActivity.this, "Successfully Uploaded Plan", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PlanUploadActivity.this,DashBoardActivity.class);
                        startActivity(intent);
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(request);

    }

}
