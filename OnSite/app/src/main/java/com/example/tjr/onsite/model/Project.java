package com.example.tjr.onsite.model;

import com.example.tjr.onsite.app.Const;

/**
 * Created by Raviyaa on 2/3/2017.
 */

public class Project {

    public String name;
    public String issues;
    public String imageUrl;
    public String location;
    public int id;
    public String companyName;
    public String startDate;
    public String estimatedCompletionDate;
    public String description;


    public String client;
    public User manager=new User();
    public boolean managerAccepted;

    public Project() {
    }

    public Project(String name, String issues, String location) {
        this.name = name;
        this.issues = issues;
        this.location = location;
    }
}
