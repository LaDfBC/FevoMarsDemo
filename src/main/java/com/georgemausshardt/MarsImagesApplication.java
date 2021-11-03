package com.georgemausshardt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.georgemausshardt.enums.CameraType;
import com.georgemausshardt.enums.RoverType;
import com.georgemausshardt.service.MarsRoverImageService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

public class MarsImagesApplication {
    public static void main(String[] args) {
        String apiKey = args[0];

        try {
            // Good improvement to work on immediately.  Make these command line options with sensible defaults.
            SortedMap<String, List<String>> photos = MarsRoverImageService
                    .getImages(apiKey)
                    .withCamera(CameraType.NAVCAM)
                    .withRover(RoverType.CURIOSITY)
                    .withEarthDate(LocalDate.now())
                    .andLimitPerDay(3)
                    .andLimitPastDays(10)
                    .getPhotosByDay();


            ObjectMapper outputMapper = new ObjectMapper();
            outputMapper.writeValue(System.out, photos);
        } catch (IOException ioe) {
            // Not the best error handling.  Improvement would be to use the either pattern, figure out what failed,
            //  report that to the user, and log in Splunk or some other logging system!
            System.out.println(
                    "****FAILED****\n\n\n" +
                    "Encountered an exception, reproduced below." +
                    "Ideally this would be logged and reported but this will work for the demo:\n");
            ioe.printStackTrace();
        }
    }
}
