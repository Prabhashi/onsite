package com.example.tjr.onsite.model;

/**
 * Created by Raviyaa on 2017-03-17.
 */

public class Task {

    private String taskName;
    private String description;
    private String startDate;
    private String estimatedEndDate;
    private String estimatedCost;

    public Task() {

    }

    public Task(String taskName, String description, String startDate, String estimatedEndDate, String estimatedCost) {
        this.taskName = taskName;
        this.description = description;
        this.startDate = startDate;
        this.estimatedEndDate = estimatedEndDate;
        this.estimatedCost = estimatedCost;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(String estimatedCost) {
        this.estimatedCost = estimatedCost;
    }
}
