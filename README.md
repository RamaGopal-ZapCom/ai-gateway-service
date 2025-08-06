# Gateway Service - AI Customer Support Agents

## 1. Project Overview

### **Project Name:** AI Customer Support Agents (ACSA) - gateway-service  
### **Description:** 
In the AI Customer Support Agents microservices design, **Spring Cloud Gateway** is used as the entry point. It manages requests using routing, security, and fault tolerance capabilities.**Path rewriting** feature enables the conversion of incoming request paths for mapping to downstream service routing.
To prevent excessive traffic or harmful things, the gateway uses **rate limiting** through a Redis-backed token bucket algorithm. This ensures that the number of requests a client can make within a given time period is limited.
In order to boost the systems resilient nature, the gateway makes use of **Resilience4j for circuit breaking**.
In addition, the gateway provides support for integration with **Static Service Discovery**, which enables static routing to microservices.
**Monitoring and observability** are made possible by integration with tools such as Spring Boot Actuator, giving insights into request flows, rate limiting statistics, and circuit breaker states.

## 2. Prerequisites

- **Java Version:** Java 21
- **Spring Boot Version:** 3.0.4
- **redis:** For rate limiting
- **Build Tool:** Gradle 8.4
- **Other Tools:** 
  - Intellij IDEA Community Edition 2023.1.3
  - Spring Cloud Gateway
  - Resilience4j (Circuit Breaker)

## 3. Project Setup

### Clone the Repository:
```
git clone <repository-url>
```

### Build the Project:
```bash
./gradlew clean build
```

### Run the Application:
```bash
./gradlew bootRun
```

## 4. Configuration

### Environment Configurations
The application is configured via the `application.yml` file. The Key configurations include:

- **Server Port**: `8080`
- **Active Spring Profile**: `dev`
- **Route Configurations** for downstream services:
  - `AUTH_SERVICE_URL=http://localhost:8081`
  - `CUSTOMER_SERVICE_URL=http://localhost:8082`
- **Rate Limiting Settings**:
  - `Replenish Rate`: `10`
  - `Burst Capacity`: `20`
  - `Requested Tokens`: `1`
- **Circuit Breaker Configurations**
- **API Key Validation Settings**
- **CORS Settings**:
  - `Allowed Methods`: `GET, POST, PUT, DELETE, OPTIONS`
  - `Allowed Headers`: `*`
  - `Allow Credentials`: `true`
  - `Max Age`: `3600` seconds

### Redis Connection Settings
```yaml
spring:
  redis:
    host: localhost
    port: 6379
```

## 5. Directory Structure

```
OptimusAPI_Gateway_service/
├── .gradle/
├── .idea
├── build
├── gradle
├── scripts/
│   └── pre-commit
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── zapcom/
│   │   │           ├── configuration/       
│   │   │           │   ├── GatewayServiceConfiguration.java
|   |   |           |   ├── GatewayServ
│   │   │           │   └── RateLimiterConfiguration.java
│   │   │           ├── controller/        
│   │   │           │   └── FallbackController.java
│   │   │           ├── exception/       
│   │   │           │   ├── GatewayServiceException.java
│   │   │           │   └── GatewayServiceGlobalExceptionHandler.java
│   │   │           │   └── GatewayServiceForbiddenException.java
│   │   │           │   └── GatewayServiceForbiddenException.java
│   │   │           │   └── GatewayServiceUnauthorizedException.java
│   │   │           ├── filter/          
│   │   │           │   ├── GatewayServiceRequestLoggingFilter.java
│   │   │           ├── model/       
│   │   │           │   ├── request/
│   │   │           │   │   └── GatewayServiceRequest.java
│   │   │           │   └── response/
│   │   │           │       ├── GatewayServiceErrorResponse.java
│   │   │           │       └── GatewayServiceResponse.java
│   │   │           ├── utils/           
│   │   │           │   ├── GatewayServicePathConstants.java
│   │   │           │   ├── GatewayServiceRequestConstants.java
│   │   │           │   └── GatewayServiceResponseConstants.java
│   │   │           └── GatewayServiceApplication.java
│   │   └── resources/
│   │       └── application.yml         
│   │       └── application-dev.yml
│   │       └── application-prod.yml
│   │       └──  
│   └── test/
│       └── java/
│           └── com.zapcom
│               └── GatewayServiceApplicationTests.java
├── .env        
├── .gitattributes
├── .gitignore                     
├── build.gradle         
├── docker-compose.yml
├── Dockerfile                
├── gradlew
├── gradlew.bat
├── README.md                            
└── settings.gradle
```

### Project Structure Description

The `OptimusAPI_Gateway_service` is a Spring Boot–based API Gateway service designed for routing, filtering, authentication, and rate limiting in a microservices architecture. Below is a breakdown of the structure:

