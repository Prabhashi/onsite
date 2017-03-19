package com.example.tjr.onsite.ui.manager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerViewProjectActivity extends AppCompatActivity {
    private String TAG=ManagerViewProjectActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private ListView listView;
    private String projectID;
    ListAdapter adapter;
    private FloatingActionButton floatingActionButton;
    //String server_url = Const.URL_PREFIX+"project/invloved/"+ Globals.userId;
    ArrayList<HashMap<String, String>> projectList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project_manager);

        projectList = new ArrayList<>();
        listView = (ListView)findViewById(R.id.list);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Globals.projectId=ids.get(i);
                Toast.makeText(getApplicationContext(),"Clicked Projct ID : "+Globals.projectId,Toast.LENGTH_LONG).show();
                //TODO: suitable dashboard for manager
                //startActivity(new Intent(ManagerViewProjectActivity.this,DashBoardActivity.class));
            }
        });

        projectsInvolved();
    }

    //projects involved
    public  void projectsInvolved() {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, Const.URL_PREFIX + "project/involved/"+Globals.userId, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Project project = new Project();
                        try {
                            //make an array list from recieved jsonarray

                            projectList = new ArrayList<>();

                            int lim = response.length();
                            for (int i = 0; i < lim; i++) {
                                JSONObject obj = response.getJSONObject(i);

                                HashMap<String ,String> m = new HashMap<>();
                                ids.add(obj.getInt("projectId"));
                                m.put("projectName", obj.getString("projectName"));
                                m.put("companyName", obj.getString("companyName"));
                                m.put("location", obj.getString("location"));
                                m.put("startDate", obj.getString("startDate"));
                                m.put("estimatedCompletionDate", obj.getString("estimatedCompletionDate"));

                                projectList.add(m);
                            }

                            ListAdapter adapter = new SimpleAdapter(
                                    ManagerViewProjectActivity.this, projectList,
                                    R.layout.list_project, new String[]{"projectName","companyName","location",
                                    "startDate","estimatedCompletionDate"}, new int[]{R.id.proName,
                                    R.id.comName, R.id.loc, R.id.startDate, R.id.endDate});

                            listView.setAdapter(adapter);

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
    ArrayList<Integer> ids = new ArrayList<>();
}
