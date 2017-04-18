# curriculi

A Scala Spring Boot application, mostly exercising Scala and Spring Cloud. Features:
- OAuth2 login, currently supports Facebook and Google
- Spring Configuraton server backed up by Git
- Service registration and discovery with Eureka
- Feign client
- Edge service with Zuul
- Microservice Users - Mongodb
- session serialised in Redis

To run, the system needs this service(s) registerd in Eureka:
- https://github.com/kolov/curri-docs-service

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

### Docker  

    aws ecr get-login --region eu-central-1

 
## Kubernetes

	 CURRI_ROOT=~/projects/curriculi
	 kubectl delete configmap curri-config
   kubectl create configmap curri-config --from-file=$CURRI_ROOT/secrets/config-server/config-server.properties
   kubectl delete -f $CURRI_ROOT/src/main/k8s/config-server-d.yml
   kubectl apply -f $CURRI_ROOT/src/main/k8s/config-server-d.yml
   kubectl delete -f $CURRI_ROOT/src/main/k8s/config-server-s.yml
   kubectl apply -f $CURRI_ROOT/src/main/k8s/config-server-s.yml
