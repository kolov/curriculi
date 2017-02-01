# curriculi

A Scala Spring Boot application, mostly exercising Scala and Spring Cloud. Features:
- OAuth2 login, currently supports Facebook and Google
- Spring Configuraton server backed up by Git
- Service registration and discovery with Eureka
- Feign client
- Edge service with Zuul
- Microservice Documents - Mongodb
- Microservice Users - Mongodb
- session serialised in Redi

## Running locally

### From IDE 
#### Start needed services on Mac OS
	mongod
	redis
#### Start config server with credentials to Git server containing configurarion
config-server has `../secrets/config-server` on its resources classpath. Place `application.yml` with the credentiials there.
#### Start all services separately, then the web app
Run the @SpringBootApplication main()

### Docker Compose

    ./gradlew build buildImages
    docker-compose -f src/main/docker/docker-compose.yml up
    Go to http://curri.xip.io:8080/index
