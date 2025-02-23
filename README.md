# 🚀 My Spring Boot Application : nevmo
A Spring Boot application built with Gradle to mimic what Venmo does.

## 📜 Description
This Spring Boot application, built using Gradle, that mimics a few features of Venmo.
Please keep in mind only a fraction of what Venmo does is reflected; adhering to the time frame of 2hrs

- A rough proposal of the system design is provided in the folder ```System Design```.
- There is a excalidraw file present that can be opened by going to the website ```https://excalidraw.com/```
- On the website on the top left a hamburg icon exists. Click and select Open.
- Select the excalidraw file
- A rough work on the design of the system, db schema, api endpoints, flowcharts, points to consider when dealing with the system are mentioned. (Yes, it is all over the place and poorly organized)

## Requirements
- Users should be able to create accounts and authenticate
- Each user account should have a balance. (By default when a user is created, its balance is set to 0)
- Users should be able to send money to other users' accounts.
- Users should be able to deposit money or withdraw money from their own accounts.
- Users should be able to view their account details and transaction history.
- Implement functionality for users to send money to other users.
- Each transaction should be recorded with relevant details such as sender, receiver, amount, and timestamp.
- Users should be able to request money from other users. The requested user should see requests when they login and be ankle to approve them.

## 📖 Swagger Doc
You can find the Swagger documentation for the API here:
- Swagger link ```http://localhost:8080/swagger-ui/index.html#/```
```Swagger link will be active once the application is running```

