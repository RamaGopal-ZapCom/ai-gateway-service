# Use lightweight OpenJDK image
FROM eclipse-temurin:21-jdk-alpine

# Set work directory in container
WORKDIR /app

# Copy the jar from host to container
COPY build/libs/gateway-service.jar app.jar

# Expose the port used by your app
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
