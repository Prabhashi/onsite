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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;

import java.io.File;

import java.util.ArrayList;
import java.util.List;


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
    private static final int RESULT_LOAD_IMAGE = 1;
    String picturePath = "";
    int imageSelectType = 0;
    private final int SELECTED_IMAGE = 3;
    LinearLayout uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_upload);

        planNameText = (EditText) findViewById(R.id.planNameText);
        planDescriptionText = (EditText) findViewById(R.id.planDescriptionText);

        uploadSelectedPlanButton = (ImageView) findViewById(R.id.uploadSelectedPlanButton);
        uploadSelectedPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        uploadButton = (LinearLayout) findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String planName = planNameText.getText().toString();
                String planDescription = planDescriptionText.getText().toString();


                if (planName.isEmpty() || planDescription.isEmpty() || picturePath.isEmpty()) {
                    if (planName.isEmpty()) planNameText.setError("Please enter a valid plan name");
                    if (planDescription.isEmpty()) planDescriptionText.setError("Please fill this filed");
                    if (picturePath.isEmpty()) Toast.makeText(getBaseContext(),"Please select a image",Toast.LENGTH_LONG).show();
                    return;
                }



            }
        });
    }

    //handling the result from image view
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            imageSelectType = SELECTED_IMAGE;

            ImageView imageView = (ImageView) findViewById(R.id.previewImageView);
            Bitmap bmp = BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(bmp);
        } else {
            imageSelectType = 0;
        }

        //using mulitipart form data

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();

                String filePath = getPath(selectedImage);
                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                picturePath = filePath;

                try {
                    if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(Const.URL_PREFIX + "plan/add");

                        //MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

                        /* example for setting a HttpMultipartMode */
                        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

                        if (filePath != null) {
                            File file = new File(filePath);
                            FileBody fileBody = new FileBody(file);
                            Log.d("EDIT USER PROFILE", "UPLOAD: file length = " + file.length());
                            Log.d("EDIT USER PROFILE", "UPLOAD: file exist = " + file.exists());
                            builder.addPart("image", fileBody);

                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                            nameValuePairs.add(new BasicNameValuePair("planName",planNameText.getText().toString()));
                            nameValuePairs.add(new BasicNameValuePair("description",planDescriptionText.getText().toString()));
                            //nameValuePairs.addPart("plans", fileBody);
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            httppost.setEntity(builder.build());
                            HttpResponse response = httpclient.execute(httppost);

                            System.out.println(response.toString());

                        }
                    } else {
                        System.out.println("Else");
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);

        return cursor.getString(column_index);
    }

}
