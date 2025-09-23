# Task Management API

## Description

The **Task Management API** is an application developed with Spring Boot that allows efficient task management. It provides RESTful endpoints to perform CRUD operations (Create, Read, Update, Delete) on tasks.

## Features

- Create new tasks.
- Retrieve the list of existing tasks.
- Update details of a specific task.
- Delete tasks.

## Technologies Used

- **Java 21**
- **Spring Boot**
- **Maven**
- **H2 Database** (In-memory database for testing)

## Prerequisites

Make sure you have the following components installed:

- **Java 21** or higher
- **Maven**

## Setup

1. Clone this repository:
   ```bash
   git clone https://github.com/Dan13l-M/java-enterprise-journey.git
   ```
2. Navigate to the project folder:
   ```bash
   cd projects/week-01/task-management-api
   ```
3. Configure the `application.properties` file if you need to customize the database or server port settings.

## Running the Application

1. Build and run the application using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
2. The API will be available at `http://localhost:8080` by default.

## Endpoints

| Method | Endpoint             | Description              |
| ------ | -------------------- | ------------------------ |
| GET    | `/api/v1/tasks`      | Retrieves all tasks      |
| POST   | `/api/v1/tasks`      | Creates a new task       |
| PUT    | `/api/v1/tasks/{id}` | Updates an existing task |
| DELETE | `/api/v1/tasks/{id}` | Deletes a specific task  |

## Project Structure

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.dan13l.taskapi
│   │   │       ├── controller
│   │   │       ├── model
│   │   │       ├── repository
│   │   │       └── service
│   │   └── resources
│   │       ├── application.properties
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── com.dan13l.taskapi
├── pom.xml
└── README.md
```

## Contributions

Contributions are welcome! If you want to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix:
   ```bash
   git checkout -b feature/new-feature
   ```
3. Make your changes and commit them:
   ```bash
   git commit -m "Added new feature"
   ```
4. Push your changes to the remote repository:
   ```bash
   git push origin feature/new-feature
   ```
5. Open a Pull Request.

## License

This project is licensed under the [MIT License](../../LICENSE).
