package com.georgemausshardt.pojo.rover_image;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Represents the JSON Object "rover" returned by the NASA Mars Image API.
 *
 * Fits into the overarching JSON response in which {@link MarsRoverImageResponse} is the toplevel parent.
 */
public class RoverResponsePojo {
    private final int id;
    private final String name;
    private final Date landingDate;
    private final Date launchDate;
    private final StatusResponsePojo status;

    public RoverResponsePojo(@JsonProperty("id") int id,
                             @JsonProperty("name") String name,
                             @JsonProperty("landing_date") Date landingDate,
                             @JsonProperty("launch_date") Date launchDate,
                             @JsonProperty("status") StatusResponsePojo status) {
        this.id = id;
        this.name = name;
        this.landingDate = landingDate;
        this.launchDate = launchDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getLandingDate() {
        return landingDate;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public StatusResponsePojo getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoverResponsePojo rover = (RoverResponsePojo) o;

        if (id != rover.id) return false;
        if (!name.equals(rover.name)) return false;
        if (!landingDate.equals(rover.landingDate)) return false;
        if (!launchDate.equals(rover.launchDate)) return false;
        return status == rover.status;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + landingDate.hashCode();
        result = 31 * result + launchDate.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }
}
