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

FROM maven:3.8.7-openjdk-17-slim


WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:resolve

# Copy the rest of the project files into the container
COPY . .

# Command to run the tests
CMD ["mvn", "test"]
