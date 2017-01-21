# curriculi

A Scala Spring Boot application, mostly exercising Scala. Features:
- OAuth2 login, currently supports Facebook and Google
- User data stored in MongoDB 

## Running locally

### From IDE 
#### Start needed services on Mac OS
	zkServer start
	secrets/init-zookeeper.sh zkCli localhost:2181
	mongod
	
Run CurriApp.main

### Docker Compose

    ./gradlew buildCompose
    docker-compose -f src/main/docker/docker-compose.yml up
    Go to http://curri.xip.io:8080/index
