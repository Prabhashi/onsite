package com.example.tjr.onsite.model.json;



/**
 * Created by TJR on 12/30/2016.
 */

public class Project{
    private Integer projectId;
    private String projectName;
    private String companyName;
    private String location;

    private String startDate;

    private String endDate;

    private String estimatedCompletionDate;

    private User manager;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEstimatedCompletionDate() {
        return estimatedCompletionDate;
    }

    public void setEstimatedCompletionDate(String estimatedCompletionDate) {
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
