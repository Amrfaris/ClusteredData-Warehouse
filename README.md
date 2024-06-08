# ClusteredDataWarehouse

## Overview
ClusteredDataWarehouse is a Java-based application that manages transaction data within a MySQL database. This project is containerized using Docker, making it easy to deploy and run.

## Prerequisites
- Docker
- Docker Compose
- Maven
- Git

## Project Structure
src/

main/
java/
Main.java
util/
DBConnection.java
dao/
TransactionDAO.java
model/
Transaction.java
test/
java/
TransactionDAOTest.java
docker-compose.yml
Dockerfile
pom.xml
README.md


## Setup and Usage

### Cloning the Repository
git clone https://github.com/your-username/ClusteredDataWarehouse.git
cd ClusteredDataWarehouse


### Building and Running the Application

1. **Build the application using Maven:**
mvn clean package


2. **Build and run the application using Docker Compose:**
3. docker-compose up --build


### Accessing the Application
Once the application and the MySQL database are up and running, you can interact with the application on `http://localhost:8080`.

### Testing the Application
The project includes unit tests using JUnit and Mockito. To run the tests:

mvn test


## Docker Configuration

### Dockerfile
```dockerfile
# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/ClusteredDataWarehouse-1.0-SNAPSHOT-jar-with-dependencies.jar /app/ClusteredDataWarehouse.jar

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "ClusteredDataWarehouse.jar"]
