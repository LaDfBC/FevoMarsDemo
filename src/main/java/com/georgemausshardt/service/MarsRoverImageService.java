package com.georgemausshardt.service;

import com.georgemausshardt.enums.CameraType;
import com.georgemausshardt.enums.RoverType;
import com.georgemausshardt.pojo.rover_image.MarsRoverImagePojo;
import com.georgemausshardt.pojo.rover_image.MarsRoverImageResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Wraps {@link MarsRoverImageAPIClient} to aggregate data, perform business logic, filter results, or any number of
 *  other things.  HTTP Request logic should go in that class instead while handling the results or using them for other
 *  purposes should go here instead.
 */
public class MarsRoverImageService {
    // The yyyy-MM-dd pattern is shared across a few of the APIs so it makes sense to put it here.
    public static final DateTimeFormatter API_DATE_FORMAT = DateTimeFormatter
            .ofPattern("yyyy-MM-dd")
            .withZone(ZoneId.systemDefault());

    public static MarsRoverImageServiceRequest getImages(String apiKey) {
        return new MarsRoverImageServiceRequest(apiKey);
    }

    /**
     * Fluid API class used heavily to build the type of request to make and the options for making it.
     */
    public static class MarsRoverImageServiceRequest {
        private CameraType camera;
        private LocalDate earthDate;
        private String apiKey;
        private RoverType rover;

        private int photosPerDay = 3;
        private int lastDaysCount = 10;

        private MarsRoverImageServiceRequest(String apiKey) {
            this.apiKey = apiKey;
        }

        public MarsRoverImageServiceRequest withCamera(CameraType camera) {
            this.camera = camera;
            return this;
        }

        public MarsRoverImageServiceRequest withRover(RoverType rover) {
            this.rover = rover;
            return this;
        }

        public MarsRoverImageServiceRequest withEarthDate(LocalDate earthDate) {
            this.earthDate = earthDate;
            return this;
        }

        public MarsRoverImageServiceRequest andLimitPerDay(int photosPerDay) {
            this.photosPerDay = photosPerDay;
            return this;
        }

        public MarsRoverImageServiceRequest andLimitPastDays(int lastDaysCount) {
            this.lastDaysCount = lastDaysCount;
            return this;
        }

        /**
         * Aggregates photos by day.  Builds a Map of Day (yyyy-MM-DD) -> List of Image URLs and holds that map in
         *  reverse sorted order so the most recent day is shown first in the output.
         *
         * @return The map as described above of Date -> Images.  Sorted in decreasing chronological order.
         * @throws IOException - Still not good error handling.  See {@link MarsRoverImageAPIClient#getImages} for
         *  what I want to do instead.
         */
        public SortedMap<String, List<String>> getPhotosByDay() throws IOException {
            MarsRoverImageAPIClient api = new MarsRoverImageAPIClient();
            SortedMap<String, List<String>> photosToReturn = new TreeMap<>(Comparator.reverseOrder());

            for(int i = 0; i < lastDaysCount; i++) {
                LocalDate dateToRun = earthDate.minusDays(i);
                MarsRoverImageResponse currentDayResponse = api.getImages(apiKey, camera, dateToRun, rover);
                photosToReturn.put(
                        API_DATE_FORMAT.format(dateToRun),
                        currentDayResponse
                                .getPhotos()
                                .stream()
                                .limit(photosPerDay)
                                .map(MarsRoverImagePojo::getImgSrc)
                                .collect(Collectors.toList()));
            }

            return photosToReturn;
        }
    }
}
