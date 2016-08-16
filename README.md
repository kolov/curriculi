Curriculi
====================
Document-editin based on templates. To abstract, wait for a working self-explaining demo.

Thanks to https://github.com/bijukunjummen/spring-boot-scala-web for the quick start with Spring-boot & Scala.


## Running it

Docker Swarm: 

    docker service create --name mongo  --mount type=bind,source=/data/mongo,target=/data/db  --constraint 'engine.labels.persistent.storage == true' mongo:3.0


