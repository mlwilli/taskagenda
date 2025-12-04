# Task Agenda Service

A simple Spring Boot REST API for managing tasks.  
Includes CRUD operations, status updates, persistence with JPA/Hibernate, and an in-memory H2 database for local development.

---

## Features

- Create, update, delete, and list tasks  
- Mark tasks as completed  
- Java 21 (Eclipse Temurin 21.0.9)  
- Spring Boot 3.3.4  
- H2 in-memory database with browser console  
- Full REST API with Postman collection included  

---

## Requirements

- Java 21  
- Maven 3.9+  

---

## Running the Application

```bash
mvn clean spring-boot:run
The service will start on:

http://localhost:8080

API Endpoints
Tasks
Method	Endpoint	Description
GET	/api/tasks	Get all tasks
GET	/api/tasks/{id}	Get task by ID
POST	/api/tasks	Create new task
PUT	/api/tasks/{id}	Update an existing task
DELETE	/api/tasks/{id}	Delete task
POST	/api/tasks/{id}/complete	Mark task completed
Database

The application uses an in-memory H2 database for development.

H2 Console:

http://localhost:8080/h2-console


JDBC settings:

JDBC URL: jdbc:h2:mem:taskdb
User: sa
Password: (empty)

Postman Collection

A full Postman collection is located in:
/postman/task-agenda-service.postman_collection.json


Import it into Postman to test all API endpoints.

Running Tests
mvn clean test

Project Structure (Overview)
src/main/java/com/github/mlwilli/taskagenda/
├── TaskAgendaApplication.java
└── task/
    ├── domain/
    ├── dto/
    ├── repository/
    ├── service/
    └── web/
