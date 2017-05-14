package com.onsite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by TJR on 12/31/2016.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {
    @Id
    @GeneratedValue
    private Integer issueId;

    @OneToOne(fetch = FetchType.LAZY)
    private User reporter;

    @OneToOne(fetch = FetchType.LAZY)
    private User assignee;



    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    private String issueTitle;
    private String description;
    private String issueType;
    private String severity;
    private String status;


    @ElementCollection
    private List<String> imageUrls;

    @ElementCollection
    private List<String> tags;

    @OneToMany
    private List<Comment> comments;

    @OneToOne
    private DesignPlan plan;

    @Temporal(TemporalType.TIME)
    private Date reportedDate;


    private Float locationX;
    private Float locationY;


    private Boolean assigneeAccepted;

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public Integer getIssueId() {

        return issueId;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getLocationX() {
        return locationX;
    }

    public void setLocationX(Float locationX) {
        this.locationX = locationX;
    }

    public Float getLocationY() {
        return locationY;
    }

    public void setLocationY(Float locationY) {
        this.locationY = locationY;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getReportedDate() {
        return reportedDate;
    }

    public DesignPlan getPlan() {
        return plan;
    }

    public void setPlan(DesignPlan plan) {
        this.plan = plan;
    }

    public void setReportedDate(Date reportedDate) {
        this.reportedDate = reportedDate;
    }

    public Boolean getAssigneeAccepted() {
        return assigneeAccepted;
    }

    public void setAssigneeAccepted(Boolean assigneeAccepted) {
        this.assigneeAccepted = assigneeAccepted;
    }
}