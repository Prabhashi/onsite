package com.onsite.model;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by TJR on 3/10/2017.
 */

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer taskId;

    @OneToOne
    private User assignee;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Task> predecessors;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    private String taskName;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date estimatedEndDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

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

    public List<Task> getPredecessors() {
        return predecessors;
    }

    public void setPredecessors(List<Task> predecessors) {
        this.predecessors = predecessors;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEstimatedEndDate() {
        return estimatedEndDate;
    }

    public void setEstimatedEndDate(Date estimatedEndDate) {
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
}
