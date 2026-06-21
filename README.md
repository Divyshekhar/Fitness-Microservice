# Fitness AI Tracker
AI-powered fitness tracking application built using Spring Boot Microservice Architecture.
## Tech Stack

 - Java 21
 - Spring Boot
 - Spring Cloud Gateway
 - Netflix Eureka
 - Google Gemini
 - PostgreSQL
 - MongoDB
 - RabbitMQ
 - Docker
 - Maven


### Contents
- User Service
- AI Service
- Activity Service
- API Gateway
- Eureka Service

## Running Locally

### 1. Clone the repository

```bash 
git clone https://github.com/Divyshekhar/Fitness-Microservice.git
cd Fitness-Microservice
```

### 2. Start the required containers
#### Postgres
``` bash 
docker run --name fitness-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=fitness_user_db -p 5432:5432 -d postgres:latest
```  
#### MongoDB
```bash 
docker run --name fitnessactivity -e MONGO_INITDB_DATABASE=fitnessactivity -p 27017:27017 -d mongo:latest
```
#### RabbitMQ
```bash
docker run -it --rm --name fitness-rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4-management
```  



### 3. Install Dependencies
Open the project in IntelliJ and allow Maven to download all dependencies.

### 4. Start Services
Start the applications in following order:  
1. Eureka Service
2. Config Service (Config Server)
3. API Gateway
4. User Service
5. AI Service
6. Activity Service

## Service Ports


| Service | Port  |
|----------|-------|
| Eureka Server | 8761  |
| API Gateway | 8084  |
|Config Server| 8888|
| User Service | 8081  |
| Activity Service | 8082  |
| AI Service | 8083  |
| PostgreSQL | 5432  |
| MongoDB | 27017 |
| RabbitMQ | 5672  |
| RabbitMQ Dashboard | 15672 |

## Environment Variables  
Put this in the environment of `AI Service`

#### - ```GEMINI_API_URL```
#### - ````GEMINI_API_KEY````