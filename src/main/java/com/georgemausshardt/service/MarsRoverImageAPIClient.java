package com.georgemausshardt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.georgemausshardt.cache.ApiCache;
import com.georgemausshardt.cache.InMemoryApiCache;
import com.georgemausshardt.enums.CameraType;
import com.georgemausshardt.enums.RoverType;
import com.georgemausshardt.pojo.rover_image.MarsRoverImageResponse;
import org.jetbrains.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Client that wraps the NASA Mars Rover Imagery API.  This sets up, runs, and converts to a nice Java Object the response
 * without any of the work surrounding the usual frustrations in making API calls.
 *
 * Simply use the {@link MarsRoverImageService#getImages method to pull this data!}  See {@link MarsRoverImageService} if you
 * want to pull for more than one day or if you want to change up the requests or do some aggregation.
 */
public class MarsRoverImageAPIClient extends NasaApi {
    private static final String MARS_IMAGE_API = NasaApi.NASA_BASE_URL + "mars-photos/api/v1/rovers/";
    
    // FUTURE IMPROVEMENT: Inject this instead of creating it here.
    private final ApiCache<ImageAPIRequest, MarsRoverImageResponse> cache = new InMemoryApiCache<>();

    public MarsRoverImageAPIClient() { }

    /**
     * Fetches all of the images stored by one specific Rover using one specific type of Camera on one Rover for one
     *  specific Earth Day.  The Api Key parameter is your own key and can be created here: https://api.nasa.gov/
     *
     * @param apiKey Your API key as mentioned above.
     * @param camera Specific type of camera whose images you want to fetch.  See {@link CameraType} for options.
     * @param earthDate Earth Date - one specific day.
     * @param rover The rover that produced the images you want to fetch.  See {@link RoverType} for supported rovers.
     * @return A pojo representing the object returned from the NASA API.  Contains every piece of metadata so you can trim
     *  it down or aggregate it at a higher layer in this code if you want.
     * @throws IOException - Generic Exception for anything that could go wrong.  THIS IS BAD BUT I'M OUT OF TIME!  SHOULD
     *  HANDLE EACH ERROR SEPARATELY!
     */
    public MarsRoverImageResponse getImages(String apiKey, CameraType camera, LocalDate earthDate, RoverType rover) throws IOException {
        // If more time: Validate Camera Type for Rover requested.  We can send up an error message if they attempted to
        //  use a camera on a rover where that camera does not exist.

        ImageAPIRequest request = new ImageAPIRequest(camera, earthDate, rover);

        // This is the textbook case for the Either pattern (A class that contains either the value, or any errors,
        // and exposes ways to interact with either case.)  Not enough time to create the framework around it
        //  so we're just bubbling errors up in this implementation
        if (cache.get(request).isPresent()) {
            return cache.get(request).get();
        } else {
            URL url = request.buildUrl(apiKey);
            HttpURLConnection connection;

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            StringBuilder responseBuilder = new StringBuilder();
            while ((inputLine = response.readLine()) != null) {
                responseBuilder.append(inputLine);
            }

            response.close();

            return cache.put(request, decodeJson(responseBuilder.toString()));
        }
    }

    /**
     * Decodes the JSON Response from the NASA API into a Java Object.
     *
     * @param json The JSON returned from the NASA API.
     * @return The decoded JSON as a Java Object
     * @throws JsonProcessingException If decoding failed for whatever reason.
     */
    private MarsRoverImageResponse decodeJson(final String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, MarsRoverImageResponse.class);
    }

    /**
     * Internal class that is used as both a Key for the Cache and to provide some helper methods.
     */
    @VisibleForTesting
    protected static class ImageAPIRequest {
        private final CameraType camera;
        private final LocalDate earthDate;
        private final RoverType rover;

        public ImageAPIRequest(CameraType camera, LocalDate earthDate, RoverType rover) {
            this.camera = camera;
            this.earthDate = earthDate;
            this.rover = rover;
        }

        public CameraType getCamera() {
            return camera;
        }

        public LocalDate getEarthDate() {
            return earthDate;
        }

        public RoverType getRover() {
            return rover;
        }

        /**
         * Builds the URL that this class will hit as an API endpoint to get the Rover Image data.
         *
         * @param apiKey Your API key.  See explanation on {@link MarsRoverImageAPIClient#getImages for more information.}
         * @return The fully formed URL
         * @throws MalformedURLException if the String that we're trying to use to form a URL is incorrect for some reason.
         */
        @VisibleForTesting
        protected URL buildUrl(String apiKey) throws MalformedURLException {
            // This could be a String since we're not storing it in between appends but StringBuilder just looks cleaner.
            StringBuilder urlBuilder = new StringBuilder(MARS_IMAGE_API)
                    .append(rover.name().toLowerCase())
                    .append("/photos?earth_date=").append(MarsRoverImageService.API_DATE_FORMAT.format(earthDate))
                    .append("&camera=").append(camera.name().toLowerCase())
                    .append("&api_key=").append(apiKey);

            return new URL(urlBuilder.toString());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ImageAPIRequest that = (ImageAPIRequest) o;
            return camera == that.camera && Objects.equals(earthDate, that.earthDate) && rover == that.rover;
        }

        @Override
        public int hashCode() {
            return Objects.hash(camera, earthDate, rover);
        }
    }
}
