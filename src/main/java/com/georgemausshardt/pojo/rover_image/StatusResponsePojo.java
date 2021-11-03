package com.georgemausshardt.pojo.rover_image;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * One JSON value in the {@link RoverResponsePojo} JSON object returned by the NASA Mars Image API.  This value
 * indicates whether the rover is active or inactive.
 */
public enum StatusResponsePojo {
    ACTIVE("active"),
    INACTIVE("inactive");

    @JsonValue
    private final String statusString;

    StatusResponsePojo(final String statusString) {
        this.statusString = statusString;
    }


}
