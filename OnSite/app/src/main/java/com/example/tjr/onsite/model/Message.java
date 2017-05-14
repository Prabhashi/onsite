package com.example.tjr.onsite.model;

/**
 * Created by Raviyaa on 2017-03-22.
 */

public class Message {
    public Integer sender;
    public String recevier;
    public String body;
    public String timeStamp;

    public Message(Integer sender, String recevier, String body, String timeStamp) {
        this.sender = sender;
        this.recevier = recevier;
        this.body = body;
        this.timeStamp = timeStamp;
    }

    public Message() {

    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public String getRecevier() {
        return recevier;
    }

    public void setRecevier(String recevier) {
        this.recevier = recevier;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
