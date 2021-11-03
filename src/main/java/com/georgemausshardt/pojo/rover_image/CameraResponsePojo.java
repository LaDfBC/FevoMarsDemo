package com.georgemausshardt.pojo.rover_image;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the JSON Object "camera" returned by the NASA Mars Image API.
 *
 *  Fits into the overarching JSON response in which {@link MarsRoverImageResponse} is the toplevel parent.
 */
public class CameraResponsePojo {
    private final long id;
    private final String name;
    private final int roverId;
    private final String fullName;

    @JsonCreator
    public CameraResponsePojo(@JsonProperty("id") long id,
                              @JsonProperty("name") String name,
                              @JsonProperty("rover_id") int roverId,
                              @JsonProperty("full_name") String fullName) {
        this.id = id;
        this.name = name;
        this.roverId = roverId;
        this.fullName = fullName;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRoverId() {
        return roverId;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CameraResponsePojo camera = (CameraResponsePojo) o;

        if (id != camera.id) return false;
        if (roverId != camera.roverId) return false;
        if (!name.equals(camera.name)) return false;
        return fullName.equals(camera.fullName);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + roverId;
        result = 31 * result + fullName.hashCode();
        return result;
    }
}