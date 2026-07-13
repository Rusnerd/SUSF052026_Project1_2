package app.service.exceptions;


public class InvalidTripDateRangeException extends RuntimeException {
    public InvalidTripDateRangeException(String message) {
        super(message);
    }
}
