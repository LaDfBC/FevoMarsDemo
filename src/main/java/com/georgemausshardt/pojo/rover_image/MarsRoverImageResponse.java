package com.georgemausshardt.pojo.rover_image;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The "toplevel" JSON object returned from the NASA Mars Image API.  Contains a list of zero-or-more photos; any and all
 * of them that matched the request parameters to the API.
 */
public class MarsRoverImageResponse {
    private final List<MarsRoverImagePojo> photos;

    @JsonCreator
    public MarsRoverImageResponse(@JsonProperty("photos") List<MarsRoverImagePojo> photos) {
        this.photos = photos;
    }

    public List<MarsRoverImagePojo> getPhotos() {
        return photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarsRoverImageResponse that = (MarsRoverImageResponse) o;

        return photos.equals(that.photos);
    }

    @Override
    public int hashCode() {
        return photos.hashCode();
    }
}
