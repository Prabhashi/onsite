package com.example.tjr.onsite.model;

import java.util.Date;

/**
 * Created by TJR on 2/23/2017.
 */

public class Issue {
    public int issueId;
    public String name;
    public String severity;
    public String type;
    public int comments;
    public String resolved;
    public String imageUrl;

    public Date dateReported;
    public Integer severityInt;

    public Issue() {
    }

    public Issue(String name, String severity, int comments, String resolved, String imageUrl) {
        this.name = name;
        this.severity = severity;
        this.comments = comments;
        this.resolved = resolved;
        this.imageUrl = imageUrl;
    }
}
