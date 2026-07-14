package app.model.enums;

public enum UserRole {
    GUEST,      // default for unauthenticated
    TRAVELER,
    HOST,       // can create/edit/delete trips (Photo/Sea/Mountain)
    GUIDE;      // can manage itinerary items
}