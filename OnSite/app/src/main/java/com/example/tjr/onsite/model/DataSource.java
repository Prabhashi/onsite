package com.example.tjr.onsite.model;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tjr.onsite.adapter.CustomAdapterList;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.ui.common.IssueHomeActivity;
import com.example.tjr.onsite.ui.common.IssuesActivity;
import com.example.tjr.onsite.ui.common.UserHomeActivity;
import com.example.tjr.onsite.ui.manager.ManagerViewTaskActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TJR on 2/3/2017.
 */

public class DataSource {
    public static final String[] names = {"Madness is like gravity", "All it takes", "Is a little push"};
    public static final int[] icons = {android.R.drawable.ic_popup_reminder};

    //change this to connect to server and populate the list with real data
    public static List<Project> getData() {
        List<Project> l = new ArrayList<Project>();
        for (int i = 0; i < 10; i++) {
            l.add(new Project(names[i % names.length], "" + i, "http://wallpaper-gallery.net/images/image/image-13.jpg"));
        }
        System.out.println(l);
        return l;
    }

    public static List<Project> getDataFromServer() {
        return null;
    }

    public static void retrieveProjectData(final ProjectsActivity context) {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, Const.URL_PREFIX + "/dummy.php", null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //make an array list from recieved jsonarray
                            ArrayList<Project> list = new ArrayList<>();
                            int lim = response.length();
                            for (int i = 0; i < lim; i++) {
                                JSONObject obj = response.getJSONObject(i);
                                Project project = new Project(obj.getString("name"), obj.getString("issues"), obj.getString("location"));
                                project.setId(obj.getInt("id"));
                                project.setImageUrl(obj.getString("url"));
                                list.add(project);
                            }
                            context.updateProjectList(list);

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

    //projects involved
    public static void projectsInvolved(final ViewProjectActivity context) {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, Const.URL_PREFIX + "project/get/"+ Globals.userId, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //make an array list from recieved jsonarray
                            ArrayList<Project> list = new ArrayList<>();
                            int lim = response.length();
                            for (int i = 0; i < lim; i++) {
                                JSONObject obj = response.getJSONObject(i);
                                Project project = new Project();
                                project.setId(obj.getInt("projectId"));
                                project.setName(obj.getString("projectName"));
                                project.setCompanyName(obj.getString("companyName"));
                                project.setLocation(obj.getString("location"));
                                project.setStartDate(obj.getString("startDate"));
                                project.setEstimatedCompletionDate(obj.getString("estimatedCompletionDate"));
                                list.add(project);
                            }
                            //context.updateProjectList(list);

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
    // /
    public static void retrieveIssueData(final IssuesActivity context, int projectId) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, Const.URL_PREFIX+"/issues/get/all/"+projectId, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<Issue> list = new ArrayList<>();
                            int lim = response.length();

                            for (int j = 0;j < lim; j++) {
                                JSONObject object = response.getJSONObject(j);
                                Issue i = new Issue();
                                i.issueId = object.getInt("issueId");
                                i.name = object.getString("issueTitle");
                                i.severity = object.getString("status");
                                i.comments = 0;// object.getInt("comments");
                                i.severity = object.getString("severity");
                                switch (i.severity){
                                    case "high": i.severityInt = 3;break;
                                    case "medium": i.severityInt = 2; break;
                                    case "low" : i.severityInt = 1;break;
                                }
                                i.resolved = object.getString("status");
                                String dateStr = object.getString("reportedDate");
                                String [] tokens = dateStr.split("-");
                                Date d = new Date(Integer.parseInt(tokens[0]),
                                        Integer.parseInt(tokens[1]),
                                        Integer.parseInt(tokens[2]));

                                i.dateReported = d;
                                i.type = object.getString("issueType");
                                JSONArray arr = object.getJSONArray("imageUrls");

                                if(arr.length() >0) {
                                    String imageUrl = arr.getString(0);
                                    i.imageUrl = imageUrl.replace("localhost", Const.BASE_IP);

                                }
                                list.add(i);
                            }
                            context.updateIssueList(list);
                        } catch (JSONException e) {
                            System.out.println(e);
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


    public static void updateUserList(final CustomAdapterList adapter,  String match) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, Const.URL_PREFIX+"/user/like/"+match, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<User> list = new ArrayList<>();
                            int lim = response.length();

                            for (int j = 0;j < lim; j++) {
                                JSONObject object = response.getJSONObject(j);
                                User u = new User();
                                u.fullName = object.getString("fullName");
                                u.username = object.getString("username");
                                u.imageUrl = object.getString("profilePicUrl");

                                list.add(u);
                            }
                           // adapter.mCustomers = list;
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            System.out.println(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        jsonArrayRequest.setTag("TAG");
        MyVolley.getRequestQueue().add(jsonArrayRequest);
    }

    public static void retrieveIssueData(final IssueHomeActivity context, final int issueId) {
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
                (Request.Method.GET, Const.URL_PREFIX+"/issues/get/"+issueId, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        DetailedIssue issue = new DetailedIssue();

                        try {
                            int lim = response.length();
                            issue.issueId = response.getInt("issueId");
                            issue.name= response.getString("issueTitle");
                            issue.date = response.getString("reportedDate");
                            issue.severity = response.getString("severity");
                            issue.state = response.getString("status");
                            issue.description = response.getString("description");
                            issue.reporterId = response.getJSONObject("reporter").getString("id");
                            issue.reporterImageUrl = response.getJSONObject("reporter").getString("profilePicUrl");
                            issue.reporterName =  response.getJSONObject("reporter").getString("fullName");;

                            JSONArray commentObjects = response.getJSONArray("comments");
                            int commentLimit = commentObjects.length();
                            for (int i = 0; i < commentLimit; i++) {
                                JSONObject obj = commentObjects.getJSONObject(i);
                                JSONObject commentor = obj.getJSONObject("commentor");
                                Comment c = new Comment();
                                c.commentBody = obj.getString("commentBody");
                                c.commentorName = commentor.getString("fullName");
                                


                                issue.comments.add(c);
                            }

                            //retrieve issue tags
                            JSONArray tags = response.getJSONArray("tags");
                            int tagLimit = tags.length();
                            for(int i=0; i<tagLimit; i++){
                                String tag = tags.getString(i);
                                issue.tags.add(tag);
                            }

                            //retrieve additional images
                            JSONArray images = response.getJSONArray("imageUrls");

                            int imgLimit = images.length();
                            issue.images = new ArrayList<>();
                            for(int i=0 ; i<imgLimit; i++){
                                issue.images.add(images.getString(i).replace("localhost",Const.BASE_IP));
                            }


                            JSONObject plan = response.getJSONObject("plan");
                            issue.planId = plan.getInt("planId");
                            issue.planImageUrl = plan.getString("planImageUrl").replace("localhost",Const.BASE_IP);
                            issue.locationX =(float) response.getDouble("locationX");
                            issue.locationY =(float) response.getDouble("locationY");



                            context.updateIssueData(issue);
                        } catch (JSONException e) {
                            System.out.println(e);
                            context.updateIssueData(issue);
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

    public static void retrieveUserData(final UserHomeActivity context, int userId) {
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
                (Request.Method.GET, Const.URL_PREFIX + "/", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        User user = new User();

                        try {
                            user.email = response.getString("email");
                            user.fullName = response.getString("fullName");
                            user.imageUrl = response.getString("imageUrl");
                            user.phone = response.getString("phone");


                            context.updateUserData(user);
                        } catch (JSONException e) {
                            System.out.println(e);
                            context.updateUserData(user);
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

    public static void getAllTask(final ManagerViewTaskActivity context) {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET,  Const.URL_PREFIX + "/task/all/1", null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //make an array list from recieved jsonarray
                            ArrayList<Task> list = new ArrayList<>();
                            int lim = response.length();
                            for (int i = 0; i < lim; i++) {
                                JSONObject obj = response.getJSONObject(i);
                                Task task = new Task();
                                task.setTaskName(obj.getString("taskName"));
                                task.setDescription(obj.getString("description"));
                                task.setStartDate(obj.getString("startDate"));
                                task.setEstimatedEndDate(obj.getString("estimatedEndDate"));
                                task.setEstimatedCost(obj.getString("cost"));
                                list.add(task);
                            }
                            context.taskAdapter.tasks.addAll(list);
                         context.taskAdapter.notifyDataSetChanged();

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
    //

}
