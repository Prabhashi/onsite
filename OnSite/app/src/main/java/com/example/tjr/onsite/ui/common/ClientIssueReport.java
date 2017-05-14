package com.example.tjr.onsite.ui.common;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.controllers.IssueReportController;
import com.example.tjr.onsite.model.json.IssueReport;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.android.photoutil.MainActivity;
import com.kosalgeek.android.photoutil.PhotoLoader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class ClientIssueReport extends AppCompatActivity {

    // variable declaration
    ImageView ivgallery, ivcamera, ivpreview;
    Button reportButton;
    EditText issueTitle,description;
    Spinner issueType,severity;

    final int GALLERY_REQUEST = 1200;
    final static int SELECTED_IMAGE = 3;
    int imageSelectType = 0;
    GalleryPhoto galleryPhoto;
    final String TAG = this.getClass().getSimpleName();
    ArrayList<String> imageList = new ArrayList<String>();
    String picturePath = "";
    CameraPhoto cameraphoto;
    final int CAMERA_REQUEST = 13232;
    private GoogleApiClient client;
    private IssueReportController controller;


    Bitmap bmp;

    boolean imageFromGallery = false,imageFromCamera = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_issue_report);
        controller = new IssueReportController(this);

        /// spinner - type
        Spinner type = (Spinner) findViewById(R.id.type);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        cameraphoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());
        ivgallery = (ImageView) findViewById(R.id.ivgallery);
        ivcamera = (ImageView) findViewById(R.id.ivcamera);
        ivpreview = (ImageView) findViewById(R.id.ivpreview);
        reportButton = (Button) findViewById(R.id.reportButton);
        issueTitle = (EditText)findViewById(R.id.issueTitle);
        description = (EditText) findViewById(R.id.description);
        issueType = (Spinner) findViewById(R.id.type);
        severity = (Spinner) findViewById(R.id.severity);


        ivgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_REQUEST);

            }
        });

        ivcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivityForResult(cameraphoto.takePhotoIntent(), CAMERA_REQUEST);
                    //cameraphoto.();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Something happen wrong while taking photos", Toast.LENGTH_SHORT).show();

                }
            }

        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportIssue();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();


                ImageView preview = (ImageView) findViewById(R.id.ivpreview);
                bmp = BitmapFactory.decodeFile(picturePath);
                preview.setImageBitmap(bmp);


            }

        }

        /// camera button
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                String photopath = cameraphoto.getPhotoPath();
                ImageView preview = (ImageView) findViewById(R.id.ivpreview);
                bmp = BitmapFactory.decodeFile(photopath);
                preview.setImageBitmap(bmp);
                try {
                    Bitmap bitmap = ImageLoader.init().from(photopath).requestSize(512, 512).getBitmap();
                    ivpreview.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error while loading the photos ", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, photopath);

            }


        }
    }


    private void reportIssue(){
       /* if(imageFromCamera || imageFromGallery){
            Toast.makeText(this,"please upload an image", Toast.LENGTH_LONG);
            return;
        }*/
        IssueReport report = new IssueReport();
        report.setIssueTitle(issueTitle.getText().toString());
        report.setReporterId(Globals.userId);
        report.setProjectId(Globals.projectId);
       //TODO : Add assignee username here
        report.setAssigneeUsername(null);
        report.setDescription(description.getText().toString());
        report.setIssueType(issueType.getSelectedItem().toString());
        report.setSeverity(severity.getSelectedItem().toString());

        ///compress and convert image to a string
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //compress
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        //make a string
        String outputString = Base64.encodeToString(byteArray,Base64.DEFAULT);
        //will be uploaded as a string
        report.setImageString(outputString);

        controller.reportIssue(report);

    }
}
