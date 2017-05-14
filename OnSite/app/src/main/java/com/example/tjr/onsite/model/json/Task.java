package com.example.tjr.onsite.model.json;

/**
 * Created by TJR on 3/10/2017.
 */

public class Task {


    private Integer taskId;

    private User assignee;

    private Project project;

    private String taskName;

    private String startDate;

    private String estimatedEndDate;

    private String endDate;

    private Double cost;

    private Boolean completed;

    private  String description;

    private Boolean assigneeAccepted;




    public Task() {
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEstimatedEndDate() {
        return estimatedEndDate;
    }

    public void setEstimatedEndDate(String estimatedEndDate) {
        this.estimatedEndDate = estimatedEndDate;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isAssigneeAccepted() {
        return assigneeAccepted;
    }

    public void setAssigneeAccepted(Boolean assigneeAccepted) {
        this.assigneeAccepted = assigneeAccepted;
    }

    public void setAssignee(String string) {
    }
}
