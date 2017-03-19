package com.example.tjr.onsite.model;

import com.example.tjr.onsite.app.Const;

/**
 * Created by TJR on 2/3/2017.
 */

public class Project {
    public String name;
    public String issues;
    public String imageUrl;
    public String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEstimatedCompletionDate() {
        return estimatedCompletionDate;
    }

    public void setEstimatedCompletionDate(String estimatedCompletionDate) {
        this.estimatedCompletionDate = estimatedCompletionDate;
    }

    private long id;
    private String companyName;
    private String startDate;
    private String estimatedCompletionDate;
    public Project() {
    }

    public Project(String name, String issues, String location) {
        this.name = name;
        this.issues = issues;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = Const.URL_PREFIX + imageUrl;
    }
}
