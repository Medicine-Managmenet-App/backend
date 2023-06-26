# Medicine Management App - backend
An application that helps you to manage family medications and medicines taking.

## Introduction
This project aimed to create an IT system to help control family medications. It tracks the expiration dates of meds that we have at home. Thanks to this application, it is easy to check, after a visit to the doctor, if we have prescribed medicines at home or if we need to visit the pharmacy.The second feature of the app is to help deal with the chaos of drug dosage tracking for a family. Every User creating an account is assigned exactly one container called Family, which is created parallelly with an account. Users can share their Family with other Users, by sending invitations with Family ID. When a User accepts an invitation, their automatically created Family is deleted and they are connected to the one from the invitation. Family is a container for all family Medicines and for Family Members. The User is not connected directly to any Family Member. Family Medicine has two fields maintaining its suitability for consumption: expiration date and a period after opening. Family Members are of two types adult (with basic data) and child (with additional data: age and weight). Every Family Member can have their own list of Prescribed Medicine. Prescribed Medicine is directly related to Family Member, as it has dosage and administration times appropriate for a particular person and situation.
The IT system consists of a stateless web application prepared in Spring Boot technology and a relational database.
It was prepared with the three-layer model, which allowed to separate layers of view, business logic, and data storage.
The deployment server is Apache Tomcat, H2 was selected as a local database management system while PostgreSQL was selected as a production database. To be able to use the application, the user must have a modern web browser and an individual account.  

## Used tools and technologies with version

### Technological stack
* Java 17;
* Javax 2.3.0;
* Jackson 2.12.1;
* Project Lombok 1.18.26;
* Flyway 9.19.1;
* Mapstruct 1.5.3 Final;
* springdoc - openapi 1.6.14;

### Development environment
* IntelliJ IDEA 2022.2.3 (Community Edition);
* Maven 3.8.1;
* Spring Boot 2.7.8;

### Runtime
* Apache Tomcat 9.0.71;
* H2 database 2.1.214;
* PostgreSQL

## Requirenments
### Functional
Application has two access levels Guest and User. Each of the access levels is assigned other collection of use cases.
Authentication is done through credentials: login and password.

### Non-functional
System meets following non-functional requirenments:
* application is a monolith in three-layer architecture with view layer, business logic layer and data storage layer,
* system requires authentication with credentials: login and password,
* the authentication patterns are stored in a relational database, 
* passwords are stored in the form of hashes calculated with the SHA-256 algorithm,
* unique logins for user accounts are required,
* business data of application are stored in relational database,
* the relational model is mapped using the JPA mapping standard,
* it provides a mechanism for reporting messages about events and collecting them in the event log,