## 📑 Table of Contents
- [Features](#-features)
- [Technologies](#-technologies)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Running the Application](#-running-the-application)
- [Docker](#-docker)
- [Usage](#-usage)
- [Testing](#-testing)
- [Screenshots](#-screenshots)

## 🛠️ Features
The application provides two main endpoints:

1. GET ```/nevmo/{id}/profile```
   This endpoint accepts Path Variable called unique identifier (id), that is the userId of the user.
2. POST ```/receipts/{id}/addUser```
   This endpoint accepts the userId as a path variable and a json body representing the user is accepted.
3. GET ```/nevmo/{id}/transactions```
   This endpoint takes the userId as a path varibale and returns all transactions that the user was a part of.
4. POST ```/receipts/{id}/addBank```
   THis endpoint will accept a json body representing a bank account with userId in the path, and adds the bank account to the user's account
5. PATCH ```/receipts/{id}/sendMoney?recipient={reciepentId}&amount={value}```
   This endpoint is meant to transfer funds from user's account to a recipient using the recipientId in the request parameters and the amount of funds to be transferred is specified in the request parameter as amount.
6. PATCH ```/receipts/{id}/withdraw?amount={value}```
   This endpoint will transfer funds from user's bank account to their nevmo account. Current implmentation takes the first linked bank account for this purpose.
7. GET ```/nevmo/{id}/delete```
   Finally the delete endpoint will delete the user and its linked bank accounts for the userId in the path.

Refer to the [Usage](#-usage) and [Testing](#-testing) sections for further details on how to interact with these endpoints.

Additionally, a rate limiter has been added to the sendMoney endpoint. This is a demonstration of how to limit the number of api calls in a specified window.

Spring secuirty was initially added but removed due to some difficulty in addressing other end points than get Get methods.
Did not like the idea of disable 'cross site referencing'
A passwordBCrypt function was initially added to encrypt user passwords but removed as a part of removing spring security.
Finally, would be a good idea to mask users bank details.

## 🧰 Technologies
This project is built with:
- <a href="https://spring.io/projects/spring-boot" target="_blank"> Spring Boot </a> - Framework for Java-based applications
- <a href="https://gradle.org/" target="_blank"> Gradle </a> - Build automation tool
- <a href="https://www.h2database.com/" target="_blank"> H2 Database </a> - In-memory database (or mention your DB here)
- <a href="https://hibernate.org/" target="_blank"> JPA/Hibernate </a> - ORM for database interaction

NOTE: ALTHOUGH THIS APPLICATION CALLS FOR A STRONG CONSISTENT DATABASE AND NOT AN IN MEMORY DATABASE LIKE H2. THE PURPOSE OF USING H2 WAS TO ACCELERATE THE WORK AND REFLECT THE BEHAVIOUR OF USING A REGULAR RDBMS.

## ⚙️ Installation

### Prerequisites
- JDK 17 or higher <a href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html" target="_blank"> Download JDK </a>
- Gradle 8.x (or you can use the Gradle Wrapper) <a href="https://gradle.org/install/" target="_blank"> Install Gradle </a>

### Steps
1. Clone this repository:
    ```bash
    git clone https://github.com/ab13roy/Nevmo.git
    cd your-repo
    ```

2. Build the project with Gradle:
    ```bash
    ./gradlew build
    ```

3. Run the application:
    ```bash
    ./gradlew bootRun
    ```

### The application will start running on `http://localhost:8080`.
### More details on running the application can be found at [HELP](HELP.md)

## 🔧 Configuration

You can configure the application by editing the ```application.properties``` file located at ```src/main/resources/application.properties```.

Example configuration for a database:
```properties

# H2 DataSource Configuration
spring.datasource.url=jdbc:h2:mem:testdb;
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.platform=h2
spring.h2.console.settings.web-allow-others=true

# H2 Console (Optional for development/debugging)
spring.h2.console.enabled=true

# Connection Pooling (Optional, HikariCP settings)
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.pool-name=HikariPool-1
spring.datasource.hikari.auto-commit=false
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Logging
logging.level.org.springframework.jdbc=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql=TRACE

# Rate Limiting
resilience4j.circuitbreaker.instances.externalService.slidingWindowSize=10
resilience4j.circuitbreaker.instances.externalService.failureRateThreshold=50
resilience4j.circuitbreaker.instances.externalService.waitDurationInOpenState=5000
resilience4j.retry.instances.externalService.maxRetryAttempts=3
resilience4j.retry.instances.externalService.waitDuration=1000
resilience4j.ratelimiter.instances.externalService.limitForPeriod=5
resilience4j.ratelimiter.instances.externalService.limitRefreshPeriod=10s
resilience4j.ratelimiter.instances.externalService.timeoutDuration=10s
resilience4j.ratelimiter.instances.externalService.registerHealthIndicator=true
```

## 🚀 Running the application

- Once the command ```./gradlew build``` is executed a jar file will be created. </br>
- This jar file can be found at path ```~/build/libs/* ``` </br>
- Running the build commands will replace previously existing jar files, unless the build version specified in the ```build.gradle``` file at line ```#8 version = '1.0.1-SNAPSHOT'``` is updated. </br>

Once the project is built, you can run the application in different ways:

### Using Java Cli
- After building the project, navigate to the build/libs folder:
```shell
cd /build/libs
```
- Run the application using:
```shell
java -jar nevmo-1.0.1-SNAPSHOT.jar
```

### Using Docker
If you prefer Docker, you can build and run the Docker image as follows:
- Build the Docker image:
```shell
docker build -t nevmo .
```
- Run the Docker container
```shell
docker run -p 8080:8080 nevmo
```

## 🐳 Docker
This section will guide you through Dockerizing the application.
- Please refer official docker documentation at <a href="https://docs.docker.com/" target="_blank"> Docker </a> for any guides.
- First, ensure that you have Docker installed. <a href="https://docs.docker.com/get-started/get-docker/" target="_blank"> Docker Desktop</a>.
- The project already includes a Dockerfile for building the Docker image.

### Steps to Create the Docker Image
- Navigate to the project directory and build the image:
```shell
docker build -t nevmo .
```
- Verify that the image has been created:
```shell
docker images
```
- Run the container with:
```shell
docker run -p 8080:8080 nevmo
```

## 📝 Testing and Usage

#### Sample JSON payload
Payload for adding a bank account
```json
{
  "bankName" : "wells fargo",
  "accountNumber" : "1233435",
  "routingNumber" : "123123123",
  "accountType" : "CHECKING",
  "balance" : 123123.123
}
```
Payload for adding a user
```json
{
  "userId": "u1245",
  "balance": 0.00,
  "userName": "asda",
  "email": "asdady@o.com",
  "password": "password",
  "accountStatus": "INACTIVE",
  "Accounts": []
}
```
### Example API Calls:
#### GET ```/nevmo/u123/profile```
- Request
```bash
curl curl --location 'localhost:8080/nevmo/u123/profile' \
--data ''
```
- Response
```json
{
  "userId": "u123",
  "balance": 1234.33,
  "userName": "danny lorega",
  "email": "danny@o.com",
  "password": "[p, a, s, s, w, o, r, d]",
  "accountStatus": "ACTIVE",
  "Accounts": [
    {
      "id": 1,
      "bankName": "123456789",
      "accountNumber": "123456789",
      "routingNumber": "Chase",
      "balance": 1000.00,
      "accountType": "CHECKING"
    }
  ]
}
```

#### GET```/nevmo/u124/transactions```

- Request
```bash
curl --location 'localhost:8080/nevmo/u124/transactions' \
--data ''
```
- Response
```json
[Transactions{transactionId='123', timestamp='2025-01-25', amount=10.00, description='test', fromAccount=u122, toAccount=u124, method='nevmo'}, 
  Transactions{transactionId='124', timestamp='2025-01-25', amount=11.00, description='test', fromAccount=u124, toAccount=u122, method='nevmo'}, Transactions{transactionId='125', timestamp='2025-01-25', amount=12.00, description='test', fromAccount=u123, toAccount=u124, method='123456789'}]
```

#### GET ```/nevmo/u124/profile```
```bash
curl --location 'localhost:8080/nevmo/u124/profile' \
--data ''
```
- Response
```json
{
    "userId": "u124",
    "balance": 0.00,
    "userName": "asda",
    "email": "asdady@o.com",
    "password": "[p, a, s, s, w, o, r, d]",
    "accountStatus": "INACTIVE",
    "Accounts": []
}
```
#### POST ```/nevmo/u124/addBank```
```bash
curl --location 'localhost:8080/nevmo/u124/addBank' \
--header 'Content-Type: application/json' \
--data '
{
"bankName" : "wells fargo",
"accountNumber" : "1233435",
"routingNumber" : "123123123",
"accountType" : "CHECKING",
"balance" : 123123.123
}'
```
- Response
```json
Bank account added
```

#### GET ```/nevmo/u124/profile```
```bash
curl --location 'localhost:8080/nevmo/u124/profile' \
--data ''
```
- Response
```json
{
    "userId": "u124",
    "balance": 0.00,
    "userName": "asda",
    "email": "asdady@o.com",
    "password": "[p, a, s, s, w, o, r, d]",
    "accountStatus": "INACTIVE",
    "Accounts": [
        {
            "id": 2,
            "bankName": "wells fargo",
            "accountNumber": "1233435",
            "routingNumber": "123123123",
            "balance": 123123.12,
            "accountType": "CHECKING"
        }
    ]
}
```

#### POST ```/nevmo/u1245/addUser```
```bash
curl --location 'localhost:8080/nevmo/u1245/addUser' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userId": "u1245",
    "balance": 0.00,
    "userName": "asda",
    "email": "asdady@o.com",
    "password": "password",
    "accountStatus": "INACTIVE",
    "Accounts": []
}'
```
- Response
```json
{
  "userId": "u1245",
  "balance": 0.00,
  "userName": "asda",
  "email": "asdady@o.com",
  "password": "[p, a, s, s, w, o, r, d]",
  "accountStatus": "INACTIVE",
  "Accounts": []
}
```

#### PATCH ```/nevmo/u1245/addUser```
```bash
curl --location --request PATCH 'localhost:8080/nevmo/u123/sendMoney?recipient=u122&amount=100' \
--header 'Authorization: Basic dXNlcjphZG1pbg==' \
--data ''
```
- Response
```json
Transfer successful
```

#### PATCH ```/nevmo/u123/withdraw?amount=10.00```
```bash
curl --location --request PATCH 'localhost:8080/nevmo/u123/withdraw?amount=10.00' \
--header 'Authorization: Basic dXNlcjphZG1pbg==' \
--data ''
```
- Response
```json
Deposit successful
```

#### DELETE ```/nevmo/u1245/delete```
```bash
curl --location --request DELETE 'localhost:8080/nevmo/u1245/delete' \
--data ''
```
- Response
```json
User deleted
```

## 📷 Screenshots
- Find them here at [Screenshots](Screenshots)

