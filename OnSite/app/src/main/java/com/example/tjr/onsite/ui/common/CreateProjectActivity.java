package com.example.tjr.onsite.ui.common;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateProjectActivity extends AppCompatActivity {
    private TextView projectNameText, companyNameText, locationText, startDateText, endDateText,managerText;
    private Button createProjectButton;
    int start_year, start_month, start_day, end_year, end_month, end_day;
    static final int START_DATE_DIALOG_ID = 0;
    static final int END_DATE_DIALOG_ID = 1;
    int PLACE_PICKER_REQUEST = 1;
    String server_url = Const.URL_PREFIX+"project/create";
    AlertDialog.Builder builder;

    CreateProjectActivity current = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        projectNameText = (TextView) findViewById(R.id.planNameText);
        companyNameText = (TextView) findViewById(R.id.companyNameText);
        locationText = (TextView) findViewById(R.id.locataionText);
        createProjectButton = (Button) findViewById(R.id.createProjectButton);
        startDateText = (TextView) findViewById(R.id.startDateText);
        endDateText = (TextView) findViewById(R.id.endDateText);
        managerText = (TextView) findViewById(R.id.managerText);
        builder = new AlertDialog.Builder(CreateProjectActivity.this);


        managerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectManager();
            }
        });
        //initializing the datepickers
        startDateText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(START_DATE_DIALOG_ID);
            }
        });

        final Calendar c = Calendar.getInstance();
        start_year = c.get(Calendar.YEAR);
        start_month = c.get(Calendar.MONTH);
        start_day = c.get(Calendar.DAY_OF_MONTH);
        updateStartDisplay();

        endDateText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(END_DATE_DIALOG_ID);
            }
        });

        final Calendar c1 = Calendar.getInstance();
        end_year = c1.get(Calendar.YEAR);
        end_month = c1.get(Calendar.MONTH);
        end_day = c1.get(Calendar.DAY_OF_MONTH);
        updateEndDisplay();

        //inserting data to the database
        createProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validation

                if(projectNameText.getText().toString().isEmpty() || companyNameText.getText().toString().isEmpty() ||locationText.getText().toString().isEmpty()|| ! validDifference(c,c1)){
                    if(projectNameText.getText().toString().isEmpty()) projectNameText.setError("Enter a valid project name!!!");
                    if(companyNameText.getText().toString().isEmpty()) companyNameText.setError("Enter a valid company name!!!");
                    if(locationText.getText().toString().isEmpty()) locationText.setError("Select a location !!!");
                    /*if(  !validDifference(c,c1)) {
                        Toast.makeText(getApplicationContext(), "Please fill the dates correctly " + Globals.taskId, Toast.LENGTH_LONG).show(); return;
                    }*/
                    return;
                }
                //

                final String projectName, companyName, location, startDate, endDate;
                projectName = projectNameText.getText().toString();
                companyName = companyNameText.getText().toString();
                location = locationText.getText().toString();
                startDate = start_day+"/"+start_year + "/" + start_month ;
                endDate =  end_day+"/"+end_year + "/" + end_month;

                final RequestQueue requestQueue = Volley.newRequestQueue(CreateProjectActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        builder.setTitle("Message!");
                        builder.setMessage("You have successfully cerated a project!");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(CreateProjectActivity.this, "Project Successfully created",Toast.LENGTH_LONG);
                                Intent intent = new Intent(CreateProjectActivity.this,ViewProjectActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });
                        AlertDialog alertDialog = builder.show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {



                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("projectName", projectName);
                        params.put("companyName", companyName);
                        params.put("location", location);
                        params.put("startDate", startDateText.getText().toString());
                        params.put("estimatedCompletionDate", endDateText.getText().toString());
                        params.put("creatorId", String.valueOf(Globals.userId));
                        params.put("otherUser", managerText.getText().toString());

                        return params;
                    }
                };



                requestQueue.add(stringRequest);
            }
        });

        //adding place picker
        locationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;

                try {
                    intent = builder.build(current);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String address = String.format("%s", place.getAddress());
                locationText.setText(address);
            }

        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            //resulf from user selection
            String username = data.getStringExtra("username");
            managerText.setText(username);

        }


    }
//end of mapping
    private void updateEndDisplay() {
        endDateText.setText(
                new StringBuilder()
                        .append(end_day).append("/")
                        .append(end_month + 1).append("/")
                        .append(end_year).append(" "));
    }
    private void updateStartDisplay() {
        startDateText.setText(
                new StringBuilder()
                        .append(start_day).append("/")
                        .append(start_month + 1).append("/")
                        .append(start_year).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    start_year = year;
                    start_month= monthOfYear;
                    start_day = dayOfMonth;
                    updateStartDisplay();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {

        switch(id){
            case START_DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, start_year, start_month, start_day);
            case END_DATE_DIALOG_ID:
                return new DatePickerDialog(this, endDateSetListener, end_year, end_month, end_day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener endDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    end_year = year;
                    end_month = monthOfYear;
                    end_day = dayOfMonth;
                    updateEndDisplay();
                }
            };

    public boolean validDifference(Calendar startDate, Calendar endDate){

        Calendar y2000 = Calendar.getInstance();
        Calendar y2100 = Calendar.getInstance();
        y2000.set(2000,1,1);
        y2100.set(2100,12,31);

         if(startDate.before(y2000)) return false;
         if(endDate.before(y2000)) return false;
         if(endDate.after(y2100)) return false;
         if(endDate.after(y2100)) return false;
        return startDate.before(endDate);
    }


    public void selectManager(){
        Intent selectorIntent = new Intent(this,SelectUserActivity.class);
        if(Globals.role.equals("client"))
            selectorIntent.putExtra("type","manager");
        else if(Globals.role.equals("manager"))
            selectorIntent.putExtra("type","client");
        startActivityForResult(selectorIntent,2);


    }




}