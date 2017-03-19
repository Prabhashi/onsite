package com.example.tjr.onsite.ui.common;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.MyVolley;

import java.util.concurrent.ExecutionException;

public class LocationSelectorActivity extends AppCompatActivity {

    ImageView planImage;
    Bitmap theBitmap;
    LocationSelectorActivity context = this;
    float lastTouchX,lastTouchY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);
        getSupportActionBar().setTitle("Location Selector");

        planImage = (ImageView) findViewById(R.id.location_selector_plan_image);

        ImageLoader imageLoader = MyVolley.getImageLoader();
        final String s =(getIntent().getStringExtra("imageUrl"));
        planImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent data = new Intent();
                //calculate ratios
                float xRatio = lastTouchX/planImage.getWidth();
                float yRatio = lastTouchY/planImage.getHeight();
                data.putExtra("xRatio", xRatio);
                data.putExtra("yRatio", yRatio);
                context.setResult(RESULT_OK , data);
                context.finish();
                return false;
            }
        });

        planImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                lastTouchX = motionEvent.getX();
                lastTouchY = motionEvent.getY();

                return false;
            }
        });

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if(Looper.myLooper() == null)
                    Looper.prepare();

                try {
                    theBitmap = Glide.
                            with(LocationSelectorActivity.this).
                            load(s).
                            asBitmap().
                            into(-1,-1).
                            get();
                } catch (final ExecutionException e) {
                    System.out.println(e.getMessage());
                } catch (final InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void dummy) {
                if (null != theBitmap) {
                    // The full bitmap should be available here
                   planImage.setImageDrawable(new BitmapDrawable(getResources(),theBitmap));

                };
            }
        }.execute();
    }
}
