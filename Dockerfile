## Use an official OpenJDK runtime as a parent image
#FROM openjdk:21-slim
#
## Set the working directory in the container
#WORKDIR /app
#
## Copy the project files into the container
#COPY . .
#
## Install Maven
#RUN apt-get update && apt-get install -y maven
#
### Run Maven install to download dependencies
##RUN mvn install -DskipTests
#
## Command to run the tests
#CMD ["mvn", "test"]

#FROM openjdk:21-slim
#
#
#WORKDIR /app
#
## Copy pom.xml and download dependencies
#COPY pom.xml .
#RUN mvn dependency:resolve
#
## Copy the rest of the project files into the container
#COPY . .
#
## Command to run the tests
#CMD ["mvn", "test"]

# Use Maven with OpenJDK 21
FROM maven:3.8.4-openjdk-21-slim

# Set the working directory in the container
WORKDIR /app

# Copy the POM file
COPY pom.xml .

# Download dependencies
# This is done in a separate step to take advantage of Docker caching
RUN mvn dependency:go-offline -B

# Copy the project files
COPY src ./src

# Build the application and run tests
# Using verify instead of test to include the package phase
CMD ["mvn", "verify"]
