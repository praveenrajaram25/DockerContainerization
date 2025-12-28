# DockerContainerization
This project demonstrates how to containerize a Spring Boot application using Docker and run it as an isolated, portable service. The goal is to make the application easy to build, deploy, and run consistently across different environments without dependency issues.


1. Prepare the Spring Boot Application
Creating a Simple Spring Boot  appication with two rest end points .
  1.One will simply show plain text
  2.Another will fetch the data from externalized MySQL DB (rinning on my machine )  and send to my dockerized spring boot application

   
1.1 Configure database connection in my application

The application uses an external database (MySQL / Oracle) running on the host machine.
Instead of using localhost, the special Docker hostname is used: host.docker.internal

Example (application.properties):
spring.datasource.url=jdbc:mysql://host.docker.internal:3306/docker_db
spring.datasource.username=docker
spring.datasource.password=docker@12345

This allows the containerized application to reach the database running on the host OS.

2. Create Database User and Grant Privileges

A dedicated database user was created for the application.In Real World , instead of giving % to grant access ,
they will allow the specific application subnet.

SQL executed in MySQL Workbench:
CREATE USER 'docker'@'%' IDENTIFIED BY '[docker@12345]';
GRANT SELECT, INSERT, UPDATE, DELETE ON docker_db.* TO 'docker'@'%';

2.1. Creating a table user  and add some data inside docker_db for accessing data

CREATE TABLE user (
  ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  NAME varchar(45) DEFAULT NULL,
  STATUS varchar(45) DEFAULT NULL,
);

3.Create Dockerfile
A Dockerfile was created to package the Spring Boot JAR into a container.

FROM eclipse-temurin:17-jdk
ADD target/DockerApp.jar DockerApp.jar
ENTRYPOINT ["java","-jar","/DockerApp.jar"]


4.Using Docker Commands build your images and using the image create container and run it

Create Image : docker build -t deploy/dockerapp:2.0 .
Create Container : docker run -p 8080:8080 deploy/dockerapp:2.0
Show Container : docker ps
Show Images : docker images
Run Container : docker start [container_name]
Stop Container : docker stop [container_name]
Delete Container : docker rm [container_name/id]
Delete Image : docker rmi [image_name/id]

Make sure to delete the containers before deleting images.If any conatiners 
available while deleting images , docker will not allow to delete

<img width="940" height="529" alt="image" src="https://github.com/user-attachments/assets/c1e63257-169a-4ff4-99ce-6b0e7ae6e219" />


<img width="940" height="529" alt="image" src="https://github.com/user-attachments/assets/765fdcfd-3f62-4d86-979a-16ec96951fd6" />


<img width="940" height="529" alt="image" src="https://github.com/user-attachments/assets/ecd053bf-2528-4853-9a55-352bc68f72ba" />


5.Use the URL to access Application
URL 1 : http://localhost:8080/container
URL 2 : http://localhost:8080/user
