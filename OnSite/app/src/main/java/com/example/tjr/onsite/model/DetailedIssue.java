package com.example.tjr.onsite.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TJR on 2/24/2017.
 */

public class DetailedIssue {
    public String name;
    public String imgUrl;
    public String date;
    public String severity;
    public String state;
    public String description;
    public String reporterId;
    public String reporterImageUrl;
    public String reporterName;
    public List<Comment> comments = new ArrayList<>();
    public List<String> tags = new ArrayList<>();
    public List<String> images = new ArrayList<>();
    public int issueId;
    public String planImageUrl;
    public float locationX,locationY;
    public int planId;

    public DetailedIssue() {
    }


}
