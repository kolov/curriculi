# curriculi

A Scala Spring Boot application, mostly exercising Scala. Features:
- OAuth2 login, currently supports Facebook and Google
- User data stored in MongoDB 

## Running locally

### From IDE
zkServer start
secrets/init-zookeeper.sh zkCli localhost:2181
mongod

### Docker Compose

    ./gradlew buildCompose -Penv=compose
    docker-compose -f src/main/docker/docker-compose.yml up
    http://curri.192.168.99.100.xip.io:8080/index


## Cloud Foundry

cf set-env curriculi SPRING_CONFIG_URI http://kolov-cs.cfapps.io
Setting env variable 'SPRING_CONFIG_URI' to 'http://kolov-cs.cfapps.io' for app curriculi


cf set-env curriculi SPRING_CONFIG_URI http://kolov-cs.cfapps.io
