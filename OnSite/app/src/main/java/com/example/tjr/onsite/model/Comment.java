package com.example.tjr.onsite.model;

/**
 * Created by TJR on 2/24/2017.
 */

public class Comment {
    public String commentorName;
    public String imageUrl;
    public String commentBody;
    public String commenterId;
    public Comment(String commentorName, String imageUrl, String commentBody) {
        this.commentorName = commentorName;
        this.imageUrl = imageUrl;
        this.commentBody = commentBody;
    }

    public Comment() {

    }
}