- **`src/main/java/com/zapcom/`**  
  Contains Java source code including configurations, controllers, filters, exception handlers, data model and utility classes folders and the main application class.

- **`configuration/`**  
  Defines configurations such as route definitions and rate limiter setup.

- **`controller/`**  
  Handles fallback endpoints and error routing when downstream services (authentication-service and customer-service) are unavailable.

- **`exception/`**  
  Includes various exceptions and a global exception handler to manage error responses uniformly.

- **`filter/`**  
  Implements pre-processing and post-processing filters including logging, response modification, and API key authentication.

- **`model/request` and `model/response`**  
  Defines structured request and response DTOs for consistent data exchange.

- **`utils/`**  
  Constants used across the application and validator for api-key.

- **`resources/application.yml`**  
  Central configuration file for gateway routing, filter settings, and externalized properties.

-  **`resources/application-dev.yml`**  
   Central configuration file for gateway routing, filter settings, and externalized properties but with dev profile active.

-  **`resources/application-prod.yml`**  
   Central configuration file for gateway routing, filter settings, and externalized properties but with prod profile active.

- **`scripts/pre-commit`**  
  Git pre-commit hook to ensure code quality checks before committing and pushing code to main.

- **`build.gradle`, `settings.gradle`, `gradlew`, `gradlew.bat`**  
  Gradle build configuration and wrapper scripts for cross-platform build support.

- **`test/java/com.zapcom/`**  
  Contains unit and integration tests for verifying the gateway functionalities used.

## 6. API Documentation

Swagger documentation is not directly applicable to API Gateway services as they primarily route to other services.

## 7. Error Handling

### Common Error Responses Used:

- **503 Service Unavailable**: This is returned when the downstream service (like authentication-service or customer-service) or the gateway-service itself is temporarily unavailable. This often happens due to the overload or maintenance as well.
- **429 Too Many Requests**: This status is returned when the pre-defined rate limits are exceeded.
- **401 Unauthorized**: Indicates that authentication has failed, often due to a missing or invalid API key or JWT token.To overcome this valid credentials must be provided.
- **403 Forbidden**: Returned when the client is authenticated but does not have the necessary permissions to perform the requested action.
- **400 Bad Request**: This status is returned when the server cannot process the request due to a client-side error. Common causes are:
  - Missing required fields in the request body (e.g., email or password not provided).

  - Invalid data types or format.

  - Malformed JSON or incorrect structure in the request body.

  - Violation of input validation rules (e.g., password too short, email improperly formatted).

  - Sending unsupported parameters or query strings that are not handled by the server.
  
## 8. Testing

### Run Tests:
```bash
./gradlew test
```

### Test Categories:
- **Unit Tests:**
  Unit tests focus on testing individual components or services in isolation. For a gateway-service, unit tests often target controllers, filters, or configuration classes.
    - Steps to run unit tests:
        - 1. Write Unit Tests (JUnit 5)
        - 2. Run Unit Tests using Gradle:
            ```bash
            ./gradlew test
            ```
        - 3. Verify Test Results: Check the output in your terminal to verify that all unit tests passed. You can also look at the build folder (Gradle) for test reports.
- **Integration Tests:**
  Integration tests test the interaction between multiple components of the system, including the actual Spring Boot context, databases, and external services.

  For the gateway-service microservice, integration tests should things like routing, filters, and integration with other services (authentication-service and customer-service)
    - Steps to run integration tests:
        - 1. Write Integration Tests (JUnit 5 + Spring Boot Test)
             Integration tests can use @SpringBootTest to load the full Spring context.
        - 2. Run Integration Tests using Gradle (Gradle 8.4 is to be used)

## 9. Deployment Instructions

### Local Deployment
1. Ensure Redis is running locally
2. Run with development profile:
   ```
   ./gradlew bootRun --args='--spring.profiles.active=dev'
   ```

### QA Environment
1. Build the application:
   ```
   ./gradlew build
   ```
2. Deploy the JAR file to the QA server
3. Run with QA profile

### Production Environment
1. Build the application:
   ```
   ./gradlew build
   ```
2. Deploy the JAR file to production servers
3. Run with production profile:
   ```
   cd build
   ```
   ```
   cd libs
   ```
   ```
   java -jar OptimusAPI_Gateway_service-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
   ```

### Docker Setup:

1. Create a `Dockerfile` within the root directory:
    ```dockerfile
    FROM openjdk:21
    COPY build/libs/Optimus_Gateway_service-0.0.01-SNAPSHOT.jar
    ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]
    ```
2. Build the project 'JAR' file:
    ```bash
    ./gradlew build
    ```
    After running this the JAR file appears in build/libs

3. Build the Docker image:
    ```
    docker build -t optimus-api-gateway
    ```

4. Run the container formed:

    ```
    docker run -p 8080:8080 optimus-api-gateway
    ```
    within the above running of the docker container 8080 port number comes into picture.


