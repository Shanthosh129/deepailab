# Use OpenJDK 21 as the base image
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file to the container
COPY target/application.jar /app/application.jar
COPY .env /app/.env
# Expose the application port (update if needed)
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
