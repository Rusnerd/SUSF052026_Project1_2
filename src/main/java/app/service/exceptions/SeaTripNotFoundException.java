package app.service.exceptions;

import java.util.UUID;

public class SeaTripNotFoundException extends RuntimeException {
    public SeaTripNotFoundException(UUID id) {
    }
}
