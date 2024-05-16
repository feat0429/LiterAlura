# LiterAlura
Console application created as part of the ONE - Oracle Next Education bootcamp provided by Alura Latam. The project was completed following the requirements provided by 
Alura to build an console applications that retrieves book data from an external API, persists the data in a PostgreSQL database and returns the stored data.
The book data was retrieved from [Gutendex](https://gutendex.com/ "Gutendex API documentation").

## Features
- Book search through Gutendex API.
- Persistance of searched books.
- Get all stored books and authors.
- Get stored living authors
- Get stored books by language
- Get stored books by author
- Get top 10 most downloaded stored books

## Technologies and packages used

- Java JDK 21.0.2
- Jackson Databind 2.17.1
- Spring Boot 3.2.5
- PostgreSQL JDBC Driver
- Spring Boot Starter Data JPA
- PostgreSQL 16.3

## Running the application
1. Clone or download the project repository.
2. In the **src/main/resources/application.properties** file insert the database connection varaibles. You can use environment variables instead.
```
spring.datasource.url=jdbc:postgresql://[HOST NAME WITH PORT NUMBER]/[DATABASE NAME]
spring.datasource.username=[USERNAME]
spring.datasource.password=[DATABASE PASSWORD]
spring.datasource.driver-class-name=org.postgresql.Driver

hibernate.dialect=org.hibernate.dialect.HSQLDialect

spring.jpa.hibernate.ddl-auto=update
```
3. Open the currency-converter project with your IDE of preference.
4. Compile and run the project.
