package com.onsite.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by TJR on 12/30/2016.
 */
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "sender")
    private User sender;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "reciever")
    private User reciever;

    private String messageBody;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReciever() {
        return reciever;
    }

    public void setReciever(User reciever) {
        this.reciever = reciever;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date sentTime;

}
