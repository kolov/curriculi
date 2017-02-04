# curriculi

A Scala Spring Boot application, mostly exercising Scala and Spring Cloud. Features:
- OAuth2 login, currently supports Facebook and Google
- Spring Configuraton server backed up by Git
- Service registration and discovery with Eureka
- Feign client
- Edge service with Zuul
- Microservice Documents - Mongodb (doesn't do a lot)
- Microservice Users - Mongodb
- session serialised in Redi

## Running locally

### From IDE 

Many applications have to be started to make this cloud-based system to work.
The IntelliJ Multirun plugin makes this easy, otherwise start all separate components 
manually.
#### Start needed services on your development station
	mongod
	redis
#### Place OAuth secrets in Git
Make `curriculi.yml` file containing Ouath2 secret ane place in the Git location accessed by 
the config server.
#### Start config server
The config-server project has `../secrets/config-server` on its resources classpath. 
Place `application.yml` with a Git server url and credentiials there.
#### Start eureka-server, then all services and the application separately
Run their corresponding @SpringBootApplication main()

### Docker Compose

There is one image containing a secret - config-server need the credentios to the git repo where the other 
secrets are kept.

    ./gradlew build buildImages
    docker-compose -f src/main/docker/docker-compose.yml up
    Go to http://curri.xip.io:8080/index
