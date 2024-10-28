# Credit Card API

## Description
The Credit Card API is a Spring Boot application that provides endpoints for managing credit card applications. It includes features such as creating, updating, retrieving, and deleting credit card applications.

## Technologies Used
- Java 17
- Spring Boot 3.3.5
- Spring Data JDBC
- Spring Security
- Liquibase
- Maven
- H2 Database (for testing)
- Lombok
- MapStruct

## Getting Started

### Prerequisites
- Java 17
- Maven

### Installation
1. Clone the repository:
    ```sh
    git clone git@github.com:Mario173/credit-card-api.git
    cd credit-card-api
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```

### Running the Application
To run the application, use the following command:
```sh
mvn spring-boot:run
```

### Running Tests
To run the unit tests, use the following command:
```sh
mvn test
```

## Database
The application uses Liquibase for database migrations. The changelog files are located in the src/main/resources/db/changelog directory.

## Endpoints
The API provides the following endpoints:  

- <b style="color:orange;">GET /credit/card/applications</b> - Retrieve all credit card applications
- <b style="color:orange;">GET /credit/card/applications/{id}</b> - Retrieve a specific credit card application by ID
- <b style="color:orange;">POST /credit/card/applications</b> - Create a new credit card application
- <b style="color:orange;">PUT /credit/card/applications/{id}</b> - Update an existing credit card application by ID
- <b style="color:orange;">DELETE /credit/card/applications/{id}</b> - Delete a credit card application by ID
- <b style="color:orange;">POST /credit/card/applications/send-card-request/{id}</b> - Find a credit card application by ID and send a card request

## Validation
The application includes validation to ensure that the personal ID in the path and the request body match. If they do not match, an error is logged and an IllegalArgumentException is thrown.

## License
This project is licensed under the MIT License.