package com.onsite.model;

import javax.persistence.*;

/**
 * Created by TJR on 12/30/2016.
 */
@Entity
public class DesignPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer planId;
    private String planName;
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    private String planImageUrl;
    private String description;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getPlanImageUrl() {
        return planImageUrl;
    }

    public void setPlanImageUrl(String planImageUrl) {
        this.planImageUrl = planImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
