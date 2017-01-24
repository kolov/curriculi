# curriculi

A Scala Spring Boot application, mostly exercising Scala and Spring Cloud. Features:
- OAuth2 login, currently supports Facebook and Google
- User data stored in MongoDB 
- Spring Configuraton server backed up by Git
- Service registration and discovery with Eureka
- Edge service with Zuul

## Running locally

### From IDE 
#### Start needed services on Mac OS
	docker run -d -p 8081:8080 netflixoss/eureka:1.3.1
	mongod
	
	
Run CurriApp.mainm and the services.

### Docker Compose

    ./gradlew build buildImages
    docker-compose -f src/main/docker/docker-compose.yml up
    Go to http://curri.xip.io:8080/index
