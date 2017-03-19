package com.example.tjr.onsite.ui.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.Project;
import com.example.tjr.onsite.toolbox.Validation;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class BarChartActivity extends AppCompatActivity {
    private BarChart barChart;
    private String TAG = BarChartActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    ArrayList<BarEntry> barEntryList;
    float diff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        barChart = (BarChart) findViewById(R.id.barChart);
        // new GetContacts().execute();
        barEntryList = new ArrayList<>();
        tasksInvolved();
    }


    public void tasksInvolved() {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, Const.URL_PREFIX + "task/all/" + Globals.projectId, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Project project = new Project();
                        try {
                            //make an array list from recieved jsonarray
                            int lim = response.length();
                            for (int i = 0; i < lim; i++) {
                                JSONObject obj = response.getJSONObject(i);
                                String taskID = obj.getString("taskId");

                                String startDate = obj.getString("startDate");


                                String endDate = obj.getString("estimatedEndDate");
                                String[] endDateSplit = startDate.split("/");
                                //int days = Integer.valueOf(endDateSplit[2]) - Integer.valueOf(startDateSplit[2]) + (Integer.valueOf(endDateSplit[1]) - Integer.valueOf(startDateSplit[1])) * 30;
                                //finding daydifference
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM.yyyy");
                                try {

                                    Date date1 = sdf.parse(startDate);
                                    Date date2 = sdf.parse(endDate);

                                    diff = Validation.getDifference(date1, date2);
                                    Toast.makeText(getApplicationContext(), Float.toString(diff), Toast.LENGTH_LONG).show();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                barEntryList.add(new BarEntry(Float.valueOf(taskID), new Random().nextInt(40) + 1));
                            }
                            //context.updateProjectList(list);
                            BarDataSet set = new BarDataSet(barEntryList, "Tasks");
                            BarData data = new BarData(set);
                            data.setBarWidth(2.0f); // set custom bar width
                            barChart.setData(data);
                            barChart.setFitBars(true); // make the x-axis fit exactly all bars
                            barChart.invalidate();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);

                    }
                });
        MyVolley.getRequestQueue().add(jsonArrayRequest);

    }
/*
    public int getDuration(String startDate, String endDate) {

    }*/
}
