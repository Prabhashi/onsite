package com.onsite.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by TJR on 12/30/2016.
 */
@Entity
public class Location {
    @Id
    private Integer locationId;

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }
}
