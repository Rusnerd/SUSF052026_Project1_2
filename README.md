# SUSF052026_Project1_2 - Travel Planner

## Tech Stack
- Java 21
- Spring Boot 3.4.0
- Spring MVC + Thymeleaf
- Spring Data JPA + MySQL/MariaDB
- Maven

## Domain Entities (5 total)
- PhotoTrip (main)
- Booking
- ItineraryItem
- (add SeaTrip / MountainTrip similarly)

## Functionalities (4+ valid)
1. Host CRUD PhotoTrip
2. Create / Cancel Booking (TRAVELER)
3. Manage Itinerary Items (GUIDE/HOST)
4. View My Bookings

## Security
Session-based login with roles (HOST, GUIDE, TRAVELER)

## Pages (8+)
- Home / photo-trips list
- Trip details
- Host create/edit
- Login / Register
- My Bookings
- Itinerary list + new

Run with: `./mvnw spring-boot:run`
