package app.service.exceptions;

import java.util.UUID;

public class PhotoTripNotFoundException extends RuntimeException {
    public PhotoTripNotFoundException(UUID id) {
        super("Photo trip not found: " + id);
    }
}
