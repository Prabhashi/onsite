package com.onsite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by TJR on 12/30/2016.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project implements Serializable{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Integer projectId;
    private String projectName;
    private String companyName;
    private String location;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Temporal(TemporalType.DATE)
    private Date estimatedCompletionDate;

    @OneToOne
    private User manager;
    @OneToOne
    private User client;

    private Boolean managerAccepted;
    private Boolean clientAccepted;


    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;

        Project project = (Project) o;

        return getProjectId().equals(project.getProjectId());
    }

    @Override
    public int hashCode() {
        return getProjectId().hashCode();
    }

    public Integer getProjectId() {
        return projectId;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEstimatedCompletionDate() {
        return estimatedCompletionDate;
    }

    public void setEstimatedCompletionDate(Date estimatedCompletionDate) {
        this.estimatedCompletionDate = estimatedCompletionDate;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Boolean getManagerAccepted() {
        return managerAccepted;
    }

    public void setManagerAccepted(Boolean managerAccepted) {
        this.managerAccepted = managerAccepted;
    }

    public Boolean getClientAccepted() {
        return clientAccepted;
    }

    public void setClientAccepted(Boolean clientAccepted) {
        this.clientAccepted = clientAccepted;
    }
}
