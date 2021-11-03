package com.georgemausshardt.service;

import com.georgemausshardt.enums.CameraType;
import com.georgemausshardt.enums.RoverType;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;

/**
 * Obviously incomplete testing.  Ran out of time and had to stop where I was at.
 */
public class MarsRoverImageAPIClientTest {
    @Test
    public void testBuildUrl_correctlyReturnsAPIEndpoint() throws MalformedURLException {
        MarsRoverImageAPIClient.ImageAPIRequest request =
                new MarsRoverImageAPIClient.ImageAPIRequest(
                        CameraType.CHEMCAM,
                        LocalDate.of(2020, Month.FEBRUARY, 3),
                        RoverType.OPPORTUNITY);

        assertEquals(
                new URL(
                        "https://api.nasa.gov/mars-photos/api/v1/rovers/opportunity/photos?earth_date=2020-02-03&camera=chemcam&api_key=FAKE_API_KEY"),
                request.buildUrl("FAKE_API_KEY"));
    }
}
