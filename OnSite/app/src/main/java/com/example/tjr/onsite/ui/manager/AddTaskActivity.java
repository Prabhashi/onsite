package com.example.tjr.onsite.ui.manager;

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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddTaskActivity extends AppCompatActivity {
    private TextView nameText,descriptionText,startDateText,endDateText,estimatedCostText;
    private Button saveButton,cancelButton;
    int start_year, start_month, start_day, end_year, end_month, end_day;
    static final int START_DATE_DIALOG_ID = 0;
    static final int END_DATE_DIALOG_ID = 1;
    String server_url = Const.URL_PREFIX+"task/create";
    AlertDialog.Builder builder;
    String startDate,endDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        nameText = (TextView)findViewById(R.id.nameText);
        descriptionText = (TextView)findViewById(R.id.descriptionText);
        startDateText = (TextView)findViewById(R.id.startDateText);
        endDateText = (TextView)findViewById(R.id.endDateText);
        estimatedCostText = (TextView)findViewById(R.id.estimatedCostText);
        saveButton = (Button)findViewById(R.id.saveButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        builder = new AlertDialog.Builder(AddTaskActivity.this);
        //cancel
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddTaskActivity.this,ViewTaskActivity.class));
            }
        });
      //initializing the date picker
        startDateText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(START_DATE_DIALOG_ID);

            }
        });

        final Calendar c = Calendar.getInstance();
        start_year = c.get(Calendar.YEAR);
        start_month = c.get(Calendar.MONTH);
        start_day = c.get(Calendar.DAY_OF_MONTH);
        updateStartDateText();

        endDateText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(END_DATE_DIALOG_ID);

            }
        });
        //initializing the date picker
        final Calendar c1 = Calendar.getInstance();
        end_year = c1.get(Calendar.YEAR);
        end_month = c1.get(Calendar.MONTH);
        end_day = c1.get(Calendar.DAY_OF_MONTH);
        updateEndDateText();

        //inserting the data
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String taskName,description,estimatedCost;

                taskName = nameText.getText().toString();
                description = descriptionText.getText().toString();
                startDate = start_day+"/"+start_month+"/"+start_year;
                endDate = end_day+"/"+end_month+"/"+end_year;
                estimatedCost = estimatedCostText.getText().toString();



                if(taskName.isEmpty() || description.isEmpty()||startDate.isEmpty() || endDate.isEmpty() || estimatedCost.isEmpty()){
                    if(taskName.isEmpty()){nameText.setError("Enter a valid name here!");}
                    if(description.isEmpty()){descriptionText.setError("Enter a valid description here!");}
                    if(estimatedCost.isEmpty()){estimatedCostText.setError("Enter a valid cost here!");}
                   return;
                }

                final RequestQueue requestQueue = Volley.newRequestQueue(AddTaskActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        builder.setTitle("Server Response");
                        builder.setMessage("Task successfully added! Do you want to add another task?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(AddTaskActivity.this,AddTaskActivity.class));
                                finish();

                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               startActivity(new Intent(AddTaskActivity.this,ViewTaskActivity.class));
                                finish();
                            }
                        });


                        AlertDialog alertDialog = builder.show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                       // params.put("TaskID",String.valueOf(start_day+end_day));
                       params.put("projectId", String.valueOf(Globals.projectId));
                        params.put("taskName",taskName);
                        params.put("description",description);
                        params.put("startDate",startDate);
                        params.put("estimatedEndDate",endDate);
                        params.put("cost",estimatedCost);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });



    }
    private void updateEndDateText() {
        endDateText.setText(
                new StringBuilder()
                        .append(end_day).append("/")
                        .append(end_month + 1).append("/")
                        .append(end_year).append(" "));
    }
    private void updateStartDateText() {
        startDateText.setText(
                new StringBuilder()
                        .append(start_day).append("/")
                        .append(start_month + 1).append("/")
                        .append(start_year).append(" "));
    }

    private DatePickerDialog.OnDateSetListener startDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    start_year = year;
                    start_month= monthOfYear;
                    start_day = dayOfMonth;
                    updateStartDateText();
                }
            };
    private DatePickerDialog.OnDateSetListener endDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    end_year = year;
                    end_month = monthOfYear;
                    end_day = dayOfMonth;
                    updateEndDateText();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {

        switch(id){
            case START_DATE_DIALOG_ID:
                return new DatePickerDialog(this, startDateSetListener, start_year, start_month, start_day);
            case END_DATE_DIALOG_ID:
                return new DatePickerDialog(this, endDateSetListener, end_year, end_month, end_day);
        }
        return null;
    }

}
