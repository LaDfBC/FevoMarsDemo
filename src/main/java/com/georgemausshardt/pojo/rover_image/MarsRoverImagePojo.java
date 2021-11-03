package com.georgemausshardt.pojo.rover_image;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Represents one image and the metadata about that image of the surface of Mars as taken by a NASA rover.  This is the
 * JSON format used by NASA to return that information from its Mars Imaging API.
 */
public class MarsRoverImagePojo {
    private final long id;
    private final long sol;
    private final CameraResponsePojo camera;
    private final String imgSrc;
    private final Date earthDate;
    private final RoverResponsePojo rover;

    @JsonCreator
    public MarsRoverImagePojo(@JsonProperty("id") long id,
                              @JsonProperty("sol") long sol,
                              @JsonProperty("camera") CameraResponsePojo camera,
                              @JsonProperty("img_src") String imgSrc,
                              @JsonProperty("earth_date") Date earthDate,
                              @JsonProperty("rover") RoverResponsePojo rover) {
        this.id = id;
        this.sol = sol;
        this.camera = camera;
        this.imgSrc = imgSrc;
        this.earthDate = earthDate;
        this.rover = rover;
    }

    public long getId() {
        return id;
    }

    public long getSol() {
        return sol;
    }

    public CameraResponsePojo getCamera() {
        return camera;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public Date getEarthDate() {
        return earthDate;
    }

    public RoverResponsePojo getRover() {
        return rover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarsRoverImagePojo that = (MarsRoverImagePojo) o;

        if (id != that.id) return false;
        if (sol != that.sol) return false;
        if (!camera.equals(that.camera)) return false;
        if (!imgSrc.equals(that.imgSrc)) return false;
        if (!earthDate.equals(that.earthDate)) return false;
        return rover.equals(that.rover);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (sol ^ (sol >>> 32));
        result = 31 * result + camera.hashCode();
        result = 31 * result + imgSrc.hashCode();
        result = 31 * result + earthDate.hashCode();
        result = 31 * result + rover.hashCode();
        return result;
    }
}
