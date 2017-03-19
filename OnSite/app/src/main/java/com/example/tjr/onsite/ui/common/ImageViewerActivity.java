package com.example.tjr.onsite.ui.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Const;

public class ImageViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        getSupportActionBar().hide();
        Intent intent = getIntent();

        String imageUrl = intent.getStringExtra("image_url"); //file:///local/dir/image.jpg
        System.out.println(imageUrl);
        WebView wv = (WebView) findViewById(R.id.web_image_viewer_main_view);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.loadUrl(imageUrl);
    }
}
