package com.georgemausshardt.enums;

/**
 * All acceptable values (I could find at least) for the camera position on a Mars Rover.
 */
public enum CameraType {
    FHAZ ("Front Hazard Avoidance Camera"),
    RHAZ ("Rear Hazard Avoidance Camera"),
    MAST ("Mast Camera"),
    CHEMCAM ("Chemistry and Camera Couplex"),
    MAHLI ("Mars Hand Lens Imager"),
    MARDI ("Mars Descent Imager"),
    NAVCAM ("Navigation Camera"),
    PANCAM ( "Panoramic Camera"),
    MINITES ("Miniature Thermal Emission Spectrometer (Mini-TES");

    // Human-Readable Description for the acronyms used by NASA for its cameras.
    private final String description;

    CameraType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
