package com.example.tjr.onsite.ui.common;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.json.DesignPlan;
import com.example.tjr.onsite.toolbox.Data;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LocationSelectorActivity extends AppCompatActivity {
    List<DesignPlan> plans = new ArrayList<>();
    ImageView planImage;
    Spinner spinner;
    Bitmap theBitmap;
    int issueId;
    int currentMap =  0;
    LocationSelectorActivity context = this;
    float lastTouchX,lastTouchY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);
        getSupportActionBar().setTitle("Location Selector");

        Intent i = getIntent();
        issueId = i.getIntExtra("issueId",0);
        planImage = (ImageView) findViewById(R.id.location_selector_plan_image);
        spinner = (Spinner) findViewById(R.id.spinner_locationselector_plan);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        populatePlans();
        ImageLoader imageLoader = MyVolley.getImageLoader();
        final String s =(getIntent().getStringExtra("imageUrl"));
        planImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent data = new Intent();
                //calculate ratios
                float xRatio = lastTouchX/planImage.getWidth();
                float yRatio = lastTouchY/planImage.getHeight();
                updateDb(plans.get(currentMap).getPlanId(),xRatio,yRatio);
                data.putExtra("xRatio", xRatio);
                data.putExtra("yRatio", yRatio);
                data.putExtra("planId", plans.get(currentMap).getPlanId());
                data.putExtra("planUrl",plans.get(currentMap).getPlanImageUrl());
                context.setResult(RESULT_OK , data);
                context.finish();
                return false;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentMap = i;
                ImageLoader loader = MyVolley.getImageLoader();
               // loader.get(plans.get(i).getPlanImageUrl(),ImageLoader.getImageListener(planImage,0,0));
                Bitmap theBitmap = Data.getBitmapFromURL(plans.get(i).getPlanImageUrl());
                planImage.setImageBitmap(theBitmap);

                   // planImage.setImageBitmap(theBitmap);

              /*  } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    void populatePlans(){
        JsonArrayRequest plansRequest = new JsonArrayRequest(
                Request.Method.GET,
                Const.URL_PREFIX + "plan/get/project/" + Globals.projectId,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int length = response.length();
                        ArrayList<String> options = new ArrayList<>();
                        for(int i=0; i<length; i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Gson gson = new GsonBuilder().create();
                                DesignPlan plan = gson.fromJson(object.toString(),DesignPlan.class);
                                plan.setPlanImageUrl(plan.getPlanImageUrl().replace("localhost",Const.BASE_IP));
                                plans.add(plan);
                                options.add(plan.getPlanName());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                    LocationSelectorActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    options);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        MyVolley.getRequestQueue().add(plansRequest);
    }

    void updateDb(int planId, float x, float y){
        Map<String,String> map = new HashMap<>();
        map.put("planId",planId+"");
        map.put("x",x+"");
        map.put("y",y+"");
        map.put("issueId",issueId+"");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Const.URL_PREFIX + "/issues/set/location",
                new JSONObject(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(LocationSelectorActivity.this,"Successfully updated location",Toast.LENGTH_LONG);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        MyVolley.getRequestQueue().add(jsonObjectRequest);
    }
}
