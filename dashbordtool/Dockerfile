
# Use Maven 3.8.3 with OpenJDK 17 for deployment
FROM maven:3.8.3-openjdk-17 AS build

# Copy the source code and the pom.xml file
COPY src /app/src
COPY pom.xml /app

# Build the application
RUN mvn -f /app/pom.xml clean package -DskipTests

# Use OpenJDK 17 for the runtime environment
FROM openjdk:17

# Copy the JAR file built in the previous stage
COPY --from=build /app/target/dashbordtool-0.0.1-SNAPSHOT.jar /app/dashbordtool-0.0.1-SNAPSHOT.jar
EXPOSE 8090

# Define the command to run the application
CMD ["java", "-jar", "/app/dashbordtool-0.0.1-SNAPSHOT.jar"]
