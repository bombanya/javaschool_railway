# Railway Company Information System

## Description
The project simulates the information system of passenger railway company.
It was a main task of the Deutsche Telekom IT Solutions Java School.

The project consists of three applications:
- [Main server](https://github.com/bombanya/javaschool_railway)
- [Front app with pages for passengers and administration](https://github.com/bombanya/jschl_railway_front)
- [Automatically updated board with trains schedule](https://github.com/bombanya/jschl_railway_board)

---

## Main server

Provides the REST API for using the system.

### Functionality:
- Adding new and searching for existing cities and stations in the system
- Saving information about trains and detailed information about places 
(lying, with a socket, etc.)
- Saving new routes and trips for the trains
- Search for suitable trips by starting and ending station and desired departure day
- Ticket purchase
- Making updates to the train schedule (changing the station arrival/departure time) 
- Notifying the board about the schedule events through the message broker
- Separation into open public methods and closed methods that require authorization

### Realisation:
- Java 8
- Spring Core
- PostgreSQL and Flyway as the DB VCS
- Hibernate as a JPA provider for the DAO layer
- Spring Security with JWT
- Spring MVC for the REST API
- Spring JMS support and Active MQ Artemis broker for messaging

### Application Server:
- Tested on the WildFly 26.0.1 
